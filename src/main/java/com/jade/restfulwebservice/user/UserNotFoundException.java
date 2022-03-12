package com.jade.restfulwebservice.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// HTTP Status code
// 2XX -> OK
// 4XX -> Client 문제
// 5XX -> Server 문제
@ResponseStatus(HttpStatus.NOT_FOUND)   //500번 상태코드도 404로 되어 예외페이지를 전달하게 한다.
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) { //예외클래스 작성
        super(message);
    }
}
