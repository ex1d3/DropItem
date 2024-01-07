package org.ex1de.dropitem.command.dropitem;

import org.bukkit.plugin.java.JavaPlugin;

public class OnDropItemService {
    private final JavaPlugin plugin;

    public OnDropItemService(final JavaPlugin pluginToApply) {
        this.plugin = pluginToApply;
    }
}
