package ru.laimcraft.lobby.rpc;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.bukkit.Bukkit;
import ru.laimcraft.lobby.AuthPlayer;
import ru.laimcraft.lobby.Lobby;

import java.net.SocketException;

@io.netty.channel.ChannelHandler.Sharable
public class ChannelHandler extends SimpleChannelInboundHandler<ByteBuf> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        try {
            RPC.ctx = ctx;
            RPC.sendMessage("binding Fw129efp2w8pdsj903jkopmgrs lobby");
        } catch (Exception ex) {
            Bukkit.getScheduler().runTask(Lobby.instance, () -> {
                Bukkit.getConsoleSender().sendMessage(ex.getMessage());
            });
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf byteBuf) {
        try {
            String msg = byteBuf.toString(io.netty.util.CharsetUtil.UTF_8);
            String[] args = msg.split(" ");

            switch (args[0]) {
                case "success":
                    Bukkit.getScheduler().runTask(Lobby.instance, () -> {
                        Bukkit.getConsoleSender().sendMessage("Connect to NettyServer success");
                    });
                    return;
                case "login":
                    String login = args[1];
                    Lobby.players.put(login, new AuthPlayer());
                    return;
                default:
                    return;
            }
        } catch (Exception ex) {
            Bukkit.getScheduler().runTask(Lobby.instance, () -> {
                Bukkit.getConsoleSender().sendMessage(ex.getMessage());
            });
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        try {
            return;
        } catch (Exception ex) {
            Bukkit.getScheduler().runTask(Lobby.instance, () -> {
                Bukkit.getConsoleSender().sendMessage(ex.getMessage());
            });
        }
    }
}
