package py660.pyMC.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import py660.pyMC.PyMC;
import py660.pyMC.guis.GemReactorGUI;

import static org.bukkit.Bukkit.getServer;

public final class GemUpgradeCommand implements CommandExecutor {

    // This method is called, when somebody uses our command
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            GemReactorGUI gemReactorGUI = new GemReactorGUI();
            getServer().getPluginManager().registerEvents(gemReactorGUI, PyMC.getInstance());
            gemReactorGUI.openInventory(player);
            return true;
        }
        return false;
    }
}