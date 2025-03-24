package ru.laimcraft.lobby.api.online;

import ru.laimcraft.lobby.api.Server;

@FunctionalInterface
public interface StatusRunnable {
    void run(Server server, ServerListPing17.StatusResponse statusResponse);
}