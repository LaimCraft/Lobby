package ru.laimcraft.lobby;

import org.bukkit.Bukkit;
import ru.laimcraft.lobby.api.Server;
import ru.laimcraft.lobby.api.online.ServerListPing17;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class Online {
    static List<ServerListPing17> servers = new ArrayList<>();
    static HashMap<Server, Integer> online = new HashMap<>();

    static {
        online.put(Server.PROXY, 0);
        online.put(Server.LOBBY, 0);
        online.put(Server.VANILLA, 0);
        online.put(Server.ROLEPLAY, 0);

        servers.add(new ServerListPing17((server, statusResponse) -> {
            online.replace(server, statusResponse.getPlayers().getOnline());
        }, Server.PROXY));

        servers.add(new ServerListPing17((server, statusResponse) -> {
            online.replace(server, statusResponse.getPlayers().getOnline());
        }, Server.LOBBY));

        servers.add(new ServerListPing17((server, statusResponse) -> {
            online.replace(server, statusResponse.getPlayers().getOnline());
        }, Server.VANILLA));

        servers.add(new ServerListPing17((server, statusResponse) -> {
            online.replace(server, statusResponse.getPlayers().getOnline());
        }, Server.ROLEPLAY));
    }

    public Online() {
        Bukkit.getAsyncScheduler().runAtFixedRate(Lobby.instance, scheduledTask -> {
            for(ServerListPing17 serverListPing17 : servers) {
                serverListPing17.acceptAsync();
            }
        }, 0, 5, TimeUnit.SECONDS);
    }

    public static int get(Server server) {
        return online.get(server);
    }
}
