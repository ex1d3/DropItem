package core.player;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.plugin.java.JavaPlugin;
import org.ex1de.dropitem.DropItem;
import org.ex1de.dropitem.core.player.PlayerUtility;
import org.ex1de.dropitem.core.player.playermemory.PersistentPlayerMemory;
import org.ex1de.dropitem.core.player.playermemory.PlayerMemory;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.*;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("PlayerUtility")
class TestPlayerUtility {
    private static ServerMock server;
    private static JavaPlugin plugin;
    private Player p;

    @BeforeAll
    static void loadAll() {
        server = MockBukkit.mock();
        plugin = MockBukkit.load(DropItem.class);
        // Implicit player initializing shutdown :)
        server.getPluginManager().unregisterPluginEvents(plugin);
    }

    @AfterAll
    static void unloadAll() {
        MockBukkit.unmock();
    }

    @BeforeEach
    void init() {
        p = server.addPlayer("John Doe");
    }

    @DisplayName("getMemory test")
    @Test
    void getMemory() throws IllegalAccessException {
        PersistentDataContainer pdc = p.getPersistentDataContainer();
        NamespacedKey namespacedKey =
                new NamespacedKey(plugin, PersistentPlayerMemory.TOKEN);
        PersistentPlayerMemory persistentPlayerMemory =
                new PersistentPlayerMemory();
        PlayerMemory pMemory = new PlayerMemory();

        pdc.set(
                namespacedKey,
                persistentPlayerMemory,
                pMemory
        );

        PlayerMemory expected = pdc.get(
                namespacedKey,
                persistentPlayerMemory
        );
        PlayerMemory actual =  PlayerUtility.getMemory(p, plugin);

        assertPlayerMemory(expected, actual);
    }

    @DisplayName("setMemory test")
    @Test
    void setMemory() throws IllegalAccessException {
        Player p1 = server.addPlayer("Jane Doe");
        PersistentDataContainer pdc = p.getPersistentDataContainer();
        PersistentDataContainer pdc1 = p1.getPersistentDataContainer();

        NamespacedKey namespacedKey =
                new NamespacedKey(plugin, PersistentPlayerMemory.TOKEN);
        PersistentPlayerMemory persistentPlayerMemory =
                new PersistentPlayerMemory();
        PlayerMemory pMemory = new PlayerMemory();


        pdc.set(
                namespacedKey,
                persistentPlayerMemory,
                pMemory
        );

        PlayerUtility.setMemory(p1, pMemory, plugin);

        PlayerMemory expected = pdc.get(namespacedKey, persistentPlayerMemory);
        PlayerMemory actual = pdc1.get(namespacedKey, persistentPlayerMemory);

        assertPlayerMemory(expected, actual);
    }

    // bc bukkit is 100% shit :(
    private void assertPlayerMemory(
            @NotNull PlayerMemory expected,
            @NotNull PlayerMemory actual
    ) throws IllegalAccessException {
        Field[] fields = expected.getClass().getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);

            assertEquals(field.get(expected), field.get(actual));
        }
    }
}
