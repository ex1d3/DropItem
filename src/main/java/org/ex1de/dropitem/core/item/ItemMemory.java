package org.ex1de.dropitem.core.item;

import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public final class ItemMemory {
    @NotNull
    public static NamespacedKey getNamespacedKey(JavaPlugin plugin) {
        return new NamespacedKey(
                plugin,
                "isPickupable"
        );
    }
}
