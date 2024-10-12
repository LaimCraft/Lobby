package ru.laimcraft.lobby.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class FoodLevelChangeEvents implements Listener {
    @EventHandler
    private void onFoodLevelChangeEvent(FoodLevelChangeEvent event) {
        //event.setCancelled(true);
    }
}
