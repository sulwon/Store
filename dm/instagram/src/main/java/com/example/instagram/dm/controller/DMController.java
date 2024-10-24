package com.example.instagram.dm.controller;

import com.example.instagram.dm.domain.DMRoom;
import com.example.instagram.dm.service.DMService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/dm")
@RequiredArgsConstructor
public class DMController {
    private final DMService dmService;

    // 채팅방 목록 조회
    @GetMapping
    public ResponseEntity<List<DMRoomResponse>> getDMRooms(
            @RequestParam("userId") Long userId
    ) {
        List<DMRoom> rooms = dmService.getUserDMRooms(userId);
        List<DMRoomResponse> responses = rooms.stream()
                .map(DMRoomResponse::from)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    // 채팅방 생성
    @PostMapping
    public ResponseEntity<DMRoomResponse> createDMRoom(
            @RequestBody CreateDMRoomRequest request
    ) {
        DMRoom room = dmService.createDMRoom(request.getUserId(), request.getOtherUserId());
        return ResponseEntity.ok(DMRoomResponse.from(room));
    }

    // 채팅방 나가기 기능
    @DeleteMapping("/{roomId}")
    public ResponseEntity<Void> leaveDMRoom(
            @PathVariable Long roomId,
            @RequestParam("userId") Long userId
    ) {
        dmService.leaveDMRoom(roomId, userId);
        return ResponseEntity.ok().build();
    }
}