package com.example.instagram.message.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageRequest {
    private String content;
    private Long imageId;  // 이미지 메시지를 위한 필드 (선택적)

    // 메시지 유효성 검사
    public boolean isValid() {
        return content != null && !content.trim().isEmpty();
    }
}