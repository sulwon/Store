package com.naver.kiosk.store;

public class StoreNotFound extends IllegalArgumentException{
    public StoreNotFound(int id) {
        super(id + " IS NOT FOUND!!");
    }
}
