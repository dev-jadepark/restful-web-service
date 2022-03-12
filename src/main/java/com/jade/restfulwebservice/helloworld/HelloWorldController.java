package com.jade.restfulwebservice.helloworld;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
public class HelloWorldController {

    @Autowired
    private MessageSource messageSource;    //빈을 자동 주입

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

    //@PathVariable 가변변수 형식 {s}에 데이터를 담는다.
    @GetMapping("/hello-world-bean/path-variable/{name}")
    public HelloWorldBean helloWorldBean(@PathVariable String name){
        return new HelloWorldBean(String.format("Hello World, %s", name));
    }

    @GetMapping("/hello-world-internationalized")
    public String helloWorldInternationalized(@RequestHeader(name="Accept-Language", required = false) Locale locale){
        //@RequestHeaders에 의해 Locale 전달
        return messageSource.getMessage("greeting.message",null,locale);
    }

}
