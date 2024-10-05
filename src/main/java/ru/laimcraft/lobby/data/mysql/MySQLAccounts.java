package ru.laimcraft.lobby.data.mysql;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import ru.laimcraft.lobby.Message;
import ru.laimcraft.lobby.Utils;

import java.sql.*;
import java.util.Date;

public class MySQLAccounts {

    //return всё что не пересчитано ниже = result = результат.
    //return -1 and null = none element = Данный элемент отсутствует в Базе Данных.
    //return "ex" and -2 = ERROR = Ошибка в базе данных!
    public static boolean create(String login, String password) {
        try (Connection connection = DriverManager.getConnection(Settings.host, Settings.user, Settings.password)) {
            Date date = new Date();
            String passwordHash = Utils.getSHA512(password);
            connection.createStatement().executeUpdate("INSERT INTO `laimcraft`.`accounts` (`login`, `password`, `regdate`, `authdate`) VALUES (" +
                    "'"+login+"', '"+passwordHash+"', '"+date.getTime()+"', '"+date.getTime()+"');");
            return true;
        } catch (SQLException ex) {
            Bukkit.getConsoleSender().sendMessage(Message.getError(ex.getMessage()));
            return false;}}

    public static String getLogin(String login) {
        try (Connection connection = DriverManager.getConnection(Settings.host, Settings.user, Settings.password)) {
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT `login` FROM `laimcraft`.`accounts` WHERE login = '"+login+"';");
            while (resultSet.next()) {
                return resultSet.getString(1);
            }return null;
        } catch (SQLException ex) {
            Bukkit.getConsoleSender().sendMessage(Message.getError(ex.getMessage()));
            return "ex";}}

    public static short auth(String login, String password) {
        try (Connection connection = DriverManager.getConnection(Settings.host, Settings.user, Settings.password)) {
            String passwordHash = Utils.getSHA512(password);
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT `login` FROM `laimcraft`.`accounts` " +
                    "WHERE login = '"+login+"' AND password = '" + passwordHash + "';");
            while (resultSet.next()) {
                if(login.equals(resultSet.getString(1))) return 1;
                return 0;
            }return -1;
        } catch (SQLException ex) {
            Bukkit.getConsoleSender().sendMessage(Message.getError(ex.getMessage()));
            return -2;}}

    public static boolean authDateUpdate(String login) {
        try (Connection connection = DriverManager.getConnection(Settings.host, Settings.user, Settings.password)) {
            Date date = new Date();
            PreparedStatement ps = connection.prepareStatement("UPDATE `laimcraft`.`accounts` SET `authdate` = ? WHERE login = ?;");
            ps.setLong(1, date.getTime());
            ps.setString(2, login);
            ps.executeUpdate();
            return true;
        } catch (Exception ex) {
            Bukkit.getConsoleSender().sendMessage("LaimCraft -> MySQL Error: " + ex.toString());
            return false;}}

    /*public static String getLoginByLogin(String Login) {
        try (Connection connection = DriverManager.getConnection(Settings.host, Settings.user, Settings.password)) {
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT `login` FROM `laimcraft`.`accounts` WHERE login = '"+Login+"';");
            while (resultSet.next()) {
                return resultSet.getString(1).toLowerCase();
            }return null;
        } catch (Exception ex) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "MySQL ERROR: " + ex);
            return "ex";}}

    public static int getBalance(String Login) {
        try (Connection connection = DriverManager.getConnection(Settings.host, Settings.user, Settings.password)) {
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT `balance` FROM `laimcraft`.`accounts` WHERE login = '"+Login+"';");
            while (resultSet.next()) {
                return resultSet.getInt(1);
            }return -1;
        } catch (Exception ex) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "MySQL ERROR: " + ex);
            return -1;}}

    public static boolean pay(String login, String pay, int amount) {
        removeBalance(login, amount);
        addBalance(pay, amount);
        return true;}

    public static void addBalance(String login, int amount) {
        try (Connection connection = DriverManager.getConnection(Settings.host, Settings.user, Settings.password)) {
            PreparedStatement ps = connection.prepareStatement("UPDATE `laimcraft`.`accounts` SET `balance` = balance + ? WHERE login = ?;");
            ps.setLong(1, amount);
            ps.setString(2, login);
            ps.executeUpdate();
        } catch (Exception ex) {
            Bukkit.getConsoleSender().sendMessage("LaimCraft -> MySQL Error: " + ex.toString());}}

    public static void removeBalance(String login, int amount) {
        try (Connection connection = DriverManager.getConnection(Settings.host, Settings.user, Settings.password)) {
            PreparedStatement ps = connection.prepareStatement("UPDATE `laimcraft`.`accounts` SET `balance` = balance - ? WHERE login = ?;");
            ps.setLong(1, amount);
            ps.setString(2, login);
            ps.executeUpdate();
        } catch (Exception ex) {
            Bukkit.getConsoleSender().sendMessage("LaimCraft -> MySQL Error: " + ex.toString());}}

    public static String getUsernameByLogin(String login) {
        try (Connection connection = DriverManager.getConnection(Settings.host, Settings.user, Settings.password)) {
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT `login` FROM `laimcraft`.`accounts` WHERE login = '"+login+"';");
            while (resultSet.next()) {
                return resultSet.getString(1);
            }return null;
        } catch (Exception ex) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "MySQL ERROR: " + ex);
            return "ex";}}

    public static String getPasswordByLogin(String login) {
        try (Connection connection = DriverManager.getConnection(Settings.host, Settings.user, Settings.password)) {
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT `password` FROM `laimcraft`.`accounts` WHERE login = '"+login+"';");
            while (resultSet.next()) {
                return resultSet.getString(1);
            }return null;
        } catch (Exception ex) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "MySQL ERROR: " + ex);
            return "ex";}}

    public static boolean authDateUpdate(String login) {
        try (Connection connection = DriverManager.getConnection(Settings.host, Settings.user, Settings.password)) {
            Date date = new Date();
            PreparedStatement ps = connection.prepareStatement("UPDATE `laimcraft`.`accounts` SET `authdate` = ? WHERE login = ?;");
            ps.setLong(1, date.getTime());
            ps.setString(2, login);
            ps.executeUpdate();
            return true;
        } catch (Exception ex) {
            Bukkit.getConsoleSender().sendMessage("LaimCraft -> MySQL Error: " + ex.toString());
            return false;}}*/
}
