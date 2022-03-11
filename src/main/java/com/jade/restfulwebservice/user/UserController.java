package com.jade.restfulwebservice.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    private UserDaoService userDaoService;  //인스턴스 생성, 선언된 빈을 스프링이 의존성 주입을 시킴, (스프링컨테이너, IOC컨테이너)

    //생성자를 통한 의존성주입
    public UserController(UserDaoService userDaoService){
        this.userDaoService = userDaoService;
    }

    @GetMapping("/users")
    public List<User> retrieveAllUsers(){
        return userDaoService.findAll();
    }

    // GET /users/1 or /users/10
    @GetMapping("/users/{id}")
    public User retrieveUser(@PathVariable int id){
        return userDaoService.findOne(id);
    }

    @PostMapping("/users")
    public void createUser(@RequestBody User user){   //@RequestBody, 매개변수에 유저 도메인을 body에 실어서 POST한다..
        User savedUser = userDaoService.save(user);
    }

    //매핑주소는 똑같아도 메서드에 따라 다른 결과를 만들어 낼 수 있다.

}
