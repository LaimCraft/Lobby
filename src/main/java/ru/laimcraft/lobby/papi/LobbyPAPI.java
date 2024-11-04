package ru.laimcraft.lobby.papi;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import ru.laimcraft.lobby.Utils;

public class LobbyPAPI extends PlaceholderExpansion {
    @Override
    public @NotNull String getIdentifier() {return "lobby";}
    @Override
    public @NotNull String getAuthor() {return "LaimCraft";}
    @Override
    public @NotNull String getVersion() {return "1.0.0";}
    public @NotNull String onPlaceholderRequest(Player player, @NotNull String params) {
        switch (params) {
            case "PillarsOfFortuneOnline":
                return "";
            case "OceanWorld":
                return "";
            default:
                return "null";
        }
    }

    /*private String to100(double mp) {
        BigDecimal bigDecimal = new BigDecimal(mp);
        BigDecimal value = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);
        return String.format("%.2f", value);
    }*/
}
