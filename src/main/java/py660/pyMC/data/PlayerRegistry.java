package py660.pyMC.data;

import org.bukkit.entity.Player;
import py660.pyMC.items.Gem;

import javax.annotation.Nullable;
import java.util.HashMap;

public final class PlayerRegistry {
    private HashMap<String, PlayerEntry> players;

    public PlayerRegistry() {
        players = new HashMap<>();
    }

    @Nullable
    public Gem getPlayer(Player player) {
        String uuid = player.getUniqueId().toString();
        PlayerEntry playerEntry = players.get(uuid);
        if (playerEntry == null) {
            return null;
        }
        return playerEntry.getGem();
    }

    public void setPlayer(Player player, Gem gem) {
        String uuid = player.getUniqueId().toString();
        players.put(uuid, new PlayerEntry(gem));
    }

    //JavaBean stuff, please don't touch!

    public HashMap<String, PlayerEntry> getPlayers() {
        return players;
    }
    public void setPlayers(HashMap<String, PlayerEntry> players) {
        this.players = players;
    }

    //public boolean hasPlayer(Player player) {
    //    return players.containsKey(player.getUniqueId().toString());
    //}
}
