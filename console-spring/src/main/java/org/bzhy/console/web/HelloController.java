package org.bzhy.console.web;

import org.bzhy.console.comm.GuardSocket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @Author: 白振宇
 * @Date： 2019/4/1 20:19
 */
@RestController
public class HelloController {
    @Autowired
    GuardSocket guardSocket;

    @GetMapping(value = "/hello")
    public String hello() throws UnknownHostException {
        guardSocket.joinPool("cmd /c notepad", InetAddress.getByName("127.0.0.1"));
        return "hello";
    }
}
