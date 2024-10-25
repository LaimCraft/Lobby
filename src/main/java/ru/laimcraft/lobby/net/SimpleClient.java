package ru.laimcraft.lobby.net;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class SimpleClient {
    public int online = -2;
    public SimpleClient() {
        Thread thread = new Thread() {
            @Override
            public void start() {
                online = online();
            }
        };
        thread.start();
    }
    private static int online() {
        String serverAddress = "127.0.0.1"; // Адрес сервера
        int port = 54007; // Порт сервера
        try (Socket socket = new Socket(serverAddress, port)) {
            InputStream input = socket.getInputStream();
            DataInputStream dataInput = new DataInputStream(input);
            int receivedNumber = dataInput.readInt(); // Читаем число от сервера
            return receivedNumber;
            // Соединение будет разорвано автоматически после выхода из блока try-with-resources
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
    }
}