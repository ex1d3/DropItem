package org.ex1de.dropitem.listener.onpickupitem;

import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.plugin.java.JavaPlugin;
import org.ex1de.dropitem.core.item.ItemMemory;
import org.jetbrains.annotations.NotNull;

public class OnPickupItemListener implements Listener {
    private final JavaPlugin plugin;

    public OnPickupItemListener(final JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void entityPickItemHandler(@NotNull EntityPickupItemEvent e) {
        Item item = e.getItem();
        PersistentDataContainer pdc = item.getPersistentDataContainer();

        if (pdc.has(ItemMemory.getNamespacedKey(plugin))) {
            e.setCancelled(true);
        }
    }
}
