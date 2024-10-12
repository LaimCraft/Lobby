package ru.laimcraft.lobby;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.math.BigInteger;
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

    public static int getOnlinePillarsOfFortuneOld() throws Exception {
        String ipAddress = "127.0.0.1"; // Replace with the server IP address
        int port = 60421; // Default Minecraft port

        Socket socket = new Socket(ipAddress, port);

        // Send query packet
        OutputStream outputStream = socket.getOutputStream();
        outputStream.write(new byte[] {
                (byte) 0xFE, (byte) 0x01 // Query packet header
        });

        // Receive response packet
        InputStream inputStream = socket.getInputStream();
        byte[] responseBytes = new byte[1024];
        int bytesRead = inputStream.read(responseBytes);

        // Parse response packet
        ByteBuffer responseBuffer = ByteBuffer.wrap(responseBytes, 0, bytesRead);
        int protocolVersion = responseBuffer.getShort();
        int onlinePlayers = responseBuffer.getShort();

        return onlinePlayers;
    }

    public static int getOnlinePillarsOfFortune() throws Exception {
        String ipAddress = "127.0.0.1"; // Replace with the server IP address
        int port = 60421; // Default Minecraft port

        Socket socket = new Socket(ipAddress, port);

        // Send handshake packet
        OutputStream outputStream = socket.getOutputStream();
        outputStream.write(new byte[] {
                (byte) 0x00, // VarInt: packet length
                (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, // VarInt: packet id (handshake)
                (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, // VarInt: protocol version
                (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, // VarInt: server address length
                (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, // VarInt: server address
                (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, // VarInt: server port
                (byte) 0x01, // Byte: next state (status)
                (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00 // VarInt: packet length
        });

        // Receive response packet
        InputStream inputStream = socket.getInputStream();
        byte[] responseBytes = new byte[1024];
        int bytesRead = inputStream.read(responseBytes);

        // Parse response packet
        ByteBuffer responseBuffer = ByteBuffer.wrap(responseBytes, 0, bytesRead);
        int packetId = readVarInt(responseBuffer);
        if (packetId == 0x00) { // Response packet
            int length = readVarInt(responseBuffer);
            byte[] jsonBytes = new byte[length];
            responseBuffer.get(jsonBytes);
            String jsonString = new String(jsonBytes, StandardCharsets.UTF_8);
            JsonReader jsonReader = Json.createReader(new StringReader(jsonString));
            JsonObject jsonObject = jsonReader.readObject();
            JsonObject playersObject = jsonObject.getJsonObject("players");
            int onlinePlayers = playersObject.getInt("online");
            return onlinePlayers;
        }
        return -1;
    }

    public static int readVarInt(ByteBuffer buffer) {
        int value = 0;
        int i = 0;
        byte b;
        do {
            b = buffer.get();
            value |= (b & 0x7F) << i;
            i += 7;
        } while ((b & 0x80) != 0);
        return value;
    }

    public static int readVarIntOld(ByteBuffer buffer) {
        int value = 0;
        int i = 0;
        while (true) {
            byte b = buffer.get();
            value |= (b & 0x7F) << i;
            if ((b & 0x80) == 0) {
                break;
            }
            i += 7;
        }
        return value;
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
