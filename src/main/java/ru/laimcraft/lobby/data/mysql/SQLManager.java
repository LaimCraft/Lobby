package ru.laimcraft.lobby.data.mysql;

import org.bukkit.entity.Player;

public class SQLManager {
    public static boolean isAuth(Player player) {
        return isAuth(player.getName());
    }

    public static boolean isAuth(String player) {
        String login = MySQLProxy.get(player);
        if(login == null) return false;
        return !login.equals("ex");}

    public static boolean add(Player player) {
        return add(player.getName());
    }

    public static boolean add(String player) {
        return MySQLProxy.add(player);
    }

    public static boolean remove(Player player) {
        return remove(player.getName());
    }

    public static boolean remove(String player) {
        return MySQLProxy.remove(player);
    }

    public static void reset() {
        MySQLProxy.reset();
    }
}
