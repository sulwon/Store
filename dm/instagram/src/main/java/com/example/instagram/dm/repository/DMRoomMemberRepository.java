package com.example.instagram.dm.repository;

import com.example.instagram.dm.domain.DMRoom;
import com.example.instagram.dm.domain.DMRoomMember;
import com.example.instagram.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DMRoomMemberRepository extends JpaRepository<DMRoomMember, Long> {
    Optional<DMRoomMember> findByDmRoomAndUser(DMRoom dmRoom, User user);  // 기존 메서드

    // userId로 찾는 메서드 추가
    Optional<DMRoomMember> findByDmRoomAndUserId(DMRoom dmRoom, Long userId);
}