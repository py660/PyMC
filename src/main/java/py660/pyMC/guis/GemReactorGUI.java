package py660.pyMC.guis;

import org.apache.commons.lang3.ArrayUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import py660.pyMC.PyMC;
import py660.pyMC.gems.AbstractGem;
import py660.pyMC.secondaries.AbstractSecondary;
import py660.pyMC.substrates.AbstractSubstrate;

import java.security.SecureRandom;
import java.util.*;

public class GemReactorGUI implements Listener {
    private final Inventory inventory;
    private final Set<Integer> staticSlots = Set.of(1, 3);
    private final Set<Integer> userSlots = Set.of(0, 2, 4);


    public GemReactorGUI() {
        inventory = Bukkit.createInventory(null, InventoryType.HOPPER, " ".repeat(8) + ChatColor.BOLD + ChatColor.DARK_BLUE + "[" + ChatColor.BLUE + "Gem Reactor Core" + ChatColor.DARK_BLUE + "]" + ChatColor.RESET);
        ItemStack empties = new ItemStack(Material.GRAY_STAINED_GLASS_PANE, 1);
        ItemMeta emptiesMeta = empties.getItemMeta();
        assert emptiesMeta != null;
        emptiesMeta.setMaxStackSize(1);
        emptiesMeta.setItemModel(new NamespacedKey(PyMC.getInstance(), "gui/empty"));
        emptiesMeta.setHideTooltip(true);
        empties.setItemMeta(emptiesMeta);

        ItemStack rightArrow = new ItemStack(Material.SPECTRAL_ARROW, 1);
        ItemMeta rightArrowMeta = rightArrow.getItemMeta();
        assert rightArrowMeta != null;
        rightArrowMeta.setMaxStackSize(1);
        rightArrowMeta.setItemModel(new NamespacedKey(PyMC.getInstance(), "gui/right_arrow"));
        rightArrowMeta.setHideTooltip(true);
        rightArrow.setItemMeta(rightArrowMeta) ;

        ItemStack plusSign = new ItemStack(Material.GRAY_STAINED_GLASS_PANE, 1);
        ItemMeta plusSignMeta = plusSign.getItemMeta();
        assert plusSignMeta != null;
        plusSignMeta.setMaxStackSize(1);
        plusSignMeta.setItemModel(new NamespacedKey(PyMC.getInstance(), "gui/plus_sign"));
        plusSignMeta.setHideTooltip(true);
        plusSign.setItemMeta(plusSignMeta);
        inventory.setItem(1, plusSign);
        inventory.setItem(3, rightArrow);
    }

    public void openInventory(final Player player) {
        player.openInventory(inventory);
    }

    private void clickAction(int slot, Player player) {
        if (slot != 3) return;
        if (inventory.getItem(4) != null) return;
        if (inventory.getItem(0) == null || inventory.getItem(2) == null) return;
        ItemStack gem, substrate, newGem;
        if (AbstractGem.isGem(Objects.requireNonNull(inventory.getItem(0)))) {
            gem = inventory.getItem(0);
            substrate = inventory.getItem(2);
        } else if (AbstractGem.isGem(Objects.requireNonNull(inventory.getItem(2)))) {
            gem = inventory.getItem(2);
            substrate = inventory.getItem(0);
        } else {
            return;
        }
        assert gem != null;
        int level = AbstractGem.getLevel(gem);
        AbstractGem.GemType type = AbstractGem.getType(gem);

        assert substrate != null;
        if (AbstractSubstrate.isSubstrate(substrate)) {
            if (AbstractSubstrate.getType(substrate) == AbstractSubstrate.SubstrateType.RANDOMIZER) {
                int i = new SecureRandom().nextInt(AbstractGem.GemType.values().length - 1);
                if (i >= ArrayUtils.indexOf(AbstractGem.GemType.values(), AbstractGem.getType(gem))) i--;
                newGem = AbstractGem.buildGem(AbstractGem.GemType.values()[i], 1, AbstractGem.getSecondary(gem));
            } else {
                if (AbstractSubstrate.getType(substrate) == AbstractSubstrate.SubstrateType.WEAK_UPGRADER && level >= 2) {
                    return;
                }
                if (level >= AbstractGem.MAX_LEVEL) return;
                newGem = AbstractGem.buildGem(type, level + 1, AbstractGem.getSecondary(gem));
            }
        } else if (AbstractSecondary.isSecondary(substrate)) {
            newGem = AbstractGem.buildGem(type, level, AbstractSecondary.getType(substrate));
        } else {
            return;
        }
        inventory.clear(0);
        inventory.clear(2);
        inventory.setItem(4, newGem);
        player.updateInventory();
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (Objects.equals(event.getClickedInventory(), inventory)) {
            HumanEntity whoClicked = event.getWhoClicked();
            if (whoClicked instanceof Player player) {
                ItemStack clickedItem = event.getCurrentItem();
                if (clickedItem == null || clickedItem.getType().isAir()) return;
                int slot = event.getRawSlot();
                if (staticSlots.contains(slot)) {
                    event.setCancelled(true);
                }
                //System.out.println(slot);
                //System.out.println(player.toString());
                clickAction(slot, player);
            } else {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent event) {
        if (Objects.equals(event.getInventory(), inventory)){
            HumanEntity whoClicked = event.getWhoClicked();
            if (whoClicked instanceof Player player) {
                Set<Integer> rawSlots = event.getRawSlots();
                if (!Collections.disjoint(rawSlots, staticSlots)){
                    event.setCancelled(true);
                }
            } else {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if (Objects.equals(event.getInventory(), inventory) && event.getPlayer() instanceof Player player){
            for (int slot : userSlots) {
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
