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

public final class AbstractSecondary {
    private AbstractSecondary() {
    }

    public static ItemStack buildSecondary(SecondaryType type) {
        ItemStack gem = new ItemStack(Material.PHANTOM_MEMBRANE, 1);
        ItemMeta meta = gem.getItemMeta();
        assert meta != null;
        meta.setDisplayName(type.title);
        meta.setRarity(ItemRarity.RARE);

        meta.setLore(List.of(ChatColor.BLUE + "Secondary" + ChatColor.RESET));
        meta.setMaxStackSize(1);
        meta.setItemModel(new NamespacedKey(PyMC.getInstance(), "secondaries/" + type.name()));
        meta.setEnchantmentGlintOverride(true);
        meta.getPersistentDataContainer().set(new NamespacedKey(PyMC.getInstance(), "category"), PersistentDataType.STRING, "secondary");
        meta.getPersistentDataContainer().set(new NamespacedKey(PyMC.getInstance(), "type"), PersistentDataType.STRING, type.name());
        gem.setItemMeta(meta);
        return gem;
    }

    public static boolean isSecondary(ItemStack secondary) {
        return Objects.equals(Objects.requireNonNull(secondary.getItemMeta()).getPersistentDataContainer().get(new NamespacedKey(PyMC.getInstance(), "category"), PersistentDataType.STRING), "secondary");
    }

    public static SecondaryType getType(ItemStack secondary) {
        return EnumUtils.getEnum(SecondaryType.class, Objects.requireNonNull(secondary.getItemMeta()).getPersistentDataContainer().get(new NamespacedKey(PyMC.getInstance(), "type"), PersistentDataType.STRING));
    }

    public enum SecondaryType {
        DEFAULT("None"),
        BEACON("Beacon"),
        BOOMBOX("Boombox"),
        EXPLODING_KITTENS("Exploding Kittens");

        private final String title;

        SecondaryType(String title) {
            this.title = title;
        }

        public String getTitle() {
            return title;
        }
    }
}
