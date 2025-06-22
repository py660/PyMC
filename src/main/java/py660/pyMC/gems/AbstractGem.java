package py660.pyMC.gems;

import org.apache.commons.lang3.EnumUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemRarity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import py660.pyMC.PyMC;
import py660.pyMC.RomanNumeral;
import py660.pyMC.secondaries.AbstractSecondary;

import java.util.Arrays;
import java.util.Objects;

public abstract class AbstractGem {
    public enum GemType {
        BEACON("Beacon Gem"),
        LAVA_RAIN("Lava Rain Gem"),
        SPACETIME("Spacetime Continuum™ Gem"),
        ENDER("Ender Gem"),
        CHICKEN_JOCKEY("Chicken Jockey Gem"),
        RICK_ASTLEY("Rick Astley Gem");

        private final String title;
        private GemType(String title) {
            this.title = title;
        }
        public String getTitle() {
            return title;
        }
    }

    public static Integer MAX_LEVEL = 4;

    public static ItemStack buildGem(GemType type, Integer level) {
        return buildGem(type, level, AbstractSecondary.SecondaryType.DEFAULT);
    }

    public static ItemStack buildGem(GemType type, Integer level, AbstractSecondary.SecondaryType secondary) {
        ItemStack gem = new ItemStack(Material.PHANTOM_MEMBRANE, 1);
        ItemMeta meta = gem.getItemMeta();
        assert meta != null;
        meta.setDisplayName(type.title);
        meta.setRarity(ItemRarity.EPIC);
        meta.setLore(Arrays.asList("" + ChatColor.YELLOW + ChatColor.BOLD + "Level " + RomanNumeral.toRoman(level) + ChatColor.RESET, "Secondary: " + secondary.getTitle()));
        meta.setMaxStackSize(1);
        meta.setItemModel(new NamespacedKey(PyMC.getInstance(), "gems/" + type.name()));
        meta.setEnchantmentGlintOverride(true);
        meta.getPersistentDataContainer().set(new NamespacedKey(PyMC.getInstance(), "category"), PersistentDataType.STRING, "gem");
        meta.getPersistentDataContainer().set(new NamespacedKey(PyMC.getInstance(), "type"), PersistentDataType.STRING, type.name());
        meta.getPersistentDataContainer().set(new NamespacedKey(PyMC.getInstance(), "level"), PersistentDataType.INTEGER, level);
        meta.getPersistentDataContainer().set(new NamespacedKey(PyMC.getInstance(), "secondary"), PersistentDataType.STRING, secondary.name());
        gem.setItemMeta(meta);
        return gem;
    }

    public static boolean isGem(ItemStack gem){
        return Objects.equals(gem.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(PyMC.getInstance(), "category"), PersistentDataType.STRING), "gem");
    }

    public static GemType getType(ItemStack gem) {
        return EnumUtils.getEnum(GemType.class, gem.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(PyMC.getInstance(), "type"), PersistentDataType.STRING));
    }

    public static AbstractSecondary.SecondaryType getSecondary(ItemStack gem) {
        return EnumUtils.getEnum(AbstractSecondary.SecondaryType.class, gem.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(PyMC.getInstance(), "secondary"), PersistentDataType.STRING));
    }

    public static Integer getLevel(ItemStack gem){
        return gem.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(PyMC.getInstance(), "level"), PersistentDataType.INTEGER);
    }

    public abstract void use(Player player, PlayerInteractEvent event);
}