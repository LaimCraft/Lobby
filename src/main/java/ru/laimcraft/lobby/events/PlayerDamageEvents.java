package ru.laimcraft.lobby.events;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDamageEvents implements Listener {
    @EventHandler
    private void onPlayerDamageEvent(PlayerDeathEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    private void onPlayerDamageEvent(EntityDamageEvent event) {
        if((event.getEntityType() != EntityType.PLAYER) || event.getEntityType() == null) return;
        event.setCancelled(true);
    }
}
