package com.demo.netty;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class NettyTestApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(NettyTestApplication.class, args);
    }


    @Async
    @Override
    public void run(String... args) throws Exception {
        /**
         * 使用异步注解方式启动netty服务端服务
         */
        new BootNettyServer().bind(8888);

    }



}
