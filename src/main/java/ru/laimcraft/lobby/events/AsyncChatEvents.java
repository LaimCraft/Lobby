package ru.laimcraft.lobby.events;

import io.papermc.paper.event.player.AsyncChatEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import ru.laimcraft.lobby.Lobby;

public class AsyncChatEvents implements Listener {
    @EventHandler
    private void onAsyncChatEvent(AsyncChatEvent event) {
        if(isAuth(event.getPlayer().getName())) {
            event.setCancelled(true);
            return;}



    }
    private boolean isAuth(String player) {
        return !Lobby.players.containsKey(player);
    }
}
