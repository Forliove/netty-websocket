package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.example.demo.netty.chat.CharServerApplication;

@SpringBootApplication
public class NettyChatApplication {

	public static void main(String[] args) throws InterruptedException {
		ConfigurableApplicationContext context = SpringApplication.run(NettyChatApplication.class, args);
		CharServerApplication charServerApplication = context.getBean(CharServerApplication.class);
		charServerApplication.start();
	}

}
