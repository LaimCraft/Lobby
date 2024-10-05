package ru.laimcraft.lobby.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import ru.laimcraft.lobby.AuthPlayer;
import ru.laimcraft.lobby.Lobby;
import ru.laimcraft.lobby.Message;
import ru.laimcraft.lobby.data.mysql.MySQLAccounts;
import ru.laimcraft.lobby.data.mysql.SQLManager;

public class PlayerJoinEvents implements Listener {
    @EventHandler
    private void onPlayerJoinEvent(PlayerJoinEvent event) {
        event.setJoinMessage(null);
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
