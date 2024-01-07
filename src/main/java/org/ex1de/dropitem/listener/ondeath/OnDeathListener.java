package org.ex1de.dropitem.listener.ondeath;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class OnDeathListener implements Listener {
    private final OnDeathService onDeathService;

    public OnDeathListener(final JavaPlugin plugin) {
        onDeathService = new OnDeathService(plugin);
    }
}
