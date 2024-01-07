package org.ex1de.dropitem.core.player.playermemory;

import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

public class PersistentPlayerMemory implements PersistentDataType<byte[], PlayerMemory> {
    @Override
    public @NotNull Class<byte[]> getPrimitiveType() {
        return null;
    }

    @Override
    public @NotNull Class<PlayerMemory> getComplexType() {
        return null;
    }

    @Override
    public byte @NotNull [] toPrimitive(@NotNull PlayerMemory complex, @NotNull PersistentDataAdapterContext context) {
        return new byte[0];
    }

    @Override
    public @NotNull PlayerMemory fromPrimitive(byte @NotNull [] primitive, @NotNull PersistentDataAdapterContext context) {
        return null;
    }
}
