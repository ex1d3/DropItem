package core.player.playermemory;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.ex1de.dropitem.DropItem;
import org.ex1de.dropitem.core.player.playermemory.PersistentPlayerMemory;
import org.ex1de.dropitem.core.player.playermemory.PlayerMemory;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("PlayerMemory")
class TestPlayerMemory {
    private static ServerMock server;
    private static JavaPlugin plugin;
    private Player player;

    @BeforeAll
    static void loadAll() {
        server = MockBukkit.mock();
        plugin = MockBukkit.load(DropItem.class);
    }

    @AfterAll
    static void unloadAll() {
        MockBukkit.unmock();
    }

    @BeforeEach
    void init() {
        player = server.addPlayer("John Doe");
    }

    @Test
    @DisplayName("namespacedKey")
    void namespacedKey() {
        NamespacedKey expected = new NamespacedKey(
                plugin,
                PersistentPlayerMemory.TOKEN
        );
        NamespacedKey actual = PlayerMemory.getNamespacedKey(plugin);

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("playerFolderPath")
    void getPlayerFolderPath() {
        String expected = String.format(
                "%s/%s",
                plugin.getDataFolder().getPath(),
                player.getUniqueId()
        );
        String actual = PlayerMemory.getPlayerFolderPath(
                player.getUniqueId(),
                plugin
        );

        assertEquals(expected, actual);
    }
}
