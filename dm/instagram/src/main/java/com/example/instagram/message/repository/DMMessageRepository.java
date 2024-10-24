package com.example.instagram.message.repository;

import com.example.instagram.message.domain.DMMessage;
import com.example.instagram.dm.domain.DMRoom;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DMMessageRepository extends JpaRepository<DMMessage, Long> {
    List<DMMessage> findByDmRoomOrderByCreatedAtDesc(DMRoom dmRoom, Pageable pageable);

    // 읽지 않은 메시지 수를 조회하는 메서드 추가
    Long countByDmRoomAndCreatedAtGreaterThan(DMRoom dmRoom, LocalDateTime lastReadTime);
}