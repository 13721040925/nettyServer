package com.wdt.datacenter;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
 
public class SocketHandler extends ChannelInboundHandlerAdapter {
 
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        System.out.println("channel active>>>>>>>\n");
    }
 
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf message = (ByteBuf) msg;
        byte[] response = new byte[message.readableBytes()];
        message.readBytes(response);
        //System.out.println("<- " + new String(response) + "\n");
 
        String sendContent = "Response from server: \"" + new String(response) + "\"" ;
        ByteBuf seneMsg = Unpooled.buffer(sendContent.length());
        seneMsg.writeBytes(sendContent.getBytes());
 
        ctx.writeAndFlush(seneMsg);
        //System.out.println("send info to client:" + sendContent);
    }
 
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        cause.printStackTrace();
        ctx.close();
    }
}