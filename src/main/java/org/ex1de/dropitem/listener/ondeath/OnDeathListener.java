package org.ex1de.dropitem.listener.ondeath;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class OnDeathListener implements Listener {
    private final OnDeathService onDeathService;

    public OnDeathListener(final JavaPlugin plugin) {
        onDeathService = new OnDeathService(plugin);
    }

    @EventHandler
    public boolean onDeathHandler(PlayerDeathEvent e) {
        Player p = e.getPlayer();
        onDeathService.dropItemsFromDropList(p);

        return true;
    }
}
