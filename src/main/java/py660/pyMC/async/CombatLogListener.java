package py660.pyMC.async;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import py660.pyMC.PyMC;

import java.time.Duration;

public final class CombatLogListener implements Listener {
    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        Entity causingEntity = event.getDamager(); //getDamageSource().getCausingEntity();
        Entity resultEntity = event.getEntity();
        if (causingEntity instanceof Player causingPlayer && resultEntity instanceof Player resultPlayer) {
            PyMC.getCooldownHandler().addCombatPlayer(resultPlayer);
            PyMC.getCooldownHandler().addCombatPlayer(causingPlayer);
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        Player causingPlayer = player.getKiller();

        // combat logging
        PyMC.getCooldownHandler().removeCombatPlayer(player);
    }

    @EventHandler
    public void onPlayerKick(PlayerKickEvent event) {
        PyMC.getCooldownHandler().removeCombatPlayer(event.getPlayer());
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (PyMC.getCooldownHandler().getCombatTimeLeft(player) > 0) {
            PyMC.getCooldownHandler().removeCombatPlayer(player);
            player.setHealth(0);
            player.ban(ChatColor.DARK_RED + "Player left while in combat." + ChatColor.RED + "\nTemp-ban expires in 30 seconds." + ChatColor.RESET, Duration.ofSeconds(30), null, false);
            Bukkit.broadcastMessage(ChatColor.RED + player.getName() + " left while in combat!" + ChatColor.RESET);
        }
    }
}
