package com.example.instagram.message.controller.dto;

import com.example.instagram.message.domain.DMMessage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageResponse {
    private Long id;
    private Long roomId;
    private Long senderId;
    private String content;
    private Long imageId;
    private LocalDateTime createdAt;
    private boolean isRead;

    public static MessageResponse from(DMMessage message, LocalDateTime lastReadAt) {
        boolean isRead = message.getSender().getId().equals(message.getDmRoom().getId()) || // 본인이 보낸 메시지인 경우
                (lastReadAt != null && !message.getCreatedAt().isAfter(lastReadAt)); // 또는 lastReadAt 이전 메시지인 경우

        return MessageResponse.builder()
                .id(message.getId())
                .roomId(message.getDmRoom().getId())
                .senderId(message.getSender().getId())
                .content(message.getContent())
                .createdAt(message.getCreatedAt())
                .isRead(isRead)
                .build();
    }
}