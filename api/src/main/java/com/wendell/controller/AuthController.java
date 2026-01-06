package com.wendell.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.wendell.entity.Note;
import com.wendell.entity.go.ApiResponse;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author ZhangWenhao
 * @date 2026/1/6 17:24
 */
@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthController {

    @GetMapping("/doLogin")
    public ApiResponse<String> doLogin(String username, String password){
        // 此处仅作模拟示例，真实项目需要从数据库中查询数据进行比对
        if("zhang".equals(username) && "123456".equals(password)) {
            StpUtil.login(10001);
            return ApiResponse.ok("登录成功");
        }
        return ApiResponse.error(301,"登录失败");
    }

    // 查询登录状态，浏览器访问： http://localhost:8081/user/isLogin
    @GetMapping("isLogin")
    public ApiResponse<String> isLogin() {
        return ApiResponse.ok( "用户登录状态为： " + StpUtil.isLogin());
    }

}
