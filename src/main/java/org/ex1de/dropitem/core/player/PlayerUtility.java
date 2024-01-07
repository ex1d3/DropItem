package org.ex1de.dropitem.core.player;

import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.plugin.java.JavaPlugin;
import org.ex1de.dropitem.core.player.playermemory.PersistentPlayerMemory;
import org.ex1de.dropitem.core.player.playermemory.PlayerMemory;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public final class PlayerUtility {
    private PlayerUtility() {  }

    @NotNull
    public static PlayerMemory getMemory(
            @NotNull Player p,
            @NotNull JavaPlugin plugin) {
        PersistentDataContainer data = p.getPersistentDataContainer();

        return Objects.requireNonNull(data.get(
                PlayerMemory.getNamespacedKey(plugin),
                new PersistentPlayerMemory()
        ));
    }

    public static void setMemory(
            @NotNull Player p,
            @NotNull PlayerMemory pMemory,
            @NotNull JavaPlugin plugin
    ) {
        PersistentDataContainer data = p.getPersistentDataContainer();

        data.set(
                PlayerMemory.getNamespacedKey(plugin),
                new PersistentPlayerMemory(),
                pMemory
        );
    }
}
