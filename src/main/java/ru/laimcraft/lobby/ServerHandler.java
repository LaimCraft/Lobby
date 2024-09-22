package ru.laimcraft.lobby;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.bukkit.Bukkit;

public class ServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object object) throws Exception {
        Bukkit.getConsoleSender().sendMessage("String.valueOf(object): " + String.valueOf(object));
        String[] msg = String.valueOf(object).split(" ");
        switch (msg[0]) {
            case "close":
                Lobby.players.remove(msg[1]);
                /*if(Lobby.players.containsKey(msg[1])) {

                    //ctx.writeAndFlush(Unpooled.copiedBuffer(msg[1].getBytes()));
                    ctx.close();
                return;}*/
                //ctx.writeAndFlush(Unpooled.copiedBuffer("no".getBytes()));
                ctx.close();
                return;
            default:
                //ctx.writeAndFlush(Unpooled.copiedBuffer("no".getBytes()));
                ctx.close();
                break;
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
