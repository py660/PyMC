package py660.pyMC.guis;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import py660.pyMC.PyMC;
import py660.pyMC.items.Gem;

import java.util.Map;
import java.util.Objects;
import java.util.Set;

public final class GemActivatorGUI extends AbstractGUI implements Listener {
    public GemActivatorGUI() {
        this.inventory = Bukkit.createInventory(null, InventoryType.HOPPER, " ".repeat(7) + ChatColor.BOLD + ChatColor.DARK_RED + "[" + ChatColor.RED + "Gem Activator Core" + ChatColor.DARK_RED + "]" + ChatColor.RESET);
        this.staticSlots = Set.of(0, 1, 3, 4);

        ItemStack empties = new ItemStack(Material.GRAY_STAINED_GLASS_PANE, 1);
        ItemMeta emptiesMeta = empties.getItemMeta();
        assert emptiesMeta != null;
        emptiesMeta.setMaxStackSize(1);
        emptiesMeta.setItemModel(new NamespacedKey(PyMC.getInstance(), "gui/empty"));
        emptiesMeta.setHideTooltip(true);
        empties.setItemMeta(emptiesMeta);

        inventory.setItem(0, empties);
        inventory.setItem(1, empties);
        inventory.setItem(3, empties);
        inventory.setItem(4, empties);
    }

    @Override
    public void openInventory(Player player) {
        super.openInventory(player);
        Gem gem = PyMC.getDataHandler().getPlayerGem(player);
        if (gem.getGemType() == Gem.GemType.DB_MISSING_GEM) {
            return;
        }
        inventory.setItem(2, gem);
    }

    @Override
    protected void clickAction(int slot, Player player, InventoryClickEvent event) {
        // later on, check for "binding"/unremovable gems
        if (slot == 2 && inventory.getItem(2) != null && !Gem.isGem(inventory.getItem(2))) {
            event.setCancelled(true);
        }
    }

    @Override
    protected void dragAction(Set<Integer> slots, Player player, InventoryDragEvent event) {
        // later on, check for "binding"/unremovable gems
        if (slots.contains(2) && inventory.getItem(2) != null && !Gem.isGem(inventory.getItem(2))) {
            event.setCancelled(true);
        }
    }

    @Override
    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        super.onInventoryClose(event);
        if (Objects.equals(event.getInventory(), inventory) && event.getPlayer() instanceof Player player) {
            Inventory inventory = event.getInventory();
            ItemStack item = inventory.getItem(2);
            Gem gem = new Gem(Gem.GemType.DB_MISSING_GEM, Gem.MIN_LEVEL);
            if (item == null) {
                player.sendMessage(ChatColor.YELLOW + "Gem successfully cleared!");
            } else if (Gem.isGem(item)) {
                player.sendMessage(ChatColor.GREEN + "Gem successfully activated!");
                gem = new Gem(item);
            } else {
                // non-genuine; reject and drop
                player.sendMessage(ChatColor.RED + "Beware: Gem Missing & Invalid item in activator core.");
                Map<Integer, ItemStack> nope = player.getInventory().addItem(item);
                for (Map.Entry<Integer, ItemStack> entry : nope.entrySet()) {
                    player.getWorld().dropItemNaturally(player.getLocation(), entry.getValue());
                }
            }
            PyMC.getDataHandler().setPlayerGem(player, gem);
        }
    }
}
