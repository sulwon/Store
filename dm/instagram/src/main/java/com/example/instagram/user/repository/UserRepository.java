package com.example.instagram.user.repository;

import com.example.instagram.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // 사용자명으로 사용자 찾기
    Optional<User> findByUsername(String username);

    // 이메일로 사용자 찾기
    Optional<User> findByEmail(String email);

    // 사용자명이나 이메일이 존재하는지 확인
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}