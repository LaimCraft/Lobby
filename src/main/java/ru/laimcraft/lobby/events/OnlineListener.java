package ru.laimcraft.lobby.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.ServerLoadEvent;
import ru.laimcraft.lobby.Online;

public class OnlineListener implements Listener {

    @EventHandler
    private void onJoin(PlayerJoinEvent event) {
    }

    @EventHandler
    private void onQuit(PlayerQuitEvent event) {
    }

    @EventHandler
    private void onReload(ServerLoadEvent event) {
    }
}
