package com.jade.restfulwebservice.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
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
        User user = userDaoService.findOne(id);

        if(user == null){
            throw new UserNotFoundException(String.format("ID[%s] not found", id)); //아이디가 없으면 예외 발생
        }

        return user;
    }

    //상태코드 201
    @PostMapping("/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user){
        //@RequestBody, 매개변수에 유저 도메인을 body에 실어서 POST한다..
        //@Valid : 사용자로부터 /users POST 가 실행되면 Validation sync가 진행된다.
        //User도메인에 @Size(min=2) 이기 때문에 이름에 N 한글자만 입력하게 되면 400번 Bad Request 발생
        User savedUser = userDaoService.save(user);

        //사용자에게 요청값을 반환
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()//현재 요청된 요청값을 사용한다라는 의미
                .path("/{id}")//반환값
                .buildAndExpand(savedUser.getId())
                .toUri();   //URI형태로 변환
        return ResponseEntity.created(location).build();
    }

    //매핑주소는 똑같아도 메서드에 따라 다른 결과를 만들어 낼 수 있다.
    //REST에서 가장 안좋은 것이 get/post로만 하는 방식
    //용도에 맞추어 설계하는 것이 가장 바람직하다.

    @DeleteMapping("/users/{id}")   //200ok
    public void deleteUser(@PathVariable int id){
        User user = userDaoService.deleteById(id);

        if (user == null) {
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }
    }
}
