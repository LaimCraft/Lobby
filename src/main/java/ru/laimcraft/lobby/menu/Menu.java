package ru.laimcraft.lobby.menu;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import ru.laimcraft.lobby.Online;
import ru.laimcraft.lobby.api.Server;

import java.util.ArrayList;
import java.util.List;

public class Menu {
    public static NamespacedKey menuKey = new NamespacedKey("lobby", "menu");
    public static NamespacedKey menuServerTeleport = new NamespacedKey("lobby", "menuserverteleport");

    public static void getMenuItem(Player player) {
        ItemStack itemStack = new ItemStack(Material.PAPER);
        ItemMeta itemMeta = itemStack.getItemMeta();

        itemMeta.setCustomModelData(1);
        itemMeta.setItemName(ChatColor.DARK_GREEN + "Выбор сервера");
        itemMeta.getPersistentDataContainer().set(menuKey, PersistentDataType.BOOLEAN, true);

        itemStack.setItemMeta(itemMeta);

        player.getInventory().setItem(1, itemStack);
    }

    public static void open(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 27, ChatColor.DARK_GREEN + "Выбор сервера");

        inventory.setItem(10, createServerItem(
                Material.GRASS_BLOCK,
                "vanilla",
                ChatColor.DARK_GREEN + "Vanilla " + ChatColor.RED + Online.get(Server.VANILLA),
                getVanillaLore()));

        inventory.setItem(12, createServerItem(
                Material.COAL_ORE, "roleplay",
                ChatColor.DARK_GREEN + "Role Play " + ChatColor.RED + Online.get(Server.ROLEPLAY),
                getRolePlayLore()));
        player.openInventory(inventory);
    }

    private static ItemStack createServerItem(Material material, String server, String name, List<String> lore) {
        ItemStack itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setItemName(name);
        itemMeta.setLore(lore);
        itemMeta.getPersistentDataContainer().set(menuKey, PersistentDataType.BOOLEAN, true);
        itemMeta.getPersistentDataContainer().set(menuServerTeleport, PersistentDataType.STRING, server);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }



    private static List<String> getVanillaLore() {
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.GOLD + "Погрузитесь в мир чистого Minecraft на нашем ванильном сервере!");
        lore.add(ChatColor.GOLD + "Здесь вы найдете оригинальный игровой процесс без модификаций и плагинов.");
        lore.add(ChatColor.GOLD + "Исследуйте бескрайние просторы, стройте уникальные сооружения и создавайте");
        lore.add(ChatColor.GOLD + "свои приключения в дружелюбном сообществе единомышленников.");
        lore.add(ChatColor.GOLD + "Участвуйте в регулярных событиях и конкурсах, наслаждайтесь честной игрой");
        lore.add(ChatColor.GOLD + "без читеров и нарушителей.");
        lore.add(ChatColor.GOLD + "Присоединяйтесь к нам и откройте для себя магию классического Minecraft!");
        lore.add(ChatColor.AQUA + "Наш Discord сервер: https://discord.gg/PA8fKJ5XEb");
        lore.add(ChatColor.DARK_AQUA + "Discord сервер только для Ванилы!");
        return lore;
    }

    private static List<String> getRolePlayLore() {
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.GOLD + "Добро пожаловать на роле плей сервер в майнкрафте");
        lore.add(ChatColor.GOLD + "Здесь вы сможете окунуться в атмосферу реальной жизни!");
        lore.add(ChatColor.GOLD + "Здесь каждый может проживать свою собственную жизнь");
        lore.add(ChatColor.GOLD + "полную увлекательных событий и возможностей.");
        lore.add(ChatColor.AQUA + "Наш Discord сервер: https://discord.gg/U7D8vzK6XS");
        lore.add(ChatColor.DARK_AQUA + "Discord сервер проекта");
        return lore;
    }
}
