package py660.pyMC.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import py660.pyMC.PyMC;

public final class SetCooldownCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            if (args.length != 3) {
                return false;
            }
            Player targetPlayer = Bukkit.getPlayer(args[0]);
            long length = Long.parseLong(args[2]);
            switch (args[1].toLowerCase()) {
                case "gem":
                    PyMC.getCooldownHandler().setGemCooldownPlayer(targetPlayer, length);
                    break;
                case "secondary":
                    PyMC.getCooldownHandler().setSecondaryCooldownPlayer(targetPlayer, length);
                case "combat":
                    if (length == 0) {
                        PyMC.getCooldownHandler().addCombatPlayer(player);
                        break;
                    } else if (length == 1) {
                        PyMC.getCooldownHandler().removeCombatPlayer(player);
                        break;
                    }
                default:
                    return false;
            }
            return true;
        }
        return false;
    }
}
