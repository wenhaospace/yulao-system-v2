package com.wendell.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ZhangWenhao
 * @date 2026/1/6 09:22
 * 当配置了Prometheus监控时， 如果在浏览器访问监控接口， 浏览器会默认请求/favicon.ico图标， 从而会被程序捕获错误
 * 因此手动配置对应接口来消除错误
 */
@RestController
public class FaviconController {
    @GetMapping("favicon.ico")
    public ResponseEntity<Void> favicon() {
        return ResponseEntity.notFound().build();
    }
}
