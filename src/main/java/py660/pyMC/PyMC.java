package py660.pyMC;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import py660.pyMC.commands.*;
import py660.pyMC.data.CooldownHandler;
import py660.pyMC.data.DataHandler;

import java.util.Objects;

public final class PyMC extends JavaPlugin {
    private static PyMC instance;
    private static DataHandler dataHandler;
    private static CooldownHandler cooldownHandler;
    private volatile static TimerHUDThread timerHUDThread;

    public static PyMC getInstance() {
        return instance;
    }
    public static DataHandler getDataHandler() {
        return dataHandler;
    }
    public static CooldownHandler getCooldownHandler() {
        return cooldownHandler;
    }
    public static TimerHUDThread getTimerHUDThread() {
        return timerHUDThread;
    }

    @Override
    public void onEnable() {
        instance = this;
        dataHandler = new DataHandler();
        cooldownHandler = new CooldownHandler();
        timerHUDThread = new TimerHUDThread();

        getServer().getPluginManager().registerEvents(new PyListener(), this);
        Objects.requireNonNull(this.getCommand("gemupgrade")).setExecutor(new GemUpgradeCommand());
        Objects.requireNonNull(this.getCommand("gemactivate")).setExecutor(new GemActivateCommand());
        Objects.requireNonNull(this.getCommand("gimmegem")).setExecutor(new GimmeGemCommand());
        Objects.requireNonNull(this.getCommand("gimmesecondary")).setExecutor(new GimmeSecondaryCommand());
        Objects.requireNonNull(this.getCommand("gimmerandomizer")).setExecutor(new GimmeRandomizerCommand());
        Objects.requireNonNull(this.getCommand("gimmeweakupgrader")).setExecutor(new GimmeWeakUpgraderCommand());
        Objects.requireNonNull(this.getCommand("gimmeupgrader")).setExecutor(new GimmeUpgraderCommand());
        Objects.requireNonNull(this.getCommand("setcooldown")).setExecutor(new SetCooldownCommand());


        timerHUDThread.start();









        // Luca's crafting stuff

        ItemStack gemSwitcher = new ItemStack(Material.SEA_PICKLE, 1);
        ItemMeta gemSwitcherMeta = gemSwitcher.getItemMeta();
        gemSwitcherMeta.setDisplayName(ChatColor.GREEN + "Gem Switcher");
        // Add code for use
        gemSwitcher.setItemMeta(gemSwitcherMeta);

        ShapedRecipe gemSwitcherRecipe = new ShapedRecipe(new NamespacedKey(this, "gemswitcher"), gemSwitcher);
        gemSwitcherRecipe.shape("XYX", "BZB", "XYX");
        gemSwitcherRecipe.setIngredient('X', Material.DIAMOND_BLOCK);
        gemSwitcherRecipe.setIngredient('Y', Material.NETHERITE_INGOT);
        gemSwitcherRecipe.setIngredient('Z', Material.GRANITE);
        gemSwitcherRecipe.setIngredient('B', Material.TOTEM_OF_UNDYING);

        Bukkit.addRecipe(gemSwitcherRecipe);

        ItemStack gemUpgrader = new ItemStack(Material.WAXED_EXPOSED_CUT_COPPER_STAIRS, 1);
        ItemMeta gemUpgraderMeta = gemUpgrader.getItemMeta();
        gemUpgraderMeta.setDisplayName(ChatColor.GREEN + "Gem Upgrader");
        // Add code for use
        gemUpgrader.setItemMeta(gemUpgraderMeta);

        ShapedRecipe gemUpgraderRecipe = new ShapedRecipe(new NamespacedKey(this, "gemupgrader"), gemUpgrader);
        gemUpgraderRecipe.shape("XYX", "BZB", "XYX");
        gemUpgraderRecipe.setIngredient('X', Material.DIAMOND_BLOCK);
        gemUpgraderRecipe.setIngredient('Y', Material.NETHERITE_INGOT);
        gemUpgraderRecipe.setIngredient('Z', Material.NETHER_STAR);
        gemUpgraderRecipe.setIngredient('B', Material.TOTEM_OF_UNDYING);

        Bukkit.addRecipe(gemUpgraderRecipe);

    }

    @Override
    public void onDisable() {
        timerHUDThread.interrupt();
        timerHUDThread = null;
        // Plugin shutdown logic
    }
}
