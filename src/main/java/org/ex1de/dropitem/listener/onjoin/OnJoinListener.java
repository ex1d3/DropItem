package org.ex1de.dropitem.listener.onjoin;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class OnJoinListener implements Listener {
    private final OnJoinService onJoinService;

    public OnJoinListener(final JavaPlugin plugin) {
        onJoinService = new OnJoinService(plugin);
    }

    @EventHandler
    public void onJoinHandler(PlayerJoinEvent pJoinEvent) {
        Player p = pJoinEvent.getPlayer();
        onJoinService.loadPlayerData(p);
    }
}
