package com.example.instagram.dm.repository;

import com.example.instagram.dm.domain.DMRoom;
import com.example.instagram.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DMRoomRepository extends JpaRepository<DMRoom, Long> {
    // 특정 사용자가 참여한 채팅방 목록 조회
    List<DMRoom> findByMembersUserOrderByUpdatedAtDesc(User user);

    // 두 사용자간의 채팅방 찾기
    Optional<DMRoom> findByMembersUserInAndMembersUserIn(
            List<User> user1,
            List<User> user2
    );
}