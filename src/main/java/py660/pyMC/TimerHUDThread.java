package py660.pyMC;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Iterator;

public final class TimerHUDThread extends Thread {
    public void run() {
        while(true) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            DecimalFormat df = new DecimalFormat("##.0");
            df.setRoundingMode(RoundingMode.CEILING);
            Iterator<Player> iter = PyMC.getDataHandler().combatPlayers.keySet().iterator();
            while (iter.hasNext()){
                Player player = iter.next();
                if (PyMC.getDataHandler().combatPlayers.get(player)+20000 > System.currentTimeMillis()) {
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("" + ChatColor.DARK_RED + ChatColor.BOLD + "Combat " + df.format((PyMC.getDataHandler().combatPlayers.get(player) - System.currentTimeMillis() + 20000) / 1000.0) + "s " + ChatColor.RED + ChatColor.UNDERLINE + "<<DO NOT LOGOUT>>" + ChatColor.RESET));
                } else {
                    iter.remove();
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.YELLOW + "Not in Combat" + ChatColor.RESET));
                }
            }
        }
    }
}
