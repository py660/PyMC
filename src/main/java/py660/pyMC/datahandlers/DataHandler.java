package py660.pyMC.datahandlers;

import org.bukkit.entity.Player;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.CustomClassLoaderConstructor;
import org.yaml.snakeyaml.inspector.TagInspector;
import py660.pyMC.PyMC;
import py660.pyMC.gems.Gem;

import java.io.*;
import java.util.HashMap;

public final class DataHandler {
    public HashMap<Player, Long> combatPlayers = new HashMap<>();

    private final Yaml yaml;

    private final PlayerRegistry playerRegistry;
    private final File file = new File(PyMC.getInstance().getDataFolder(), "playerRegistry.yml");

    public DataHandler() {
        LoaderOptions loaderOptions = new LoaderOptions();
        TagInspector tagInspector = tag -> tag.getClassName().equals(PlayerRegistry.class.getName());
        loaderOptions.setTagInspector(tagInspector);
        yaml = new Yaml(new CustomClassLoaderConstructor(DataHandler.class.getClassLoader(), loaderOptions));

        file.getParentFile().mkdirs();
        InputStream inputStream;
        try {
            file.createNewFile();
            //InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("server.properties");
            inputStream = new FileInputStream(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        PlayerRegistry loadedRegistry = yaml.loadAs(inputStream, py660.pyMC.datahandlers.PlayerRegistry.class);
        if (loadedRegistry != null) { // if the file is empty, then it's null for some reason
            playerRegistry = loadedRegistry;
        } else {
            playerRegistry = new PlayerRegistry();
            dump();
        }
    }

    private void dump() {
        FileWriter writer;
        try {
            writer = new FileWriter(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        yaml.dump(playerRegistry, writer);
    }

    public void setPlayerGem(Player player, Gem gem) {
        playerRegistry.setPlayer(player, gem);
        dump();
    }

    public Gem getPlayerGem(Player player) {
        Gem gem = playerRegistry.getPlayer(player);
        if (gem == null) {
            gem = new Gem();
            setPlayerGem(player, gem);
        }
        return gem;
    }
}
