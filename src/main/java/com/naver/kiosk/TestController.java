package com.naver.kiosk;

import org.springframework.web.bind.annotation.*;

@RestController
//@Controller + @ResponseBody = @RestController
public class TestController {
//   @RequestMapping(value ="/java" , method = RequestMethod.GET)
    @GetMapping("/java")
    public String java(){
        return"java";
    }
// get 메소드로 /name 으로 이름 출력하는 것 만들어보세요
    @GetMapping("/name")
    public String name(){
        return"name";
    }



}
