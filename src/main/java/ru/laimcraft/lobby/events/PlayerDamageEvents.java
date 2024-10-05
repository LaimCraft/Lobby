package ru.laimcraft.lobby.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDamageEvents implements Listener {
    @EventHandler
    private void onPlayerDamageEvent(PlayerDeathEvent event) {
        event.setCancelled(true);
    }
}
