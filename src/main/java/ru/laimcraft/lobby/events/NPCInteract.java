package ru.laimcraft.lobby.events;

import net.citizensnpcs.api.event.NPCRightClickEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import ru.laimcraft.lobby.Lobby;
import ru.laimcraft.lobby.Utils;

public class NPCInteract implements Listener {

    @EventHandler
    private void onNPCInteract(NPCRightClickEvent event) {
        if(event.getNPC().getId() == 1) {
            Utils.sendTransferMessage(Lobby.instance, event.getClicker(), "roleplay");
            return;
        }         if(event.getNPC().getId() == 0) {
            Utils.sendTransferMessage(Lobby.instance, event.getClicker(), "vanilla");
            return;
        }
    }
}
