package ru.laimcraft.lobby.events.player;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

public class onPlayerDropItemEvent implements Listener {

    @EventHandler
    private void onPlayerDropItemEvent(PlayerDropItemEvent event) {
        event.setCancelled(true);
    }
}
