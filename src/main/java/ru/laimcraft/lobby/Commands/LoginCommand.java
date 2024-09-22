package ru.laimcraft.lobby.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import ru.laimcraft.lobby.AuthPlayer;
import ru.laimcraft.lobby.Lobby;
import ru.laimcraft.lobby.Message;
import ru.laimcraft.lobby.data.mysql.MySQLAccounts;
import ru.laimcraft.lobby.data.mysql.SQLManager;

public class LoginCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String s, String[] args) {
        if(!(sender instanceof Player player)) return true;
        if(Lobby.players.containsKey(player.getName())) return true;
        if(args.length >= 1) {
            if(args[0] == null || args[0].isEmpty()) return true;
            if(args[0].length() > 48) {
                player.sendMessage(Message.passwordMaxLength);
            return true;}
            String login = MySQLAccounts.getLogin(player.getName());
            switch (login) {
                case null:
                    player.sendMessage(Message.noRegister);
                    player.sendMessage(Message.registerSendMessage);
                    return true;
                case "ex":
                    player.kick(Message.kickError);
                    return true;
                default:
                    break;}
            short result = MySQLAccounts.auth(player.getName(), args[0]);
            switch (result) {
                case 1:
                    player.sendMessage(Message.auth);
                    Lobby.players.put(player.getName(), new AuthPlayer());
                    SQLManager.add(player);
                    return true;
                case 0, -1:
                    player.kick(Message.noPassword);
                    return true;
                case -2:
                    player.kick(Message.kickError);
                    return true;
                default:
                    return true;
            }
        }
        player.sendMessage(Message.loginSendMessage);
        return true;
    }
}
