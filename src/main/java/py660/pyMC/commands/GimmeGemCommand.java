package py660.pyMC.commands;

import org.apache.commons.lang3.EnumUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import py660.pyMC.gems.AbstractGem;

public final class GimmeGemCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            if (args.length != 1){
                return false;
            }
            AbstractGem.GemType gemType = EnumUtils.getEnumIgnoreCase(AbstractGem.GemType.class, args[0]);
            if (gemType == null) {
                return false;
            }
            ItemStack gem = AbstractGem.buildGem(gemType, 1);
            player.getInventory().addItem(gem);
            return true;
        }
        return false;
    }
}
