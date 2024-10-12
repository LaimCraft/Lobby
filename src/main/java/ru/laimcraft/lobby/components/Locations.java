package ru.laimcraft.lobby.components;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class Locations {
    public static Location spawnJumpTpLocation = new Location(
            Bukkit.getWorld("world"),
            190.5d,
            67,
            253.5d,
            90,
            0
    );

    public static Location spawnJumpTpBackLocation = new Location(
            Bukkit.getWorld("world"),
            243.5d,
            63,
            253.5d,
            -90,
            0
    );

    public static Location spawnLocation = new Location(
            Bukkit.getWorld("world"),
            252.5d,
            64,
            253.5d,
            90,
            0
    );
}
