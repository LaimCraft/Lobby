package ru.laimcraft.lobby.events.player;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import ru.laimcraft.lobby.menu.Menu;

public class onPlayerInteractEvent implements Listener {

    @EventHandler
    private void onPlayerInteractEvent(PlayerInteractEvent event) {
        if(!event.getAction().isRightClick()) return;
        Menu.open(event.getPlayer());
    }
}
