package py660.pyMC;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemRarity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.scheduler.BukkitRunnable;
import py660.pyMC.items.Gem;

import java.time.Duration;
import java.util.Date;

public final class PyListener implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Bukkit.broadcastMessage("Welcome, " + event.getPlayer().getName() + ", to the server!");
        Player player = event.getPlayer();
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.RED + "Welcome (back?)!" + ChatColor.RESET));
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event){
        Entity causingEntity = event.getDamageSource().getCausingEntity();
        Entity resultEntity = event.getEntity();
        if (causingEntity instanceof Player causingPlayer && resultEntity instanceof Player resultPlayer){
            PyMC.getDataHandler().combatPlayers.put(resultPlayer, System.currentTimeMillis());
            PyMC.getDataHandler().combatPlayers.put(causingPlayer, System.currentTimeMillis());
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event){
        Player player = event.getEntity();
        Player causingPlayer = player.getKiller();

        // combat logging
        PyMC.getDataHandler().combatPlayers.remove(player);

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
            PyMC.getDataHandler().setPlayerGem(player, new Gem(gem.getGemType(), gem.getLevel()-1, gem.getSecondary()));
        }

        // drop player head
        ItemStack head = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) head.getItemMeta();
        assert meta != null;
        meta.setDisplayName(player.getName() + "'s head");
        meta.setRarity(ItemRarity.RARE);
        meta.setOwningPlayer(player);
        head.setItemMeta(meta);
        player.getWorld().dropItem(player.getLocation(), head);
    }
    @EventHandler
    public void onPlayerKick(PlayerKickEvent event){
        PyMC.getDataHandler().combatPlayers.remove(event.getPlayer());
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event){
        Player player = event.getPlayer();
        if (PyMC.getDataHandler().combatPlayers.containsKey(player)){
            PyMC.getDataHandler().combatPlayers.remove(player);
            player.setHealth(0);
            player.ban(ChatColor.DARK_RED + "Player left while in combat." + ChatColor.RED + "\nTemp-ban expires in 30 seconds." + ChatColor.RESET, Duration.ofSeconds(30), null, false);
            Bukkit.broadcastMessage(ChatColor.RED + player.getName() + " left while in combat!" + ChatColor.RESET);
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event){
    }
}
