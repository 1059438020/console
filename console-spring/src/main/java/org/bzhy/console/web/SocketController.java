package org.bzhy.console.web;

import org.bzhy.console.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Author: 白振宇
 * @Date： 2019/4/1 17:18
 */
@Controller
public class SocketController {
    @Autowired
    TaskService taskService;

    @GetMapping(value = "/testSocket")
    public String testSocket() {
        taskService.cmdSocket("12345");
        return "success";
    }
}
