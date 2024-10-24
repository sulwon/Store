package com.example.instagram.message.controller;

import com.example.instagram.message.controller.dto.MessageRequest;
import com.example.instagram.message.controller.dto.MessageResponse;
import com.example.instagram.message.domain.DMMessage;
import com.example.instagram.message.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;

    // 메시지 전송
    @PostMapping("/rooms/{roomId}")
    public ResponseEntity<MessageResponse> sendMessage(
            @PathVariable Long roomId,
            @RequestParam("userId") Long userId,
            @RequestBody MessageRequest request
    ) {
        DMMessage message = messageService.sendMessage(roomId, userId, request.getContent());
        LocalDateTime lastReadAt = messageService.getLastReadTime(roomId, userId);
        return ResponseEntity.ok(MessageResponse.from(message, lastReadAt));
    }

    // 메시지 목록 조회
    @GetMapping("/rooms/{roomId}")
    public ResponseEntity<List<MessageResponse>> getMessages(
            @PathVariable Long roomId,
            @RequestParam("userId") Long currentUserId,  // 변수명을 더 명확하게
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        LocalDateTime lastReadAt = messageService.getLastReadTime(roomId, currentUserId);
        List<DMMessage> messages = messageService.getMessages(roomId, currentUserId, page, size);

        // 로그 추가
        System.out.println("Current User ID: " + currentUserId);
        System.out.println("Last Read At: " + lastReadAt);

        List<MessageResponse> responses = messages.stream()
                .map(message -> {
                    Long senderId = message.getSender().getId();
                    boolean isRead = senderId.equals(currentUserId) || // 내가 보낸 메시지
                            (lastReadAt != null && !message.getCreatedAt().isAfter(lastReadAt)); // 읽은 시간 이전 메시지

                    // 디버깅용 로그
                    System.out.println("Message ID: " + message.getId() +
                            ", Sender ID: " + senderId +
                            ", Created At: " + message.getCreatedAt() +
                            ", Is Read: " + isRead);

                    return MessageResponse.builder()
                            .id(message.getId())
                            .roomId(message.getDmRoom().getId())
                            .senderId(senderId)
                            .content(message.getContent())
                            .createdAt(message.getCreatedAt())
                            .isRead(isRead)
                            .build();
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(responses);
    }

    // 읽지 않은 메시지 수 조회
    @GetMapping("/rooms/{roomId}/unread")
    public ResponseEntity<Long> getUnreadMessageCount(
            @PathVariable Long roomId,
            @RequestAttribute("userId") Long userId,
            @RequestParam String lastReadTime
    ) {
        Long count = messageService.getUnreadMessageCount(
                roomId,
                userId,
                LocalDateTime.parse(lastReadTime)
        );
        return ResponseEntity.ok(count);
    }

    // 읽음 상태 업데이트
    @PutMapping("/rooms/{roomId}/read")  // '/read'가 추가되어야 함
    public ResponseEntity<Void> updateReadStatus(
            @PathVariable Long roomId,
            @RequestParam("userId") Long userId
    ) {
        messageService.updateReadStatus(roomId, userId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/rooms/{roomId}/messages/{messageId}")
    public ResponseEntity<Void> deleteMessage(
            @PathVariable Long roomId,
            @PathVariable Long messageId,
            @RequestParam("userId") Long userId  // 메시지 삭제 권한 확인용
    ) {
        messageService.deleteMessage(roomId, messageId, userId);
        return ResponseEntity.ok().build();
    }
}