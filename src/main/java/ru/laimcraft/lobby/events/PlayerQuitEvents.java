package ru.laimcraft.lobby.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import ru.laimcraft.lobby.Lobby;
import ru.laimcraft.lobby.data.mysql.SQLManager;

public class PlayerQuitEvents implements Listener {
    @EventHandler
    private void onPlayerQuitEvent(PlayerQuitEvent event) {
        //Lobby.players.remove(event.getPlayer().getName());
    }
}
