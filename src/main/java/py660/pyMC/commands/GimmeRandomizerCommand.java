package py660.pyMC.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import py660.pyMC.substrates.AbstractSubstrate;

public final class GimmeRandomizerCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            ItemStack randomizer = AbstractSubstrate.buildSubstrate(AbstractSubstrate.SubstrateType.RANDOMIZER);
            player.getInventory().addItem(randomizer);
            return true;
        }
        return false;
    }
}
