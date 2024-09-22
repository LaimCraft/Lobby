package ru.laimcraft.lobby.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import ru.laimcraft.lobby.Message;
import ru.laimcraft.lobby.data.mysql.MySQLAccounts;

public class AsyncPlayerPreLoginEvents implements Listener {
    @EventHandler
    private void onAsyncPlayerPreLoginEvent(AsyncPlayerPreLoginEvent event) {
        String login = MySQLAccounts.getLogin(event.getName());
        switch (login) {
            case null:
                return;
            case "ex":
                event.kickMessage(Message.kickError);
                event.setLoginResult(AsyncPlayerPreLoginEvent.Result.KICK_OTHER);
                return;
            default:
                if(login.equals(event.getName())) return;
                event.kickMessage(Message.kickUsername(event.getName(), login));
                event.setLoginResult(AsyncPlayerPreLoginEvent.Result.KICK_OTHER);
                return;
        }
    }
}
