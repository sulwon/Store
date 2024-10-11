package com.naver.kiosk.kiosk;

import com.naver.kiosk.store.Store;
import com.naver.kiosk.kiosk.Utils;

public class Kiosk {
    private final int id;
    private int kioskNumber;
    private Store store;
    private boolean active;


    public Kiosk(Store store, int kioskNumber) {
        this.active = true;
        this.store = store;
        this.kioskNumber = kioskNumber;
        this.id =Utils.kioskCount++;
    }

    public int getId() {
        return id;
    }

    public int getKioskNumber() {
        return kioskNumber;
    }

    public Store getStore() {
        return store;
    }

    public boolean isActive() {
        return active;
    }
}
