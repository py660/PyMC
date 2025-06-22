package py660.pyMC.commands;

import org.apache.commons.lang3.EnumUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import py660.pyMC.secondaries.AbstractSecondary;

public final class GimmeSecondaryCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            if (args.length != 1){
                return false;
            }
            AbstractSecondary.SecondaryType secondaryType = EnumUtils.getEnumIgnoreCase(AbstractSecondary.SecondaryType.class, args[0]);
            if (secondaryType == null) {
                return false;
            }
            ItemStack secondary = AbstractSecondary.buildSecondary(secondaryType);
            player.getInventory().addItem(secondary);
            return true;
        }
        return false;
    }
}
