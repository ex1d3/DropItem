package org.ex1de.dropitem.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.ex1de.dropitem.core.player.PlayerUtility;
import org.ex1de.dropitem.core.player.playermemory.PlayerMemory;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class OnQuitListener implements Listener {
    public final JavaPlugin plugin;

    public OnQuitListener(final JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onQuitHandler(@NotNull PlayerQuitEvent e) {
        Player p = e.getPlayer();
        UUID pId = p.getUniqueId();
        PlayerMemory pMemory = PlayerUtility.getMemory(p, plugin);

        PlayerUtility.savePlayerMemoryToFile(pId, pMemory, plugin);
    }
}
