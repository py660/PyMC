package py660.pyMC.actions;

import org.bukkit.entity.Player;
import py660.pyMC.PyMC;
import py660.pyMC.items.AbstractSecondary;
import py660.pyMC.items.Gem;

public class SecondaryActionHandler {
    private SecondaryActionHandler() {
        throw new RuntimeException("You aren't supposed to initialize this class!");
    }
    public static void action(Player player) {
        Gem gem = PyMC.getDataHandler().getPlayerGem(player);
        switch(gem.getSecondary()) {
            case AbstractSecondary.SecondaryType.BEACON:
                beacon(player);
        }
    }

    private static void beacon(Player player) {

    }
}
