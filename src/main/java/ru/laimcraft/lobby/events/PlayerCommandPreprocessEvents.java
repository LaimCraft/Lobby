package ru.laimcraft.lobby.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import ru.laimcraft.lobby.Lobby;

import java.util.ArrayList;
import java.util.List;

public class PlayerCommandPreprocessEvents implements Listener {
    @EventHandler
    private void onPlayerCommandPreprocessEvent(PlayerCommandPreprocessEvent event) {
        if(Lobby.players.containsKey(event.getPlayer().getName())) return;
        String[] cmd = event.getMessage().split(" ");
        List<String> allowCommands = new ArrayList<>();
        allowCommands.add("/register");
        allowCommands.add("/reg");
        allowCommands.add("/login");
        allowCommands.add("/l");
        if(allowCommands.contains(cmd[0].toLowerCase())) return;
        event.setCancelled(true);
    }
}
