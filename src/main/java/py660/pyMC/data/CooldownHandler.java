package py660.pyMC.data;

import org.bukkit.entity.Player;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class CooldownHandler {
    private Map<Player, Long> combatPlayers = new ConcurrentHashMap<>();
    private Map<Player, Long> gemCooldown = new ConcurrentHashMap<>();
    private Map<Player, Long> secondaryCooldown = new ConcurrentHashMap<>();

    // Combat Logging
    public void addCombatPlayer(Player player) {
        combatPlayers.put(player,  System.currentTimeMillis() + 20 * 1000);
    }
    public void removeCombatPlayer(Player player) {
        combatPlayers.remove(player);
    }
    public Long getCombatTimeLeft(Player player) {
        long left = combatPlayers.getOrDefault(player, 0L) - System.currentTimeMillis();
        if (left <= 0) {
            removeCombatPlayer(player);
            return 0L;
        }
        return left;
    }

    // Gem Cooldown
    public void setGemCooldownPlayer(Player player, Long durationMsec) {
        gemCooldown.put(player, System.currentTimeMillis() + durationMsec);
    }
    public Long getGemCooldownTimeLeft(Player player) {
        long left = gemCooldown.getOrDefault(player, 0L) - System.currentTimeMillis();
        if (left <= 0) {
            gemCooldown.remove(player);
            return 0L;
        }
        return left;
    }

    // Secondary Cooldown
    public void setSecondaryCooldownPlayer(Player player, Long durationMsec) {
        secondaryCooldown.put(player, System.currentTimeMillis() + durationMsec);
    }
    public Long getSecondaryCooldownTimeLeft(Player player) {
        long left = secondaryCooldown.getOrDefault(player, 0L) - System.currentTimeMillis();
        if (left <= 0) {
            secondaryCooldown.remove(player);
            return 0L;
        }
        return left;
    }

    // For HUD, to only act on relevant players
    public Set<Player> getCombatPlayers() {
        return combatPlayers.keySet();
    }
    public Set<Player> getGemCooldownPlayers() {
        return gemCooldown.keySet();
    }
    public Set<Player> getSecondaryCooldownPlayers() {
        return secondaryCooldown.keySet();
    }
}
