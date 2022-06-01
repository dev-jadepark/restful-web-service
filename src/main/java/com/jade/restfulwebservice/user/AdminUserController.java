package com.jade.restfulwebservice.user;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminUserController {
    @Autowired
    private UserDaoService userDaoService;  //인스턴스 생성, 선언된 빈을 스프링이 의존성 주입을 시킴, (스프링컨테이너, IOC컨테이너)

    @GetMapping("/users")
    public MappingJacksonValue retrieveAllUsers(){
        List<User> users = userDaoService.findAll();

        SimpleBeanPropertyFilter simpleBeanPropertyFilter = SimpleBeanPropertyFilter
                .filterOutAllExcept("id", "name", "joinDate", "password");   //SimpleBeanPropertyFilter를 사용하여 필터링이 가능

        FilterProvider filterProvider = new SimpleFilterProvider().addFilter("UserInfo", simpleBeanPropertyFilter);

        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(users);
        mappingJacksonValue.setFilters(filterProvider);


        return mappingJacksonValue;
    }

    /*
    버전관리방법에서 중요한 점
    - 통일성 없는 URI 지양
    - 잘못된 헤더값 사용 지양
    - 캐시관리
    - 웹브라우저에서 즉시실행 가능여부
    - 문서관리
     */

//    @GetMapping("/v1/users/{id}") //URI에 의한 버전관리 방법
//    @GetMapping(value = "/users/{id}", params="version=1")  //RequestParam에 의한 버전관리 방법
//    @GetMapping(value = "/users/{id}", headers = "X-API-VERSION=1") //headers 값에 의한 버전관리 방법
    @GetMapping(value = "/users/{id}", produces = "application/vnd.company.appv1+json")  //MIME 타입을 이용하는 방법
    public MappingJacksonValue retrieveUserV1(@PathVariable int id){
        User user = userDaoService.findOne(id);

        if(user == null){
            throw new UserNotFoundException(String.format("ID[%s] not found", id)); //아이디가 없으면 예외 발생
        }

        SimpleBeanPropertyFilter simpleBeanPropertyFilter = SimpleBeanPropertyFilter
                .filterOutAllExcept("id", "name", "joinDate", "password", "ssn");   //SimpleBeanPropertyFilter를 사용하여 필터링이 가능

        FilterProvider filterProvider = new SimpleFilterProvider().addFilter("UserInfo", simpleBeanPropertyFilter);

        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(user);
        mappingJacksonValue.setFilters(filterProvider);

        return mappingJacksonValue;
    }

    @GetMapping("/v2/users/{id}")
    public MappingJacksonValue retrieveUserV2(@PathVariable int id){
        User user = userDaoService.findOne(id);

        if(user == null){
            throw new UserNotFoundException(String.format("ID[%s] not found", id)); //아이디가 없으면 예외 발생
        }

        // User -> User2
        UserV2 userV2 = new UserV2();
        BeanUtils.copyProperties(user,userV2);  //id, name, joinDate, password, ssn
        userV2.setGrade("VIP");

        SimpleBeanPropertyFilter simpleBeanPropertyFilter = SimpleBeanPropertyFilter
                .filterOutAllExcept("id", "name", "joinDate", "password", "ssn", "grade");   //SimpleBeanPropertyFilter를 사용하여 필터링이 가능

        FilterProvider filterProvider = new SimpleFilterProvider().addFilter("UserInfoV2", simpleBeanPropertyFilter);

        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(userV2);
        mappingJacksonValue.setFilters(filterProvider);

        return mappingJacksonValue;
    }

}
