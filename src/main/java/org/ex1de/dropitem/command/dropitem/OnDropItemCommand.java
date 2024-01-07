package org.ex1de.dropitem.command.dropitem;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class OnDropItemCommand implements CommandExecutor {
    private final OnDropItemService onDropItemService;

    public OnDropItemCommand(@NotNull final JavaPlugin plugin) {
        this.onDropItemService = new OnDropItemService(plugin);
    }

    @Override
    public boolean onCommand(
            @NotNull CommandSender sender,
            @NotNull Command command,
            @NotNull String label,
            @NotNull String[] args
    ) {
        return false;
    }
}
