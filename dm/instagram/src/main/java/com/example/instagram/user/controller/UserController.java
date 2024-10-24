package com.example.instagram.user.controller;

import com.example.instagram.user.domain.User;
import com.example.instagram.user.service.UserService;
import com.example.instagram.user.controller.dto.UserResponse;  // 경로 수정
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    // 사용자 정보 조회
    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUser(@PathVariable Long userId) {
        User user = userService.getUser(userId);
        return ResponseEntity.ok(UserResponse.from(user));
    }

    // 사용자명으로 조회
    @GetMapping("/username/{username}")
    public ResponseEntity<UserResponse> getUserByUsername(@PathVariable String username) {
        User user = userService.getUserByUsername(username);
        return ResponseEntity.ok(UserResponse.from(user));
    }

    // 이메일로 조회
    @GetMapping("/email/{email}")
    public ResponseEntity<UserResponse> getUserByEmail(@PathVariable String email) {
        User user = userService.getUserByEmail(email);
        return ResponseEntity.ok(UserResponse.from(user));
    }
}