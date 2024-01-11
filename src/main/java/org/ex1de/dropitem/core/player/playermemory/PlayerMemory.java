package org.ex1de.dropitem.core.player.playermemory;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.*;

@Getter
@Setter
public class PlayerMemory implements Serializable {
    private Map<Material, Integer> dropList;
    private List<Material> cachedSearchItems;

    public PlayerMemory() {
        dropList = new EnumMap<>(Material.class);
        cachedSearchItems = Arrays.stream(Material.values()).toList();
    }

    @NotNull
    public static NamespacedKey getNamespacedKey(JavaPlugin plugin) {
        return new NamespacedKey(
                plugin,
                PersistentPlayerMemory.TOKEN
        );
    }

    @NotNull
    public static String getPlayerFolderPath(
            UUID playerId,
            @NotNull JavaPlugin plugin
    ) {
        return String.format(
                "%s/%s",
                plugin.getDataFolder().getPath(),
                playerId
        );
    }
}
