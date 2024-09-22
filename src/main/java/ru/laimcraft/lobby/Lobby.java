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
    @Override
    public void onEnable() {
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
