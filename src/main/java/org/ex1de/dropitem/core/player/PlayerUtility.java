package org.ex1de.dropitem.core.player;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.plugin.java.JavaPlugin;
import org.ex1de.dropitem.core.player.playermemory.PersistentPlayerMemory;
import org.ex1de.dropitem.core.player.playermemory.PlayerMemory;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public final class PlayerUtility {
    private static final String DROP_LIST_TOKEN = "dropItems";
    private static final String PLAYER_DATA_FILE_PATH = "/general.yml";

    private PlayerUtility() {  }

    @NotNull
    public static PlayerMemory getMemory(
            @NotNull Player p,
            @NotNull JavaPlugin plugin) {
        PersistentDataContainer pdc = p.getPersistentDataContainer();

        return Objects.requireNonNull(pdc.get(
                PlayerMemory.getNamespacedKey(plugin),
                new PersistentPlayerMemory()
        ));
    }

    public static void setMemory(
            @NotNull Player p,
            @NotNull PlayerMemory pMemory,
            @NotNull JavaPlugin plugin
    ) {
        PersistentDataContainer pdc = p.getPersistentDataContainer();

        pdc.set(
                PlayerMemory.getNamespacedKey(plugin),
                new PersistentPlayerMemory(),
                pMemory
        );
    }

    public static void savePlayerMemoryToFile(
            @NotNull UUID pId,
            @NotNull PlayerMemory pMemory,
            @NotNull JavaPlugin plugin
    ) {
        String pFolderPath = PlayerMemory.getPlayerFolderPath(
                pId,
                plugin
        );
        File file = new File(pFolderPath + PLAYER_DATA_FILE_PATH);

        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);

        saveDropList(pMemory.getDropList(), cfg);

        try {
            cfg.save(file);
        } catch (Exception ignore) {  }
    }

    private static void saveDropList(
            @NotNull Map<Material, Integer> dropList,
            @NotNull FileConfiguration cfg
    ) {
        cfg.createSection(DROP_LIST_TOKEN);

        String token = DROP_LIST_TOKEN + ".";

        for (Map.Entry<Material, Integer> entry : dropList.entrySet()) {
            cfg.set(token + entry.getKey(), entry.getValue());
        }
    }


    @NotNull
    public static PlayerMemory loadMemoryFromFile(
            @NotNull UUID pId,
            @NotNull JavaPlugin plugin
    ) {
        PlayerMemory pMemory = new PlayerMemory();
        String pFolderPath = PlayerMemory.getPlayerFolderPath(
                pId,
                plugin
        );
        File file = new File(pFolderPath + PLAYER_DATA_FILE_PATH);
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);

        pMemory.setDropList(loadMemoryDropList(cfg));

        return pMemory;
    }

    @NotNull
    private static Map<Material, Integer> loadMemoryDropList(
            @NotNull FileConfiguration cfg
    ) {
        Map<Material, Integer> dropList = new EnumMap<>(Material.class);

        Objects.requireNonNull(cfg.getConfigurationSection(DROP_LIST_TOKEN))
                .getKeys(false)
                .forEach(key -> {
                    Material material = Material.getMaterial(key);
                    Integer count = (int) Objects.requireNonNull(
                            cfg.get(DROP_LIST_TOKEN + "." + key)
                    );

                    dropList.put(material, count);
                });

        return dropList;
    }

    @NotNull
    public static Boolean isPlayerDataFolderExists(
            @NotNull UUID pId,
            @NotNull JavaPlugin plugin
    ) {
        Path path = Paths.get(
                PlayerMemory.getPlayerFolderPath(pId, plugin)
        );

        return Files.exists(path);
    }

    public static void createDataFolderAndFile(
            @NotNull UUID pId,
            @NotNull JavaPlugin plugin
    ) {
        File f = new File(PlayerMemory.getPlayerFolderPath(pId, plugin));
        f.mkdirs();

        try {
            f = new File(f.getPath() + PLAYER_DATA_FILE_PATH);
            f.createNewFile();

            savePlayerMemoryToFile(pId, new PlayerMemory(), plugin);
        } catch (Exception ignore) {  }
    }
}
