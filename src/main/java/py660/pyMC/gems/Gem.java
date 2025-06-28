package py660.pyMC.gems;

import org.apache.commons.lang3.EnumUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemRarity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import py660.pyMC.PyMC;
import py660.pyMC.RomanNumeral;
import py660.pyMC.secondaries.AbstractSecondary;

import java.util.*;

public final class Gem extends ItemStack {
    public enum GemType {
        DB_MISSING_GEM("DB_MISSING_GEM"),
        BEACON("Beacon Gem"),
        LAVA_RAIN("Lava Rain Gem"),
        SPACETIME("Spacetime Continuum™ Gem"),
        ENDER("Ender Gem"),
        CHICKEN_JOCKEY("Chicken Jockey Gem"),
        RICK_ASTLEY("Rick Astley Gem");

        private final String title;
        GemType(String title) {
            this.title = title;
        }
        public String getTitle() {
            return title;
        }
    }

    public static List<GemType> obtainableTypes = List.of(
            GemType.BEACON,
            GemType.LAVA_RAIN,
            GemType.SPACETIME,
            GemType.ENDER,
            GemType.CHICKEN_JOCKEY,
            GemType.RICK_ASTLEY
    );

    public static int MAX_LEVEL = 4;
    public static int MIN_LEVEL = -5;

    private final static Material MATERIAL = Material.PHANTOM_MEMBRANE;

    private final GemType gemType;
    private final int level;
    private final AbstractSecondary.SecondaryType secondary;

    public static boolean isGem(ItemStack gem) {
        return Objects.equals(gem.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(PyMC.getInstance(), "category"), PersistentDataType.STRING), "gem");
    }

    public Gem(ItemStack gem) {
        super(MATERIAL, 1);
        if (!isGem(gem)) {
            throw new RuntimeException("bro did not check if the item was a gem before creating the gem object (LOL) xD");
        }
        PersistentDataContainer pdc = gem.getItemMeta().getPersistentDataContainer();
        gemType = EnumUtils.getEnum(GemType.class, pdc.get(new NamespacedKey(PyMC.getInstance(), "type"), PersistentDataType.STRING));
        level = pdc.get(new NamespacedKey(PyMC.getInstance(), "level"), PersistentDataType.INTEGER);
        secondary = EnumUtils.getEnum(AbstractSecondary.SecondaryType.class, pdc.get(new NamespacedKey(PyMC.getInstance(), "secondary"), PersistentDataType.STRING));
        setSelfMeta();
    }

    public Gem() {
        super(MATERIAL, 1);
        ArrayList<GemType> obtainableTypesCopy = new ArrayList<>(obtainableTypes);
        Collections.shuffle(obtainableTypesCopy);
        gemType = obtainableTypesCopy.getFirst();
        level = 1;
        secondary = AbstractSecondary.SecondaryType.DEFAULT;
        setSelfMeta();
    }

    public Gem(GemType gemType, int level) {
        super(MATERIAL, 1);
        this.gemType = gemType;
        this.level = level;
        this.secondary = AbstractSecondary.SecondaryType.DEFAULT;
        setSelfMeta();
    }

    public Gem(GemType gemType, int level, AbstractSecondary.SecondaryType secondary) {
        super(MATERIAL, 1);
        this.gemType = gemType;
        this.level = level;
        this.secondary = secondary;
        setSelfMeta();
    }

    private void setSelfMeta() {
        //System.out.println(level);
        ItemMeta meta = this.getItemMeta();
        assert meta != null;
        meta.setDisplayName(gemType.title);
        meta.setRarity(ItemRarity.EPIC);
        meta.setLore(Arrays.asList("" + ChatColor.YELLOW + ChatColor.BOLD + "Level " + RomanNumeral.toRoman(level) + ChatColor.RESET, "Secondary: " + secondary.getTitle()));
        meta.setMaxStackSize(1);
        meta.setItemModel(new NamespacedKey(PyMC.getInstance(), "gems/" + gemType.name()));
        meta.setEnchantmentGlintOverride(true);
        meta.getPersistentDataContainer().set(new NamespacedKey(PyMC.getInstance(), "category"), PersistentDataType.STRING, "gem");
        meta.getPersistentDataContainer().set(new NamespacedKey(PyMC.getInstance(), "type"), PersistentDataType.STRING, gemType.name());
        meta.getPersistentDataContainer().set(new NamespacedKey(PyMC.getInstance(), "level"), PersistentDataType.INTEGER, level);
        meta.getPersistentDataContainer().set(new NamespacedKey(PyMC.getInstance(), "secondary"), PersistentDataType.STRING, secondary.name());
        this.setItemMeta(meta);
    }

    public GemType getGemType() {
        return gemType;
    }

    public int getLevel() {
        return level;
    }

    public AbstractSecondary.SecondaryType getSecondary() {
        return secondary;
    }
}
