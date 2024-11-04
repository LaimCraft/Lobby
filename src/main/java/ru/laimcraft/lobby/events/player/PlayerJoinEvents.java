package ru.laimcraft.lobby.events.player;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import ru.laimcraft.lobby.AuthPlayer;
import ru.laimcraft.lobby.Lobby;
import ru.laimcraft.lobby.Message;
import ru.laimcraft.lobby.components.Locations;
import ru.laimcraft.lobby.data.mysql.MySQLAccounts;
import ru.laimcraft.lobby.data.mysql.SQLManager;
import ru.laimcraft.lobby.menu.Menu;

public class PlayerJoinEvents implements Listener {
    @EventHandler
    private void onPlayerJoinEvent(PlayerJoinEvent event) {
        Menu.getMenuItem(event.getPlayer());
        event.setJoinMessage(null);
        event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SPEED, PotionEffect.INFINITE_DURATION, 0, true, false));

        event.getPlayer().teleport(Locations.spawnLocation);
        if(SQLManager.isAuth(event.getPlayer().getName())) {
            Lobby.players.put(event.getPlayer().getName(), new AuthPlayer());
        return;}

        String login = MySQLAccounts.getLogin(event.getPlayer().getName());
        if(login == null) {
            event.getPlayer().sendMessage(Message.registerSendMessage);
            return;}
        event.getPlayer().sendMessage(Message.loginSendMessage);
    }
}
