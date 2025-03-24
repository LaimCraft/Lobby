package ru.laimcraft.lobby;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import ru.laimcraft.lobby.Commands.ChangePasswordCommand;
import ru.laimcraft.lobby.Commands.EcoCommand;
import ru.laimcraft.lobby.Commands.LoginCommand;
import ru.laimcraft.lobby.Commands.RegisterCommand;
import ru.laimcraft.lobby.net.SimpleServer;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

public final class Lobby extends JavaPlugin {
    public static HashMap<String, AuthPlayer> players = new HashMap<>();
    public static Lobby instance;
    @Override
    public void onEnable() {
        instance = this;
        new Online(); // Регистрация асинхронного обновления онлайна
        new EventHandler(this);
        getServer().getMessenger().registerOutgoingPluginChannel(this, "server:transfer");
        getServer().getMessenger().registerOutgoingPluginChannel(this, "server:login");
        Objects.requireNonNull(getCommand("login")).setExecutor(new LoginCommand());
        Objects.requireNonNull(getCommand("register")).setExecutor(new RegisterCommand());
        Objects.requireNonNull(getCommand("changepassword")).setExecutor(new ChangePasswordCommand());
        Objects.requireNonNull(getCommand("eco")).setExecutor(new EcoCommand());
    }

    @Override
    public void onDisable() {
        for(Player player : Bukkit.getOnlinePlayers()) {
            if(player.getName().equals("limeworld")) continue;
            player.kick(Message.reloading);
        }
    }
}
