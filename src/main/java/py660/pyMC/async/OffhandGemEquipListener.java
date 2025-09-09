package py660.pyMC.async;

import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryEvent;
import py660.pyMC.PyMC;
import py660.pyMC.items.Gem;

import java.util.List;

public class OffhandGemEquipListener implements Listener {
    @EventHandler
    public void onInventoryEvent(InventoryEvent event) {
        // probably could be an inventoryinteractevent, but just wanna be safe--either way, it's better than polling
        List<HumanEntity> viewers = event.getViewers();
        for (HumanEntity entity : viewers) {
            if (entity instanceof Player player) {
                if (Gem.isGem(player.getInventory().getItemInOffHand())) {
                    PyMC.getDataHandler().setPlayerGem(player, (Gem) player.getInventory().getItemInOffHand());
                }
            }
        }
    }
}
