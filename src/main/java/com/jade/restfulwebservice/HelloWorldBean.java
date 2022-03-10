package com.jade.restfulwebservice;
// lombok 빈클래스를 생성후 data 자동 생성 @Data

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data   //getter, setter
@AllArgsConstructor //생성자
@NoArgsConstructor  //default 생성자
public class HelloWorldBean {
    private String message;

}
