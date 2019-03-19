package com.example.demo.netty.chat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

@Configuration
public class NettyServer {
	
	@Autowired
	WebSocketHandler WebSocketHandler;
	
	@Bean
	public NioEventLoopGroup parentGroup() {
		return new NioEventLoopGroup();
	}
	
	@Bean
	public NioEventLoopGroup childGroup() {
		return new NioEventLoopGroup();
	}
	
	@Bean(name="bootstrap")
	public ServerBootstrap bootstrap() {
		ServerBootstrap serverBootstrap = new ServerBootstrap();
		serverBootstrap.group(parentGroup(), childGroup()).channel(NioServerSocketChannel.class)
		.childHandler(new ChannelInitializer<SocketChannel>() {
			@Override
			protected void initChannel(SocketChannel socketChannel) throws Exception {
				//将请求和应答的消息编码解码为http的消息
				socketChannel.pipeline().addLast(new HttpServerCodec());
				//向客户端发送html5的文件
				socketChannel.pipeline().addLast(new ChunkedWriteHandler());
				//将多条信息整合为一条
				socketChannel.pipeline().addLast(new HttpObjectAggregator(65536));
				//
				socketChannel.pipeline().addLast(new WebSocketServerProtocolHandler("/websocket"));
				//socketChannel.pipeline().addLast(WebSocketHandler);
			}
			
		}).option(ChannelOption.SO_BACKLOG, 100).childOption(ChannelOption.SO_KEEPALIVE, true);
		return serverBootstrap;
	}
}
