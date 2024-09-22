package ru.laimcraft.lobby;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import ru.laimcraft.lobby.Commands.ChangePasswordCommand;
import ru.laimcraft.lobby.Commands.LoginCommand;
import ru.laimcraft.lobby.Commands.RegisterCommand;

import java.util.HashMap;
import java.util.Objects;

public final class Lobby extends JavaPlugin {
    public static HashMap<String, AuthPlayer> players = new HashMap<>();
    public static ServerPack serverPack;
    public static ClientPack clientPack;
    @Override
    public void onEnable() {
        Thread thread = new Thread(() -> {
            // code to be executed in the separate thread

        });
        thread.start();
        try {
            serverPack = new ServerPack();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        try {
            clientPack = new ClientPack();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        new EventHandler(this);
        Objects.requireNonNull(getCommand("login")).setExecutor(new LoginCommand());
        Objects.requireNonNull(getCommand("register")).setExecutor(new RegisterCommand());
        Objects.requireNonNull(getCommand("changepassword")).setExecutor(new ChangePasswordCommand());
    }

    @Override
    public void onDisable() {
        for(Player player : Bukkit.getOnlinePlayers()) {
            player.kick(Message.reloading);
        }
    }
}
