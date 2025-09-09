package py660.pyMC.async;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import py660.pyMC.PyMC;
import py660.pyMC.actions.GemActionHandler;
import py660.pyMC.actions.SecondaryActionHandler;
import py660.pyMC.items.Gem;

public final class ActionListener implements Listener {
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Gem gem = PyMC.getDataHandler().getPlayerGem(player);
        ItemStack item = event.getItem();
        if (Gem.isGem(item)) {
            Action action = event.getAction();
            if (action == Action.LEFT_CLICK_AIR || action == Action.LEFT_CLICK_BLOCK) {
                player.sendMessage("Thank you for left clicking with a gem!");
                GemActionHandler.action(player);
            } else if (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {
                player.sendMessage("Thank you for right clicking with a gem!");
                SecondaryActionHandler.action(player); 
            }

        }
    }
}
