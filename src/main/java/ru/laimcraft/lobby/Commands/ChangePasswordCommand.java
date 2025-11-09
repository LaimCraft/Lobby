package ru.laimcraft.lobby.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.laimcraft.api.command.PlayerCommand;
import ru.laimcraft.lobby.AuthPlayer;
import ru.laimcraft.lobby.Lobby;
import ru.laimcraft.lobby.Message;
import ru.laimcraft.lobby.api.Notification;
import ru.laimcraft.lobby.data.mysql.MySQLAccounts;
import ru.laimcraft.lobby.rpc.RPC;

public class ChangePasswordCommand implements PlayerCommand {
    @Override
    public void onCommand(Player player, String[] args) {
        if(!Lobby.players.containsKey(player.getName())) {
            player.sendMessage(ChatColor.DARK_RED + "Вы должны пройти авторизацию прежде чем менять пароль");
            return;
        }
        if(args.length == 2) {
            if(args[0] == null || args[0].isEmpty()) return;
            if(args[1] == null || args[1].isEmpty()) return;

            if(args[0].length() > 48 || args[1].length() > 48) {
                player.sendMessage(Message.passwordMaxLength);
                return;
            }

            String login = MySQLAccounts.getLogin(player.getName());

            switch (login) {
                case null:
                    player.sendMessage(Message.noRegister);
                    player.sendMessage(Message.registerSendMessage);
                    Lobby.players.remove(player.getName());
                    return;
                case "ex":
                    player.kick(Message.kickError);
                    return;
                default:
                    break;}
            short result = MySQLAccounts.auth(player.getName(), args[0]);
            switch (result) {
                case 1: // Успешная смена пароля
                    player.sendMessage(Message.auth);
                    Lobby.players.put(player.getName(), new AuthPlayer());
                    MySQLAccounts.authDateUpdate(player.getName());
                    RPC.sendMessage(String.format("login %s", player.getName()));
                    Notification.sendLogicMessage(player.getName());
                    return;
                case 0, -1:
                    player.sendMessage(ChatColor.RED + "Вы ввели неверно старый пароль");
                    return;
                case -2:
                    player.kick(Message.kickError);
                    return;
                default:
                    return;
            }
        } else {
            if(args.length == 0) player.sendMessage(Message.changePasswordSendMessage);
            else player.sendMessage(Message.changePasswordCommandIncorrectSendMessage);
            return;
        }
    }
}
