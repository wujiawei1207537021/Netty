package com.demo.netty.handle.client;

import com.demo.netty.client.EchoClient;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 * <p> 客户端自定义拦截器 </p>
 *
 * @author: zhangqi
 * @create: 2019/12/02 08:45
 */
@Slf4j
public class ClientCustomHandle extends ChannelInboundHandlerAdapter {

    private String host;

    private int port;

    /**
     * 重连次数
     */
    public int count;

    public ClientCustomHandle(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        log.info("{}客户端已连接", ctx.name());
        count = 3;
        log.info("开启从链路连接{}", ctx.channel().id().toString());
        super.channelRegistered(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("{}客户端关闭", ctx.name());
        if (count != 0) {
            EchoClient client = new EchoClient();
            try {
                client.start(host, port);
                count--;
            } catch (Exception e) {
                log.error("从链路尝试重连失败", e.getMessage());
            }
        }
        if (count == 0) {
            super.channelInactive(ctx);
        }
    }

}
