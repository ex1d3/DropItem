package org.ex1de.dropitem.core.player.playermemory;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.EnumMap;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
public class PlayerMemory implements Serializable {
    private Map<Material, Integer> dropItems;

    public PlayerMemory() {
        this.dropItems = new EnumMap<>(Material.class);
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
