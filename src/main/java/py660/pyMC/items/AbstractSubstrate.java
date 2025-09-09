package py660.pyMC.items;

import org.apache.commons.lang3.EnumUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemRarity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import py660.pyMC.PyMC;

import java.util.List;
import java.util.Objects;

public final class AbstractSubstrate {
    public static ItemStack buildSubstrate(SubstrateType type) {
        ItemStack gem = new ItemStack(Material.PHANTOM_MEMBRANE, 1);
        ItemMeta meta = gem.getItemMeta();
        assert meta != null;
        meta.setDisplayName(type.getTitle());
        meta.setRarity(ItemRarity.RARE);
        meta.setLore(List.of(ChatColor.BLUE + "Reactor Substrate" + ChatColor.RESET));
        meta.setMaxStackSize(1);
        meta.setItemModel(new NamespacedKey(PyMC.getInstance(), "substrates/" + type.name()));
        meta.setEnchantmentGlintOverride(true);
        meta.getPersistentDataContainer().set(new NamespacedKey(PyMC.getInstance(), "category"), PersistentDataType.STRING, "substrate");
        meta.getPersistentDataContainer().set(new NamespacedKey(PyMC.getInstance(), "type"), PersistentDataType.STRING, type.name());
        gem.setItemMeta(meta);
        return gem;
    }

    public static boolean isSubstrate(ItemStack substrate) {
        return Objects.equals(Objects.requireNonNull(substrate.getItemMeta()).getPersistentDataContainer().get(new NamespacedKey(PyMC.getInstance(), "category"), PersistentDataType.STRING), "substrate");
    }

    public static SubstrateType getType(ItemStack substrate) {
        return EnumUtils.getEnum(SubstrateType.class, Objects.requireNonNull(substrate.getItemMeta()).getPersistentDataContainer().get(new NamespacedKey(PyMC.getInstance(), "type"), PersistentDataType.STRING));
    }

    public enum SubstrateType {
        RANDOMIZER("Gem Randomizer"),
        WEAK_UPGRADER("Weak Gem Upgrader"),
        UPGRADER("Gem Upgrader");

        private final String title;

        SubstrateType(String title) {
            this.title = title;
        }

        public String getTitle() {
            return title;
        }
    }
}
