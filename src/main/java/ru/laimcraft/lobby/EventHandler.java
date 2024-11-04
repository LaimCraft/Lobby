package ru.laimcraft.lobby;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import ru.laimcraft.lobby.events.*;
import ru.laimcraft.lobby.events.inventory.onInventoryClickEvent;
import ru.laimcraft.lobby.events.player.*;

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
        register(new TestEvents());
        register(new NPCInteract());
        register(new onInventoryClickEvent());
        register(new onPlayerInteractEvent());
    }

    private void register(Listener listener) {
        Bukkit.getPluginManager().registerEvents(listener, lobby);
    }
}
