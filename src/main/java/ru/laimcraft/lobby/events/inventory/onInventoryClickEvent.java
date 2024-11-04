package ru.laimcraft.lobby.events.inventory;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import ru.laimcraft.lobby.Lobby;
import ru.laimcraft.lobby.Utils;
import ru.laimcraft.lobby.menu.Menu;

public class onInventoryClickEvent implements Listener {

    @EventHandler
    private void onInventoryClickEvent(InventoryClickEvent event) {
        if(event.getCurrentItem() == null) return;
        if(event.getCurrentItem().getPersistentDataContainer().get(Menu.menuKey, PersistentDataType.BOOLEAN) == null) return;
        event.setCancelled(true);
        serverTeleport(event);
    }

    private void serverTeleport(InventoryClickEvent event) {
        String server = event.getCurrentItem().getPersistentDataContainer().get(Menu.menuServerTeleport, PersistentDataType.STRING);
        if(server == null || server.isEmpty()) return;
        Utils.sendTransferMessage(Lobby.instance, (Player) event.getWhoClicked(), server);
    }
}
