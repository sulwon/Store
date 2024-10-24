// dm/controller/dto/CreateDMRoomRequest.java
package com.example.instagram.dm.controller.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
public class CreateDMRoomRequest {
    private Long userId;
    private Long otherUserId;
}