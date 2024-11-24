package ru.laimcraft.lobby;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Online {

    protected static final String server = "lobby";

    public static String get(String server) {
        try (Connection connection = DriverManager.getConnection(Settings.host, Settings.user, Settings.password)) {
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT `online` FROM `laimcraft`.`servers` WHERE server = '"+server+"';");
            while (resultSet.next()) {
                return resultSet.getString(1);
            }
            return null;
        } catch (Exception ex) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "MySQL ERROR: " + ex);
            return null;
        }
    }

    public static void add() {
        try (Connection connection = DriverManager.getConnection(Settings.host, Settings.user, Settings.password)) {
            PreparedStatement ps = connection.prepareStatement("UPDATE `laimcraft`.`servers` SET `online` = online + 1 WHERE server = '"+server+"';");
            ps.executeUpdate();
        } catch (Exception ex) {
            Bukkit.getConsoleSender().sendMessage("LaimCraft -> MySQL Error: " + ex.toString());
        }
    }

    public static void remove() {
        try (Connection connection = DriverManager.getConnection(Settings.host, Settings.user, Settings.password)) {
            PreparedStatement ps = connection.prepareStatement("UPDATE `laimcraft`.`servers` SET `online` = online - 1 WHERE server = '"+server+"';");
            ps.executeUpdate();
        } catch (Exception ex) {
            Bukkit.getConsoleSender().sendMessage("LaimCraft -> MySQL Error: " + ex.toString());
        }
    }

    public static void set(int i) {
        try (Connection connection = DriverManager.getConnection(Settings.host, Settings.user, Settings.password)) {
            PreparedStatement ps = connection.prepareStatement("UPDATE `laimcraft`.`servers` SET `online` = "+i+" WHERE server = '"+server+"';");
            ps.executeUpdate();
        } catch (Exception ex) {
            Bukkit.getConsoleSender().sendMessage("LaimCraft -> MySQL Error: " + ex.toString());
        }
    }

    public static void reset() {
        try (Connection connection = DriverManager.getConnection(Settings.host, Settings.user, Settings.password)) {
            PreparedStatement ps = connection.prepareStatement("UPDATE `laimcraft`.`servers` SET `online` = 0 WHERE server = '"+server+"';");
            ps.executeUpdate();
        } catch (Exception ex) {
            Bukkit.getConsoleSender().sendMessage("LaimCraft -> MySQL Error: " + ex.toString());
        }
    }
}
