// src/main/java/com/example/instagram/user/controller/dto/UserResponse.java
package com.example.instagram.user.controller.dto;

import com.example.instagram.user.domain.User;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
public class UserResponse {
    private Long id;
    private String username;
    private String email;
    private String profileImage;

    public static UserResponse from(User user) {
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setEmail(user.getEmail());
        response.setProfileImage(user.getProfileImage());
        return response;
    }
}