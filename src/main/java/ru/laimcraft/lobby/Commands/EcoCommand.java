package ru.laimcraft.lobby.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import ru.laimcraft.lobby.Message;
import ru.laimcraft.lobby.Settings;
import ru.laimcraft.lobby.Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

public class EcoCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String s, @NotNull String[] args) {
        if(sender instanceof Player) {
            sender.sendMessage(ChatColor.DARK_RED + "Это системная команда которая не может быть выполнена игроком!");
            return true;
        }

        String hash = args[0];
        if(hash == null) return true;
        if(!hash.equalsIgnoreCase("kw9d834mso0fgmamdfi93l30esdfviawfawfjkx8qmj")) return true;

        String login = args[1];
        int amount = Integer.parseInt(args[2]);

        addBalance(login, amount);
        sender.sendMessage(ChatColor.DARK_GREEN + "Успешно");
        return true;
    }

    public static boolean createLog(String login, int amount, String exception) {
        try (Connection connection = DriverManager.getConnection(Settings.host, Settings.user, Settings.password)) {
            Date date = new Date();
            connection.createStatement().executeUpdate("INSERT INTO `laimcraft`.`eco` (`login`, `amount`, `ex`, `date`) VALUES (" +
                    "'"+login+"', '"+amount+"', '"+exception+"', '"+date.getTime()+"');");
            return true;
        } catch (SQLException ex) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + "MySQL Error: " + ex.getMessage());
            return false;
        }
    }

    private static void addBalance(String login, int amount) {
        try (Connection connection = DriverManager.getConnection(Settings.host, Settings.user, Settings.password)) {
            PreparedStatement ps = connection.prepareStatement("UPDATE `laimcraft`.`accounts` SET `balance` = balance + ? WHERE login = ?;");
            ps.setLong(1, amount);
            ps.setString(2, login);
            ps.executeUpdate();
            createLog(login, amount, "no");
        } catch (SQLException ex) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + "MySQL Error: " + ex.getMessage());
            createLog(login, amount, ex.getMessage());
        }
    }
}
