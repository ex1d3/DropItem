package org.ex1de.dropitem.listener.onjoin;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class OnJoinListener implements Listener {
    private final OnJoinService onJoinService;

    public OnJoinListener(final JavaPlugin plugin) {
        onJoinService = new OnJoinService(plugin);
    }
}
