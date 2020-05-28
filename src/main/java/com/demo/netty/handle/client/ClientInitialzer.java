package com.demo.netty.handle.client;

import com.demo.netty.protocol.EncodeAdapter;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;

/**
 * <p> 消息通道初始化类 </p>
 *
 * @author zhangqi
 * @date 2019-11-27 15:20
 */
public class ClientInitialzer extends ChannelInitializer<NioSocketChannel> {

    private String host;

    private int port;


    public ClientInitialzer(String host, int port){
        this.host = host;
        this.port = port;
    }

    @Override
    protected void initChannel(NioSocketChannel channel) throws Exception {
        //  tcp粘包处理 这里采用分隔符解码器 以数据包结尾 0x5d作为分隔
        ByteBuf delimiter = Unpooled.copiedBuffer(new byte[]{0x5d});
        channel.pipeline().addLast(new DelimiterBasedFrameDecoder(Integer.MAX_VALUE, false, delimiter));
        //  心跳监测
        channel.pipeline().addLast(new ClientCustomHandle(host,port));
        //  数据包解码器
//        channel.pipeline().addLast(new Jt809DecoderAdapter(argIndex));
        //  各类数据包解析器 后续其他业务/资业务响应数据包解析扩展

        //  数据包编码器
        channel.pipeline().addLast(new EncodeAdapter());
    }
}
