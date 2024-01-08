package org.ex1de.dropitem.listener.onquit;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.ex1de.dropitem.core.player.PlayerUtility;
import org.ex1de.dropitem.core.player.playermemory.PlayerMemory;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class OnQuitService {
    private final JavaPlugin plugin;

    public OnQuitService(final JavaPlugin pluginToApply) {
        plugin = pluginToApply;
    }

    public void savePlayerData(@NotNull Player p) {
        UUID pId = p.getUniqueId();
        PlayerMemory pMemory = PlayerUtility.getMemory(p, plugin);

        PlayerUtility.savePlayerMemoryToFile(pId, pMemory, plugin);
    }
}
