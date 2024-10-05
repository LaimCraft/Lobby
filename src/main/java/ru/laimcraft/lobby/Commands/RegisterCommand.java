package ru.laimcraft.lobby.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.laimcraft.lobby.AuthPlayer;
import ru.laimcraft.lobby.Lobby;
import ru.laimcraft.lobby.Message;
import ru.laimcraft.lobby.Utils;
import ru.laimcraft.lobby.data.mysql.MySQLAccounts;
import ru.laimcraft.lobby.data.mysql.SQLManager;

public class RegisterCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
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
                    break;
                case "ex":
                    player.kick(Message.kickError);
                    return true;
                default:
                    player.sendMessage(Message.AccountCreated);
                    player.sendMessage(Message.loginSendMessage);
                    return true;}
            boolean result = MySQLAccounts.create(player.getName(), args[0]);
            if(result) {
                login = MySQLAccounts.getLogin(player.getName());
                switch (login) {
                    case null:
                        player.kick(Message.kickError);
                        return true;
                    case "ex":
                        player.kick(Message.kickError);
                        return true;
                    default:
                        player.sendMessage(Message.registerSuccess);
                        Lobby.players.put(player.getName(), new AuthPlayer());
                        SQLManager.add(player);
                        Utils.sendTransferMessage(Lobby.instance, player, "vanilla");
                        return true;}
            } login = MySQLAccounts.getLogin(player.getName());
            switch (login) {
                case null:
                    player.kick(Message.kickError);
                    return true;
                case "ex":
                    player.kick(Message.kickError);
                    return true;
                default:
                    player.sendMessage(Message.registerSuccess);
                    Lobby.players.put(player.getName(), new AuthPlayer());
                    SQLManager.add(player);
                    return true;}
        }
        player.sendMessage(Message.loginSendMessage);
        return true;
    }
}
