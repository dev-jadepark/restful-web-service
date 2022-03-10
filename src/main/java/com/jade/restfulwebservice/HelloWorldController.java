package com.jade.restfulwebservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {
    /*
    GET
   /hello-world (endpoint)
     */
    //@RequestMapping(method=RequestMethod.GET, path="/hello-world")
    @GetMapping(path="/hello-world")
    public String helloWorld(){
        return "hello World";
    }

    //빈생성방법 option + enter
    @GetMapping("/hello-world-bean")
    public HelloWorldBean helloWorldBean(){
        return new HelloWorldBean("Hello World");
        //자바빈 형태로 반환하게 되면 json형태로 반환하게 된다.
        //{"message":"Hello World"}
    }

    /*
    > RestController
    >
    > - Spring4부터 지원
    > - @Controller + @ResponseBody
    > - view를 갖지 않는 REST Data(JSON/XML)를 반환
     */

}
