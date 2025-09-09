package py660.pyMC.actions;

import org.bukkit.entity.Player;
import py660.pyMC.PyMC;
import py660.pyMC.items.Gem;

public final class GemActionHandler {
    private GemActionHandler() {
        throw new RuntimeException("You aren't supposed to initialize this class!");
    }
    public static void action(Player player) {
        Gem gem = PyMC.getDataHandler().getPlayerGem(player);
        switch(gem.getGemType()) {
            case Gem.GemType.BEACON:

        }
    }

    private static void beacon(Player player) {

    }
}
