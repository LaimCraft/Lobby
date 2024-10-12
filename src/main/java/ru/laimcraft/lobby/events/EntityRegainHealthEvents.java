package ru.laimcraft.lobby.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRegainHealthEvent;

public class EntityRegainHealthEvents implements Listener {
    @EventHandler
    private void onEntityRegainHealthEvent(EntityRegainHealthEvent event) {
        //event.setCancelled(true);
    }
}
