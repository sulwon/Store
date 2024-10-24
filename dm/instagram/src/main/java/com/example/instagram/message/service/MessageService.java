package com.example.instagram.message.service;

import com.example.instagram.message.domain.DMMessage;
import com.example.instagram.message.repository.DMMessageRepository;
import com.example.instagram.dm.domain.DMRoom;
import com.example.instagram.dm.domain.DMRoomMember;  // 추가
import com.example.instagram.dm.repository.DMRoomRepository;
import com.example.instagram.dm.repository.DMRoomMemberRepository;  // 추가
import com.example.instagram.user.domain.User;
import com.example.instagram.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MessageService {
    private final DMMessageRepository messageRepository;
    private final DMRoomRepository dmRoomRepository;
    private final DMRoomMemberRepository dmRoomMemberRepository;  // 추가
    private final UserRepository userRepository;

    // 메시지 전송
    public DMMessage sendMessage(Long roomId, Long senderId, String content) {
        DMRoom room = dmRoomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Room not found: " + roomId));
        User sender = userRepository.findById(senderId)
                .orElseThrow(() -> new RuntimeException("User not found: " + senderId));

        DMMessage message = new DMMessage();
        message.setDmRoom(room);
        message.setSender(sender);
        message.setContent(content);

        return messageRepository.save(message);
    }

    // 메시지 목록 조회
    @Transactional(readOnly = true)
    public List<DMMessage> getMessages(Long roomId, Long userId, int page, int size) {  // userId 파라미터 추가
        DMRoom room = dmRoomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Room not found: " + roomId));

        // 사용자가 해당 채팅방의 멤버인지 확인
        dmRoomMemberRepository.findByDmRoomAndUserId(room, userId)
                .orElseThrow(() -> new RuntimeException("Member not found"));

        PageRequest pageRequest = PageRequest.of(
                page,
                size,
                Sort.by("createdAt").descending()
        );

        return messageRepository.findByDmRoomOrderByCreatedAtDesc(room, pageRequest);
    }

    // 읽지 않은 메시지 수 조회
    @Transactional(readOnly = true)
    public Long getUnreadMessageCount(Long roomId, Long userId, LocalDateTime lastReadTime) {
        DMRoom room = dmRoomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Room not found: " + roomId));

        return messageRepository.countByDmRoomAndCreatedAtGreaterThan(room, lastReadTime);
    }

    // 읽음 상태 업데이트
    @Transactional
    public void updateReadStatus(Long roomId, Long userId) {
        DMRoom room = dmRoomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Room not found"));

        // 현재 시간을 기준으로 읽음 처리
        LocalDateTime now = LocalDateTime.now();

        // DMRoomMember의 lastReadAt 업데이트
        DMRoomMember member = dmRoomMemberRepository.findByDmRoomAndUserId(room, userId)  // 메서드 이름 변경
                .orElseThrow(() -> new RuntimeException("Member not found"));
        member.setLastReadAt(now);
        dmRoomMemberRepository.save(member);
    }

    @Transactional(readOnly = true)
    public LocalDateTime getLastReadTime(Long roomId, Long userId) {
        DMRoom room = dmRoomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Room not found: " + roomId));

        // 해당 사용자의 채팅방 멤버 정보 조회
        DMRoomMember member = dmRoomMemberRepository.findByDmRoomAndUserId(room, userId)
                .orElseThrow(() -> new RuntimeException("Member not found"));

        // 마지막 읽은 시간 반환
        return member.getLastReadAt();
    }

    @Transactional
    public void deleteMessage(Long roomId, Long messageId, Long userId) {
        // 메시지 존재 여부 확인
        DMMessage message = messageRepository.findById(messageId)
                .orElseThrow(() -> new RuntimeException("Message not found"));

        // 채팅방 확인
        if (!message.getDmRoom().getId().equals(roomId)) {
            throw new RuntimeException("Message does not belong to this room");
        }

        // 삭제 권한 확인 (메시지 작성자만 삭제 가능)
        if (!message.getSender().getId().equals(userId)) {
            throw new RuntimeException("Not authorized to delete this message");
        }

        // 메시지 삭제
        messageRepository.delete(message);
    }

}