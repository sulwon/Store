package com.naver.kiosk.kiosk;

import com.naver.kiosk.store.Store;

public class KioskRequest {
    private final int kioskNumber;
    private final Store store;

    public KioskRequest(int kioskNumber, Store store) {
        this.kioskNumber = kioskNumber;
        this.store = store;
    }

    public Kiosk toKiosk() {
        return new Kiosk(store, kioskNumber); // 매개변수 순서 수정
    }
}