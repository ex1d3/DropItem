package org.ex1de.dropitem.listener.ondeath;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.ex1de.dropitem.core.player.PlayerUtility;
import org.ex1de.dropitem.core.player.playermemory.PlayerMemory;

import java.util.Map;

public class OnDeathService {
    private final JavaPlugin plugin;

    public OnDeathService(final JavaPlugin pluginToApply) {
        plugin = pluginToApply;
    }

    public void dropItemsFromDropList(Player p) {
        float centerOfBlock = 0.5F;

        PlayerMemory pMemory = PlayerUtility.getMemory(p, plugin);
        Map<Material, Integer> dropList = pMemory.getDropList();
        Location location = p.getLocation();

        location.add(centerOfBlock, centerOfBlock, centerOfBlock);

        Block block = location.getBlock();

        for (Map.Entry<Material, Integer> entry : dropList.entrySet()) {
            ItemStack itemStack = new ItemStack(
                    entry.getKey(),
                    entry.getValue()
            );
            block.getWorld().dropItemNaturally(location, itemStack);
        }
    }
}

