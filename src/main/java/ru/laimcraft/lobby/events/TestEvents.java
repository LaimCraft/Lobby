package ru.laimcraft.lobby.events;

import com.destroystokyo.paper.event.player.PlayerJumpEvent;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import ru.laimcraft.lobby.components.Locations;

public class TestEvents implements Listener {
    Location location;

    @EventHandler
    private void eventJump(PlayerToggleSneakEvent event) {
        //event.getPlayer().sendMessage("sneak");
    }

    @EventHandler
    private void onPlayerMoveToJump(PlayerMoveEvent event) {

    }

    @EventHandler
    private void onMovement(PlayerMoveEvent event) {
        if(event.getTo().getBlock().getType() != Material.WATER) return;
        event.getPlayer().teleport(Locations.spawnLocation);
    }

    @EventHandler
    private void onJump(PlayerJumpEvent event) {
        if(spawnJumpTpLocation(event)) {
            //event.setCancelled(true);
            return;
        }
        if(spawnJumpTpBackLocation(event)) {
            //event.setCancelled(true);
            return;
        }
    }

    private boolean spawnJumpTpLocation(PlayerJumpEvent event){
        if(event.getFrom().getBlock() == null) return false;
        Block block = event.getFrom().getBlock();
        if(block.getX() < 236 || block.getX() > 241) return false;
        if(block.getY() != 63) return false;
        if(block.getZ() < 252 || block.getZ() > 254) return false;
        event.getPlayer().teleport(Locations.spawnJumpTpLocation);
        return true;
    }

    private boolean spawnJumpTpBackLocation(PlayerJumpEvent event){
        if(event.getFrom().getBlock() == null) return false;
        Block block = event.getFrom().getBlock();
        if(block.getX() < 196 || block.getX() > 201) return false;
        if(block.getY() != 67) return false;
        if(block.getZ() < 252 || block.getZ() > 254) return false;
        event.getPlayer().teleport(Locations.spawnJumpTpBackLocation);
        return true;
    }
}
