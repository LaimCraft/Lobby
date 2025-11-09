package ru.laimcraft.lobby.api;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Notification {

    private static final SystemTray tray = SystemTray.getSystemTray();
    private static final BufferedImage img = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
    private static final Graphics2D graphics2D = getGraphics2D();
    private static final TrayIcon trayIcon = getTrayIcon();

    private static Graphics2D getGraphics2D() {
        Graphics2D graphics2D = img.createGraphics();
        graphics2D.setColor(Color.GREEN);
        graphics2D.fillOval(0, 0, 16, 16);
        graphics2D.dispose();
        return graphics2D;
    }

    private static TrayIcon getTrayIcon() {
        TrayIcon trayIcon = new TrayIcon(img, "Уведомление");
        trayIcon.setImageAutoSize(true);
        try {
            tray.add(trayIcon);
        } catch (AWTException ignored) {}
        return trayIcon;
    }

    public static void sendMessage(String caption, String text, TrayIcon.MessageType messageType) {
        trayIcon.displayMessage(caption, text, messageType);
    }

    public static void sendRegisterMessage(String playerName) {
        sendMessage("Уведомление", "Новый пользователь " + playerName + " зарегистрировался", TrayIcon.MessageType.INFO);
    }

    public static void sendLogicMessage(String playerName) {
        trayIcon.displayMessage("Уведомление", "Пользователь " + playerName + " авторизовался", TrayIcon.MessageType.INFO);
    }
}
