package ru.laimcraft.lobby.net;

import org.bukkit.Bukkit;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class SimpleServer {
    public static void startServer() {
        int port = 54007; // Порт для прослушивания
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            Bukkit.getConsoleSender().sendMessage("Сервер запущен, ожидает подключения...");
            try (Socket clientSocket = serverSocket.accept()) {
                Bukkit.getConsoleSender().sendMessage("Клиент подключен.");
                OutputStream output = clientSocket.getOutputStream();
                DataOutputStream dataOutput = new DataOutputStream(output);
                dataOutput.writeInt(Bukkit.getOnlinePlayers().size()); // Отправляем число 50 (заменили на онлайн)
                Bukkit.getConsoleSender().sendMessage("Отправлен онлайн сервера");
                // Соединение будет разорвано автоматически после выхода из блока try-with-resources
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}