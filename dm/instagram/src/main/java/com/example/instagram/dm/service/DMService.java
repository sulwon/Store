package com.example.instagram.dm.service;

import com.example.instagram.dm.domain.DMRoom;
import com.example.instagram.dm.domain.DMRoomMember;
import com.example.instagram.dm.repository.DMRoomRepository;
import com.example.instagram.dm.repository.DMRoomMemberRepository;
import com.example.instagram.user.domain.User;
import com.example.instagram.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class DMService {
    private final DMRoomRepository dmRoomRepository;
    private final DMRoomMemberRepository dmMemberRepository;
    private final UserRepository userRepository;

    // DM 방 생성
    public DMRoom createDMRoom(Long userId1, Long userId2) {
        User user1 = userRepository.findById(userId1)
                .orElseThrow(() -> new RuntimeException("User not found: " + userId1));
        User user2 = userRepository.findById(userId2)
                .orElseThrow(() -> new RuntimeException("User not found: " + userId2));

        // 이미 존재하는 채팅방 확인
        Optional<DMRoom> existingRoom = dmRoomRepository
                .findByMembersUserInAndMembersUserIn(
                        List.of(user1),
                        List.of(user2)
                );

        if (existingRoom.isPresent()) {
            return existingRoom.get();
        }

        // 새 채팅방 생성
        DMRoom dmRoom = new DMRoom();
        dmRoom = dmRoomRepository.save(dmRoom);

        // 멤버 추가
        dmMemberRepository.save(new DMRoomMember(dmRoom, user1));
        dmMemberRepository.save(new DMRoomMember(dmRoom, user2));

        return dmRoom;
    }

    // 사용자의 DM 방 목록 조회
    @Transactional(readOnly = true)
    public List<DMRoom> getUserDMRooms(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found: " + userId));
        return dmRoomRepository.findByMembersUserOrderByUpdatedAtDesc(user);
    }

    // DM 방 나가기
    public void leaveDMRoom(Long userId, Long roomId) {
        DMRoom room = dmRoomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Room not found: " + roomId));

        DMRoomMember member = dmMemberRepository.findByDmRoomAndUser(
                room,
                userRepository.getReferenceById(userId)
        ).orElseThrow(() -> new RuntimeException("Member not found"));

        dmMemberRepository.delete(member);
    }
}