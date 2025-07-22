package py660.pyMC.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import py660.pyMC.PyMC;
import py660.pyMC.guis.GemActivatorGUI;

import static org.bukkit.Bukkit.getServer;

public final class GemActivateCommand implements CommandExecutor {

    // This method is called, when somebody uses our command
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            GemActivatorGUI gemActivatorGUI = new GemActivatorGUI();
            getServer().getPluginManager().registerEvents(gemActivatorGUI, PyMC.getInstance());
            gemActivatorGUI.openInventory(player);
            return true;
        }
        return false;
    }
}