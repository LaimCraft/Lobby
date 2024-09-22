package ru.laimcraft.lobby.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import ru.laimcraft.lobby.Message;
import ru.laimcraft.lobby.data.mysql.MySQLAccounts;

public class PlayerJoinEvents implements Listener {
    @EventHandler
    private void onPlayerJoinEvent(PlayerJoinEvent event) {
        String login = MySQLAccounts.getLogin(event.getPlayer().getName());
        if(login == null) {
            event.getPlayer().sendMessage(Message.registerSendMessage);
            return;}
        event.getPlayer().sendMessage(Message.loginSendMessage);
    }
}
