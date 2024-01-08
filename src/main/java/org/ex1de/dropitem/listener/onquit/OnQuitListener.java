package org.ex1de.dropitem.listener.onquit;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public final class OnQuitListener implements Listener {
    public final OnQuitService onQuitService;

    public OnQuitListener(final JavaPlugin plugin) {
        onQuitService = new OnQuitService(plugin);
    }

    @EventHandler
    public void onQuitHandler(@NotNull PlayerQuitEvent e) {
        onQuitService.savePlayerData(e.getPlayer());
    }
}
