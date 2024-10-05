package ru.laimcraft.lobby;

import io.papermc.paper.event.player.AsyncChatEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import ru.laimcraft.lobby.events.*;

public class EventHandler implements Listener {
    private final Lobby lobby;
    public EventHandler(Lobby lobby) {
        this.lobby = lobby;
        register(new AsyncPlayerPreLoginEvents());
        register(new PlayerCommandPreprocessEvents());
        register(new PlayerJoinEvents());
        register(new AsyncChatEvents());
        register(new PlayerQuitEvents());
        register(new PlayerDamageEvents());
        register(new EntityRegainHealthEvents());
        register(new FoodLevelChangeEvents());
    }

    private void register(Listener listener) {
        Bukkit.getPluginManager().registerEvents(listener, lobby);
    }
}
