package org.ex1de.dropitem.listener.onquit;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class OnQuitListener implements Listener {
    public final OnQuitService onQuitService;

    public OnQuitListener(final JavaPlugin plugin) {
        onQuitService = new OnQuitService(plugin);
    }
}
