package org.ex1de.dropitem.listener;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.ex1de.dropitem.core.item.ItemMemory;
import org.ex1de.dropitem.core.player.PlayerUtility;
import org.ex1de.dropitem.core.player.playermemory.PlayerMemory;

import java.util.Map;

public class OnDeathListener implements Listener {
    private final JavaPlugin plugin;

    public OnDeathListener(final JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onDeathHandler(PlayerDeathEvent e) {
        Player p = e.getPlayer();
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

            Item item =
                    block.getWorld().dropItemNaturally(location, itemStack);
            PersistentDataContainer pdc = item.getPersistentDataContainer();

            pdc.set(
                    ItemMemory.getNamespacedKey(plugin),
                    PersistentDataType.BOOLEAN,
                    true
            );
        }
    }
}
