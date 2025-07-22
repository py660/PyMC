package py660.pyMC.items;

import org.bukkit.event.Listener;

public abstract class Action implements Listener {
    private final Gem gem;

    public Action(Gem gem) {
        this.gem = gem;
    }

    public abstract void onUse();
}
