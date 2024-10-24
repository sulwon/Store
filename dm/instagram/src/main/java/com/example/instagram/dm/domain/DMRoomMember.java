package com.example.instagram.dm.domain;

import com.example.instagram.user.domain.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Table(name = "dm_room_members")
@Getter @Setter
@NoArgsConstructor
public class DMRoomMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private DMRoom dmRoom;

    // User 엔티티와의 관계는 유지 (마이크로서비스 간 통신은 서비스 계층에서 처리)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private LocalDateTime lastReadAt;

    public DMRoomMember(DMRoom dmRoom, User user) {
        this.dmRoom = dmRoom;
        this.user = user;
        this.lastReadAt = LocalDateTime.now();
    }
}