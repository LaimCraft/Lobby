package ru.laimcraft.lobby.events.player;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import ru.laimcraft.lobby.Commands.LoginCommand;
import ru.laimcraft.lobby.Commands.RegisterCommand;
import ru.laimcraft.lobby.Lobby;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PlayerCommandPreprocessEvents implements Listener {

    public static List<String> loginCommand = new ArrayList<>();
    public static List<String> registerCommand = new ArrayList<>();

    static {
        loginCommand.add("/l");
        loginCommand.add("/login");
        registerCommand.add("/reg");
        registerCommand.add("/register");
    }

    @EventHandler (priority = EventPriority.HIGHEST)
    private void onPlayerCommandPreprocessEvent(PlayerCommandPreprocessEvent event) {
        if(Lobby.players.containsKey(event.getPlayer().getName())) return;
        String[] cmd = event.getMessage().split(" ");
        if(loginCommand.contains(cmd[0].toLowerCase()) || registerCommand.contains(cmd[0].toLowerCase())) return;
        event.setCancelled(true);
    }
}
