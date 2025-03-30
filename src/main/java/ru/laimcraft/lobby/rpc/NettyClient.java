package ru.laimcraft.lobby.rpc;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.bukkit.Bukkit;
import ru.laimcraft.lobby.Lobby;

public class NettyClient {

    public static Bootstrap bootstrap;
    public static EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
    public static ChannelFuture channelFuture;

    public static void connect() {
        try {
            bootstrap = new Bootstrap();
            bootstrap.group(eventLoopGroup);
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.handler(new ChannelHandler());

            channelFuture = bootstrap.connect("127.0.0.1", 25555).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            Bukkit.getConsoleSender().sendMessage("NettyClient error: " + e.getMessage());
        } finally {
            reconnect();
        }
    }

    public static void reconnect() {
        try {
            Thread.sleep(1000L);
            connect();
        } catch (InterruptedException e) {
            return;
        }
    }
}
