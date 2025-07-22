package py660.pyMC.data;

import org.apache.commons.lang3.EnumUtils;
import py660.pyMC.items.AbstractSecondary;
import py660.pyMC.items.Gem;

public final class PlayerEntry {
    private String type;
    private int level;
    private String secondary;

    public PlayerEntry() {
        type = Gem.GemType.DB_MISSING_GEM.name();
        level = Gem.MIN_LEVEL;
        secondary = AbstractSecondary.SecondaryType.DEFAULT.name();
    }

    public PlayerEntry(Gem gem) {
        type = gem.getGemType().name();
        level = gem.getLevel();
        secondary = gem.getSecondary().name();
    }

    public Gem getGem() {
        return new Gem(EnumUtils.getEnum(Gem.GemType.class, type), level, EnumUtils.getEnum(AbstractSecondary.SecondaryType.class, secondary));
    }

    // JavaBean stuff, please don't touch! :)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getSecondary() {
        return secondary;
    }

    public void setSecondary(String secondary) {
        this.secondary = secondary;
    }
}
