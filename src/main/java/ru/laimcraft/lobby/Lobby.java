package ru.laimcraft.lobby;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.jetbrains.annotations.NotNull;
import ru.laimcraft.lobby.Commands.ChangePasswordCommand;
import ru.laimcraft.lobby.Commands.EcoCommand;
import ru.laimcraft.lobby.Commands.LoginCommand;
import ru.laimcraft.lobby.Commands.RegisterCommand;
import ru.laimcraft.lobby.data.mysql.MySQLAccounts;

import java.util.HashMap;
import java.util.Objects;

public final class Lobby extends JavaPlugin {
    public static HashMap<String, AuthPlayer> players = new HashMap<>();
    public static Lobby instance;
    @Override
    public void onEnable() {
        instance = this;
        new Online(); // Регистрация асинхронного обновления онлайна
        new EventHandler(this);
        getServer().getMessenger().registerIncomingPluginChannel(this, "laimcraft:proxy", (channel, player, bytes) -> {
            Bukkit.getConsoleSender().sendMessage("FFFFFFFFFFF");
            Bukkit.getConsoleSender().sendMessage(channel);
        });
        getServer().getMessenger().registerOutgoingPluginChannel(this, "laimcraft:proxy");
        Objects.requireNonNull(getCommand("login")).setExecutor(new LoginCommand());
        Objects.requireNonNull(getCommand("register")).setExecutor(new RegisterCommand());
        Objects.requireNonNull(getCommand("changepassword")).setExecutor(new ChangePasswordCommand());
        Objects.requireNonNull(getCommand("eco")).setExecutor(new EcoCommand());
        Bukkit.getConsoleSender().sendMessage("ti");
    }

    @Override
    public void onDisable() {
        for(Player player : Bukkit.getOnlinePlayers()) {
            if(player.getName().equals("limeworld")) continue;
            player.kick(Message.reloading);
        }
    }

    public void onPluginMessageReceived(@NotNull String s, @NotNull Player player, @NotNull byte[] bytes) {
        Bukkit.getConsoleSender().sendMessage("ЫАААААААААААААААААА");
        Bukkit.getConsoleSender().sendMessage(s);
        switch (s) {
            case "laimcraft:proxy":
                ByteArrayDataInput input = ByteStreams.newDataInput(bytes);
                String request = input.readUTF();
                if(request.equals("login")) {
                    LoginType loginType = LoginType.valueOf(input.readUTF());
                    switch (loginType) {
                        case ALREADY_LOGIN:
                            players.put(player.getName(), new AuthPlayer());
                            return;
                        case NOT_AUTH:
                            String login = MySQLAccounts.getLogin(player.getName());
                            if(login == null) {
                                player.sendMessage(Message.registerSendMessage);
                                return;}
                            player.sendMessage(Message.loginSendMessage);
                            return;
                        default:
                            return;
                    }
                } else return;
            default:
                return;
        }
    }

    public static enum LoginType {
        ALREADY_LOGIN, // Уже авторизовался
        NOT_AUTH; // Не авторизирован
    }
}
