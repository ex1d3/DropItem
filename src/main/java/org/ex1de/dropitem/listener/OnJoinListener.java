package org.ex1de.dropitem.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.ex1de.dropitem.core.player.PlayerUtility;
import org.ex1de.dropitem.core.player.playermemory.PlayerMemory;

import java.util.UUID;

public class OnJoinListener implements Listener {
    private final JavaPlugin plugin;

    public OnJoinListener(final JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoinHandler(PlayerJoinEvent pJoinEvent) {
        Player p = pJoinEvent.getPlayer();

        UUID pId = p.getUniqueId();
        PlayerMemory pMemory;

        if (!PlayerUtility.isPlayerDataFolderExists(pId, plugin)) {
            PlayerUtility.createDataFolderAndFile(pId, plugin);
        }

        pMemory = PlayerUtility.loadMemoryFromFile(pId, plugin);

        PlayerUtility.setMemory(p, pMemory, plugin);
    }
}
