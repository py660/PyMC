package py660.pyMC.async;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import py660.pyMC.PyMC;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.Set;

public final class CooldownHUDThread extends Thread {
    public void run() {
        while (true) {
            try {
                if (PyMC.getTimerHUDThread() != Thread.currentThread()) {
                    return;
                }
                Thread.sleep(100);
                DecimalFormat df = new DecimalFormat("##.0");
                df.setRoundingMode(RoundingMode.CEILING);

                Set<Player> activePlayers = new HashSet<>();
                activePlayers.addAll(PyMC.getCooldownHandler().getCombatPlayers());
                activePlayers.addAll(PyMC.getCooldownHandler().getGemCooldownPlayers());
                activePlayers.addAll(PyMC.getCooldownHandler().getSecondaryCooldownPlayers());
                //System.out.println(activePlayers.toArray().length);

                for (Player player : activePlayers) {
                    String hud = "";
                    if (PyMC.getCooldownHandler().getCombatTimeLeft(player) > 0) {
                        hud += ChatColor.DARK_RED + "Combat " + ChatColor.BOLD + df.format(PyMC.getCooldownHandler().getCombatTimeLeft(player) / 1000.0) + "s " + ChatColor.RESET;
                    } else {
                        hud += ChatColor.DARK_GREEN + "Not in combat" + ChatColor.RESET;
                    }
                    hud += " | ";
                    if (PyMC.getCooldownHandler().getGemCooldownTimeLeft(player) > 0) {
                        hud += ChatColor.GOLD + "Gem " + ChatColor.BOLD + df.format(PyMC.getCooldownHandler().getGemCooldownTimeLeft(player) / 1000.0) + "s " + ChatColor.RESET;
                    } else {
                        hud += ChatColor.DARK_GREEN + "Gem ready" + ChatColor.RESET;
                    }
                    hud += " | ";
                    if (PyMC.getCooldownHandler().getSecondaryCooldownTimeLeft(player) > 0) {
                        hud += ChatColor.BLUE + "Secondary " + ChatColor.BOLD + df.format(PyMC.getCooldownHandler().getSecondaryCooldownTimeLeft(player) / 1000.0) + "s " + ChatColor.RESET;
                    } else {
                        hud += ChatColor.DARK_GREEN + "Secondary ready" + ChatColor.RESET;
                    }
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(hud));
                }
            } catch (InterruptedException e) {
                //recieved signal for exiting, but nothing to clean up so do nothing other than exit
                return;
                //throw new RuntimeException(e);
            }
        }
    }
}
