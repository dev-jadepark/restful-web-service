package com.jade.restfulwebservice.user;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@AllArgsConstructor
//@JsonIgnoreProperties(value = {"password","ssn"})
@JsonFilter("UserInfo") //프로그래밍 제어 필터링
public class User {

    private Integer id;

    @Size(min=2, message = "Name은 2글자 이상 입력해 주세요.") //details에 message 반영된다.
    private String name;

    @Past
    private Date joinDate;

//    @JsonIgnore
    private String password;

//    @JsonIgnore
    private String ssn;

    /*
    스프링부트는 jackson을 사용하여 json데이터를 필터링 할 수 있다.
    @JsonIgnore 을 사용하면 클라이언트에서는 해당 필드를 확인 할 수 없다.
    or @JsonIgnoreProperties(value = {"password","ssn"})를 클래스에 어노테이션한다.
     */
}
