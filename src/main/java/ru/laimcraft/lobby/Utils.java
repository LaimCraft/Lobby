package ru.laimcraft.lobby;

import com.destroystokyo.paper.proxy.VelocityProxy;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.velocitypowered.proxy.VelocityServer;
import net.kyori.adventure.audience.ForwardingAudience;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.io.*;
import java.math.BigInteger;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.regex.Pattern;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

public class Utils {
    public static String getSHA512(String input){
        String toReturn = null; try {
            MessageDigest digest = MessageDigest.getInstance("SHA-512");
            digest.reset();
            digest.update(input.getBytes("utf8"));
            toReturn = String.format("%0128x", new BigInteger(1, digest.digest()));} catch (Exception e) {
            Bukkit.getConsoleSender().sendMessage(e.toString());}return toReturn;}

    public static boolean checkPlayerName(String player) {
        if(player == null || player.isEmpty()) return false;
        if(player.length() < 3 || player.length() > 16) return false;
        if(!Pattern.matches("^[a-zA-Z0-9_]+$", player)) return false;
        if(Character.isDigit(player.charAt(0))) return false;
        return true;}

    public static boolean checkInteger(String Integer) {
        if(Integer == null || Integer.isEmpty()) return false;
        if(Integer.length() > 6) return false;
        if(!Pattern.matches("^[0-9]+$", Integer)) return false;
        if(!Character.isDigit(Integer.charAt(0))) return false;
        return true;}

    public static void sendTransferMessage(Plugin plugin, Player player, String serverName) {
        ByteArrayDataOutput output = ByteStreams.newDataOutput();
        output.writeUTF("YAm3Q5pioe4q");
        output.writeUTF("transfer");
        output.writeUTF(serverName);
        output.writeUTF(player.getName());
        player.sendPluginMessage(plugin, "server:transfer", output.toByteArray());
    }

    /*public static void sendOnlineMessage(Plugin plugin, String serverName) {
        VelocityServer velocityServer = VelocityProxy.
        ByteArrayDataOutput output = ByteStreams.newDataOutput();
        output.writeUTF("YAm3Q5pioe4q");
        output.writeUTF("online");
        output.writeUTF(serverName);
        player.sendPluginMessage(plugin, "server:transfer", output.toByteArray());
    }*/


    public static int getOnlinePillarsOfFortune() {
        String address = "127.0.0.1";
        int port = 60421;

        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(address, port));

            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            InputStream in = socket.getInputStream();

            // Отправляем handshake
            ByteArrayOutputStream handshakeBytes = new ByteArrayOutputStream();
            DataOutputStream handshake = new DataOutputStream(handshakeBytes);
            writeVarInt(handshake, 0x00);
            writeVarInt(handshake, 47);
            writeString(handshake, address);
            handshake.writeShort(port);
            writeVarInt(handshake, 1);
            writeVarInt(out, handshakeBytes.size());
            out.write(handshakeBytes.toByteArray());

            // Отправляем status request
            out.writeByte(0x01);
            out.writeByte(0x00);

            // Читаем весь ответ в байтовый массив
            ByteArrayOutputStream response = new ByteArrayOutputStream();
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                response.write(buffer, 0, bytesRead);
            }

            byte[] responseBytes = response.toByteArray();
            Bukkit.getConsoleSender().sendMessage("Полный ответ (в hex): " + bytesToHex(responseBytes));

            // Теперь попробуем интерпретировать эти байты
            ByteArrayInputStream bais = new ByteArrayInputStream(responseBytes);
            DataInputStream dis = new DataInputStream(bais);

            int length = readVarInt(dis);
            Bukkit.getConsoleSender().sendMessage("Длина пакета: " + length);

            int packetId = readVarInt(dis);
            Bukkit.getConsoleSender().sendMessage("ID пакета: " + packetId);

            if (packetId != 0x00) {
                Bukkit.getConsoleSender().sendMessage("Неожиданный ID пакета: " + packetId);
                return -1;
            }

            String json = readString(dis);
            Bukkit.getConsoleSender().sendMessage("Полученный JSON: " + json);

            JsonObject jsonObject = Json.createReader(new StringReader(json)).readObject();
            JsonObject players = jsonObject.getJsonObject("players");
            int online = players.getInt("online");
            Bukkit.getConsoleSender().sendMessage("Онлайн игроков: " + online);

            // Добавляем проверку
            if (online > 5000) {
                Bukkit.getConsoleSender().sendMessage("Получено неожиданное количество онлайн игроков: " + online);
                online = 5000;
            }

            return online;
        } catch (Exception e) {
            Bukkit.getConsoleSender().sendMessage("Ошибка при получении количества онлайн игроков: " + e.getMessage());
            e.printStackTrace();
            return -1;
        }
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02X ", b));
        }
        return sb.toString().trim(); // Убираем последний пробел
    }

    private static void writeVarInt(DataOutputStream out, int value) throws IOException {
        while (true) {
            if ((value & 0xFFFFFF80) == 0) {
                out.writeByte(value);
                return;
            }
            out.writeByte(value & 0x7F | 0x80);
            value >>>= 7;
        }
    }

    private static int readVarInt(DataInputStream in) throws IOException {
        int numRead = 0;
        int result = 0;
        byte read;
        do {
            read = in.readByte();
            int value = (read & 0b01111111);
            result |= (value << (7 * numRead));
            numRead++;
            if (numRead > 5) {
                throw new RuntimeException("VarInt is too big");
            }
        } while ((read & 0b10000000) != 0);
        return result;
    }

    private static void writeString(DataOutputStream out, String string) throws IOException {
        byte[] bytes = string.getBytes(StandardCharsets.UTF_8);
        writeVarInt(out, bytes.length);
        out.write(bytes);
    }

    private static String readString(DataInputStream in) throws IOException {
        int length = readVarInt(in);
        byte[] bytes = new byte[length];
        in.readFully(bytes);
        return new String(bytes, StandardCharsets.UTF_8);
    }

    public static void vanillaTabColorUpdate(Player player) {
        String world = player.getLocation().getWorld().getName();

        if(world.equalsIgnoreCase("world")) {
            player.setPlayerListName(ChatColor.DARK_GREEN + player.getName());
            return;}
        if(world.equalsIgnoreCase("world_nether")) {
            player.setPlayerListName(ChatColor.RED + player.getName());
            return;}
        if(world.equalsIgnoreCase("world_the_end")) {
            player.setPlayerListName(ChatColor.GOLD + player.getName());
            return;}}

    public static String vanillaGetTabColor(Player player) {
        String world = player.getLocation().getWorld().getName();

        if(world.equalsIgnoreCase("world")) {
            return ChatColor.DARK_GREEN + "";}
        if(world.equalsIgnoreCase("world_nether")) {
            return ChatColor.RED + "";}
        if(world.equalsIgnoreCase("world_the_end")) {
            return ChatColor.GOLD + "";}
        return ChatColor.WHITE + "";}
}
