package com.example.demo.netty.chat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;

@Component
public class CharServerApplication {
	
	@Autowired
	@Qualifier("bootstrap")
	private ServerBootstrap serverBootstrap;
	
	private Channel channel;
	
	public void start() throws InterruptedException {
		System.err.println("Netty启动");
		channel = serverBootstrap.bind(8888).sync().channel().closeFuture().channel();
	}
	
	public void close() {
		channel.close();
		channel.parent().close();
	}
}
