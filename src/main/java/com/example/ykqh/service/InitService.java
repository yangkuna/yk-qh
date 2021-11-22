package com.example.ykqh.service;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @author 杨昆
 * @date 2021/11/18 8:36
 * @describe 系统启动后的初始化操作
 */
@Component
public class InitService implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) {
        System.out.println("我来了");
    }
}