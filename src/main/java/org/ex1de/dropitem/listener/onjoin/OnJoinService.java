package org.ex1de.dropitem.listener.onjoin;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.ex1de.dropitem.core.player.PlayerUtility;
import org.ex1de.dropitem.core.player.playermemory.PlayerMemory;

import java.util.UUID;

public class OnJoinService {
    private final JavaPlugin plugin;

    public OnJoinService(final JavaPlugin pluginToApply) {
        plugin = pluginToApply;
    }

    public void loadPlayerData(Player p) {
        UUID pId = p.getUniqueId();
        PlayerMemory pMemory;

        if (!PlayerUtility.isPlayerDataFolderExists(pId, plugin)) {
            PlayerUtility.createDataFolderAndFile(pId, plugin);
        }

        pMemory = PlayerUtility.loadMemoryFromFile(pId, plugin);

        PlayerUtility.setMemory(p, pMemory, plugin);
    }
}
