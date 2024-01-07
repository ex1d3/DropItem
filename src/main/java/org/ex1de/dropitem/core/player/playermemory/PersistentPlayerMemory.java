package org.ex1de.dropitem.core.player.playermemory;

import lombok.SneakyThrows;
import org.apache.commons.lang3.SerializationUtils;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;

public class PersistentPlayerMemory implements PersistentDataType<byte[], PlayerMemory> {
    public static final String TOKEN = "player_memory";

    @Override
    public @NotNull Class<byte[]> getPrimitiveType() {
        return byte[].class;
    }

    @Override
    public @NotNull Class<PlayerMemory> getComplexType() {
        return PlayerMemory.class;
    }

    @Override
    public byte @NotNull [] toPrimitive(
            @NotNull PlayerMemory complex,
            @NotNull PersistentDataAdapterContext context
    ) {
        return SerializationUtils.serialize(complex);
    }

    @SneakyThrows
    @Override
    public PlayerMemory fromPrimitive(
            byte @NotNull [] primitive,
            @NotNull PersistentDataAdapterContext context
    ) {
        InputStream inputStream = new ByteArrayInputStream(primitive);
        ObjectInputStream objectInputStream = new ObjectInputStream(
                inputStream
        );

        return (PlayerMemory) objectInputStream.readObject();
    }
}
