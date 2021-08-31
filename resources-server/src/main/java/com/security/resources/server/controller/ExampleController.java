package com.security.resources.server.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p></p>
 * <p></p>
 *
 * @author 王森明
 * @date 2021/6/4 11:22
 * @since 1.0.0
 */
@RestController
@RequestMapping("/demo/api")
public class ExampleController {

    @PreAuthorize("hasAuthority('admin')")
    @PostMapping("/admin/hello")
    public String helloAdmin() {
        return "Hello,Admin!";
    }

    @PreAuthorize("hasAuthority('user')")
    @PostMapping("/user/hello")
    public String helloUser() {
        return "Hello,User!";
    }

    @PostMapping("/hello")
    public String hello() {
        return "Hello!";
    }
}
