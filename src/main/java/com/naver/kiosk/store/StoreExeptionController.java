package com.naver.kiosk.store;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class StoreExeptionController {
    @ExceptionHandler(StoreNotFound.class)
    public  ResponseEntity<?> runtimeExceptionHandler(StoreNotFound e){
        System.out.println(e.getMessage());
        return ResponseEntity.notFound().build();
    }
}
