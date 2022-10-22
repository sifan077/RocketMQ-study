package com.sifan;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class MQTestController {

    @Resource
    private SpringProducer springProducer;

    @GetMapping("/sendMessage")
    public String sendMessage(@RequestParam("message") String message){
        springProducer.sendMessage("demo-topic", message);
        return "消息发送完成";
    }


}