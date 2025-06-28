package py660.pyMC.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import py660.pyMC.items.AbstractSubstrate;

public final class GimmeUpgraderCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            ItemStack upgrader = AbstractSubstrate.buildSubstrate(AbstractSubstrate.SubstrateType.UPGRADER);
            player.getInventory().addItem(upgrader);
            return true;
        }
        return false;
    }
}
