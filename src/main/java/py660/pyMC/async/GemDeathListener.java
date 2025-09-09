package py660.pyMC.async;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemRarity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.scheduler.BukkitRunnable;
import py660.pyMC.PyMC;
import py660.pyMC.items.Gem;

import java.util.Date;

public final class GemDeathListener implements Listener {
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        Player causingPlayer = player.getKiller();

        // gem levelling
        Gem gem = PyMC.getDataHandler().getPlayerGem(player);
        if (gem.getLevel() <= Gem.MIN_LEVEL) {
            Bukkit.broadcastMessage(ChatColor.RED + "Final Kill! " + player.getName() + " ran out of lives and was banned.");
            player.ban(ChatColor.DARK_RED + "You have run out of lives." + ChatColor.RESET, (Date) null, causingPlayer == null ? "Natural Causes" : causingPlayer.getName(), false);
            new BukkitRunnable() {
                @Override
                public void run() {
                    player.kickPlayer(ChatColor.DARK_RED + "You have run out of lives." + ChatColor.RESET);
                }
            }.runTaskLater(PyMC.getInstance(), 1);
            // don't kick immediately, otherwise double inventory for some reason?!?
        } else {
            PyMC.getDataHandler().setPlayerGem(player, new Gem(gem.getGemType(), gem.getLevel() - 1, gem.getSecondary()));
        }

        // drop player head
        ItemStack head = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) head.getItemMeta();
        assert meta != null;
        meta.setDisplayName(player.getName() + "'s head");
        meta.setRarity(ItemRarity.RARE);
        meta.setOwningPlayer(player);
        head.setItemMeta(meta);
        event.getDrops().add(head);
        player.getWorld().dropItem(player.getLocation(), head);

        // prevent offhand gem from dropping
        if (Gem.isGem(player.getInventory().getItemInOffHand())) {
            event.getDrops().remove(player.getInventory().getItemInOffHand());
        }
    }
}
