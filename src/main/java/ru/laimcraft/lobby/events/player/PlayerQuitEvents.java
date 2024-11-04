package ru.laimcraft.lobby.events.player;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import ru.laimcraft.lobby.Lobby;

public class PlayerQuitEvents implements Listener {
    @EventHandler
    private void onPlayerQuitEvent(PlayerQuitEvent event) {
        event.setQuitMessage(null);
        Lobby.players.remove(event.getPlayer().getName());
    }
}
