package py660.pyMC.guis;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import py660.pyMC.PyMC;
import py660.pyMC.items.AbstractSecondary;
import py660.pyMC.items.AbstractSubstrate;
import py660.pyMC.items.Gem;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public final class GemReactorGUI extends AbstractGUI implements Listener {


    public GemReactorGUI() {
        this.inventory = Bukkit.createInventory(null, InventoryType.HOPPER, " ".repeat(8) + ChatColor.BOLD + ChatColor.DARK_BLUE + "[" + ChatColor.BLUE + "Gem Reactor Core" + ChatColor.DARK_BLUE + "]" + ChatColor.RESET);
        this.staticSlots = Set.of(1, 3);
        this.returnSlots = Set.of(0, 2, 4);

        ItemStack rightArrow = new ItemStack(Material.SPECTRAL_ARROW, 1);
        ItemMeta rightArrowMeta = rightArrow.getItemMeta();
        assert rightArrowMeta != null;
        rightArrowMeta.setMaxStackSize(1);
        rightArrowMeta.setItemModel(new NamespacedKey(PyMC.getInstance(), "gui/right_arrow"));
        rightArrowMeta.setHideTooltip(true);
        rightArrow.setItemMeta(rightArrowMeta);

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

    @Override
    protected void clickAction(int slot, Player player, InventoryClickEvent event) {
        if (slot != 3) return;
        if (inventory.getItem(4) != null) return;
        if (inventory.getItem(0) == null || inventory.getItem(2) == null) return;
        ItemStack substrate;
        Gem gem;
        Gem newGem;
        if (Gem.isGem(Objects.requireNonNull(inventory.getItem(0)))) {
            gem = new Gem(inventory.getItem(0));
            substrate = inventory.getItem(2);
        } else if (Gem.isGem(Objects.requireNonNull(inventory.getItem(2)))) {
            gem = new Gem(inventory.getItem(2));
            substrate = inventory.getItem(0);
        } else {
            return;
        }
        int level = gem.getLevel();
        Gem.GemType type = gem.getGemType();

        assert substrate != null;
        if (AbstractSubstrate.isSubstrate(substrate)) {
            if (AbstractSubstrate.getType(substrate) == AbstractSubstrate.SubstrateType.RANDOMIZER) {
                List<Gem.GemType> obtainableTypesCopy = new ArrayList<>(Gem.obtainableTypes);
                obtainableTypesCopy.removeIf(tempType -> tempType == type);
                newGem = new Gem(obtainableTypesCopy.getFirst(), 1);
            } else {
                if (AbstractSubstrate.getType(substrate) == AbstractSubstrate.SubstrateType.WEAK_UPGRADER && level >= 2) {
                    return;
                }
                if (level >= Gem.MAX_LEVEL) return;
                newGem = new Gem(type, level + 1, gem.getSecondary());
            }
        } else if (AbstractSecondary.isSecondary(substrate)) {
            newGem = new Gem(type, level, AbstractSecondary.getType(substrate));
        } else {
            return;
        }
        inventory.clear(0);
        inventory.clear(2);
        inventory.setItem(4, newGem);
        player.updateInventory();
    }
}
