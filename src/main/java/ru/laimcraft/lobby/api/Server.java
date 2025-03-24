package ru.laimcraft.lobby.api;

public enum Server {
    ROLEPLAY("RolePlay", "localhost", 60250),
    VANILLA("Vanilla", "localhost", 60120),
    LOBBY("Lobby", "localhost", 60100),
    PROXY("Proxy", "localhost", 25566);

    private final String name;
    private final String host;
    private final int port;

    private Server(String name, String host, int port) {
        this.name = name;
        this.host = host;
        this.port = port;
    }

    public String getName() {
        return name;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }
}
