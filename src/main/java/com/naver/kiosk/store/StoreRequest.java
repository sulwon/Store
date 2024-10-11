package com.naver.kiosk.store;
// 모든 field 가 final 이고
public record StoreRequest
        (String name, String address, short openTime, short closeTime) {

    public Store toStore() {
        return new Store(name, address, openTime, closeTime);
    }
}

