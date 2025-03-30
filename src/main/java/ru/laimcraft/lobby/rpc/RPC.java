package ru.laimcraft.lobby.rpc;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import java.nio.charset.StandardCharsets;

public class RPC {

    public static ChannelHandlerContext ctx = null;

    public static void sendMessage(String message) {
        if (ctx == null || ctx.channel() == null) return;

        byte[] bytes = message.getBytes(StandardCharsets.UTF_8);

        ByteBuf byteBuf = ctx.alloc().buffer(bytes.length);
        byteBuf.writeBytes(bytes);

        ctx.writeAndFlush(byteBuf);
    }

    public static void sendMessage(ByteBuf byteBuf) {
        if (ctx == null || ctx.channel() == null) return;

        ctx.writeAndFlush(byteBuf);
    }

}
