package org.ex1de.dropitem;

import org.bukkit.Server;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.ex1de.dropitem.command.dropitem.OnDropItemCommand;
import org.ex1de.dropitem.listener.ondeath.OnDeathListener;
import org.ex1de.dropitem.listener.onjoin.OnJoinListener;
import org.ex1de.dropitem.listener.onquit.OnQuitListener;

public final class Dropitem extends JavaPlugin {

    @Override
    public void onEnable() {
        Server server = this.getServer();
        PluginManager pluginManager = server.getPluginManager();

        pluginManager.registerEvents(new OnJoinListener(this), this);
        pluginManager.registerEvents(new OnDeathListener(this), this);
        pluginManager.registerEvents(new OnQuitListener(this), this);

        this.getCommand("dropitem").setExecutor(new OnDropItemCommand(this));

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
