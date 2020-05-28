package com.demo.netty.client;

import com.demo.netty.client.packet.LinkRegistrationMessage;
import com.demo.netty.handle.client.ClientInitialzer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.bytes.ByteArrayEncoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

/**
 * @Description
 * @Author 吴佳伟
 * @Date 2020-05-28 10:37 上午
 */
@Slf4j
public class EchoClient {

    private static Channel channel;

    private final String host;
    private final int port;

    public EchoClient() {
        this(0);
    }

    public EchoClient(int port) {
        this("localhost", port);
    }

    public EchoClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void start() throws Exception {
        start(this.host, this.port);
    }

    public void start(String host, int port) throws Exception {
        if (channel != null && channel.isActive()) {
            return;
        }
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group) // 注册线程池
                    .channel(NioSocketChannel.class) // 使用NioSocketChannel来作为连接用的channel类
                    .remoteAddress(new InetSocketAddress(host, port)) // 绑定连接端口和host信息
                    .handler(new ChannelInitializer<SocketChannel>() { // 绑定连接初始化器
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            System.out.println("正在连接中...");
                            ch.pipeline().addLast(new StringEncoder(Charset.forName("GBK")));
                            ch.pipeline().addLast(new EchoClientHandler());
                            ch.pipeline().addLast(new ByteArrayEncoder());
                            ch.pipeline().addLast(new ChunkedWriteHandler());
                            ch.pipeline().addLast(new ClientInitialzer(host,port));

                        }
                    });
            // System.out.println("服务端连接成功..");

            ChannelFuture cf = b.connect().sync(); // 异步连接服务器
            cf.addListener((ChannelFutureListener) listener -> {
                if (listener.isSuccess()) {
                    channel = listener.channel();
                    log.info("Connect to server successfully!");
                } else {
                    log.info("Failed to connect to server, try connect after 2s");
                    listener.channel().eventLoop().schedule(() -> {
                        try {
                            start();
                        } catch (Exception e) {
                            log.error("从链路重连失败{}", e.getMessage());
                        }
                    }, 2, TimeUnit.SECONDS);
                }
            });
            System.out.println("服务端连接成功..."); // 连接完成
            channel = cf.channel();
            //  发送从链路连接保持消息
            subConnect();

            cf.channel().closeFuture().sync(); // 异步等待关闭连接channel
            System.out.println("连接已关闭.."); // 关闭完成

        } finally {
            group.shutdownGracefully().sync(); // 释放线程池资源
        }
    }

    public static void main(String[] args) throws Exception {
        new EchoClient("218.5.80.28", 7810).start(); // 连接127.0.0.1/65535，并启动
//        new EchoClient("127.0.0.1",8888).start();

    }

    /**
     * 从链路建立连接
     */
    public void subConnect() {
        //  从链路
        log.info("发送请求");
        LinkRegistrationMessage linkRegistrationMessage=new LinkRegistrationMessage();
        channel.writeAndFlush(linkRegistrationMessage);
        while (true) {
            try {
                log.info("发送心跳❤️请求");
                //  每隔1s发送一次心跳包给从链路
//                Jt809SubHeartbeatPacket heartbeatPacket = new Jt809SubHeartbeatPacket(argIndex);
//                channel.writeAndFlush("heartbeatPacket");

//                LinkRegistrationMessage linkRegistrationMessage = new LinkRegistrationMessage();
                channel.writeAndFlush(linkRegistrationMessage);
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                log.error(e.getMessage());
            }
        }
    }
}
