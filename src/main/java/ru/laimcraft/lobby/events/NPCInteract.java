package ru.laimcraft.lobby.events;

import net.citizensnpcs.api.event.NPCRightClickEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import ru.laimcraft.lobby.Lobby;
import ru.laimcraft.lobby.Utils;
import ru.laimcraft.lobby.rpc.RPC;

public class NPCInteract implements Listener {

    @EventHandler
    private void onNPCInteract(NPCRightClickEvent event) {
        if(event.getNPC().getId() == 1) {
            RPC.sendMessage(String.format("transfer %s roleplay", event.getClicker().getName()));
            return;
        } if(event.getNPC().getId() == 0) {
            RPC.sendMessage(String.format("transfer %s vanilla", event.getClicker().getName()));
            return;
        }
    }
}
