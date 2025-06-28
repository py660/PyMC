package py660.pyMC.guis;

import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public abstract class AbstractGUI implements Listener {
    protected Inventory inventory;
    protected Set<Integer> staticSlots;
    protected Set<Integer> returnSlots;


    public AbstractGUI() {
        staticSlots = Set.of();
        returnSlots = Set.of();
        inventory = Bukkit.createInventory(null, 9, ":/ The dev forgot to define the GUI's constructor");
    }

    public void openInventory(final Player player) {
        player.openInventory(inventory);
    }

    protected void clickAction(int slot, Player player, InventoryClickEvent event) {

    }

    protected void dragAction(Set<Integer> slots, Player player, InventoryDragEvent event) {

    }

    // must be public due to spigot bug
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        //System.out.println(staticSlots);
        if (Objects.equals(event.getClickedInventory(), inventory)) {
            HumanEntity whoClicked = event.getWhoClicked();
            if (whoClicked instanceof Player player) {
                ItemStack clickedItem = event.getCurrentItem();
                if (clickedItem == null || clickedItem.getType().isAir()) return;
                int slot = event.getRawSlot();
                if (staticSlots.contains(slot)) {
                    event.setCancelled(true);
                }
                clickAction(slot, player, event);
            } else {
                event.setCancelled(true);
            }
        }
    }

    // must be public, spigot bug
    @EventHandler
    public void onInventoryDrag(InventoryDragEvent event) {
        if (Objects.equals(event.getInventory(), inventory)){
            HumanEntity whoClicked = event.getWhoClicked();
            if (whoClicked instanceof Player player) {
                Set<Integer> rawSlots = event.getRawSlots();
                if (!Collections.disjoint(rawSlots, staticSlots)){
                    event.setCancelled(true);
                }
                dragAction(rawSlots, player, event);
            } else {
                event.setCancelled(true);
            }
        }
    }

    // public bc spigot bug
    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if (Objects.equals(event.getInventory(), inventory) && event.getPlayer() instanceof Player player){
            for (int slot : returnSlots) {
                if (inventory.getItem(slot) == null) {
                    continue;
                }
                HashMap<Integer, ItemStack> nope = player.getInventory().addItem(inventory.getItem(slot));
                for (Map.Entry<Integer, ItemStack> entry : nope.entrySet()) {
                    player.getWorld().dropItemNaturally(player.getLocation(), entry.getValue());
                }
            }
            HandlerList.unregisterAll(this);
        }
    }
}
