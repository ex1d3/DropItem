package org.ex1de.dropitem.command.dropitem;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.ex1de.dropitem.core.material.MaterialUtility;
import org.ex1de.dropitem.core.player.PlayerUtility;
import org.ex1de.dropitem.core.player.playermemory.PlayerMemory;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;

public class OnDropItemCommand implements CommandExecutor, TabCompleter {
    private final JavaPlugin plugin;
    private final Integer MINIMAL_ARGS_LENGTH = 2;
    private final Integer MAX_ARGS_LENGTH = 4;

    public OnDropItemCommand(@NotNull final JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(
            @NotNull CommandSender sender,
            @NotNull Command command,
            @NotNull String label,
            @NotNull String[] args
    ) {
        if (args.length < MINIMAL_ARGS_LENGTH
                || args.length > MAX_ARGS_LENGTH) {
            return false;
        }

        Player p = plugin.getServer().getPlayer(args[ArgsIndex.NICKNAME]);

        if (p == null) {
            return false;
        }

        PlayerMemory pMemory = PlayerUtility.getMemory(p, plugin);

        String rawMessage = executeCommandAction(
                pMemory,
                args[ArgsIndex.ACTION_TYPE],
                args
        );

        PlayerUtility.setMemory(p, pMemory, plugin);

        sender.sendMessage(
                String.format(
                        ChatColor.BLUE + "DropItem "
                                + ChatColor.GREEN + "(%s) "
                                + ChatColor.GOLD + "- "
                                + ChatColor.GRAY + "%s",
                        p.getName(), rawMessage
                )
        );

        return true;
    }

    public @NotNull List<String> onTabComplete(
            @NotNull CommandSender sender,
            @NotNull Command command,
            @NotNull String label,
            @NotNull String[] args
    ) {

        if (args.length == 1) {
            List<Player> players = new ArrayList<>(
                    plugin.getServer().getOnlinePlayers()
            );

            return players.stream()
                    .map(Player::getName)
                    .filter(name ->
                            name.startsWith(args[ArgsIndex.NICKNAME])
                    )
                    .collect(Collectors.toList());
        } else if (args.length == 2) {
            return Arrays.asList(
                    ActionType.GET,
                    ActionType.ADD,
                    ActionType.REMOVE,
                    ActionType.CLEAR
            );
        } else if (args.length == 3) {
            boolean isRemoveAction = Objects.equals(
                    args[ArgsIndex.ACTION_TYPE],
                    ActionType.REMOVE
            );
            String materialNameToSearch = args[ArgsIndex.MATERIAL];
            Material[] materials;

            Player p = plugin.getServer().getPlayer(args[ArgsIndex.NICKNAME]);

            if (p == null) {
                return new ArrayList<>();
            }

            if (isRemoveAction) {
                PlayerMemory pMemory = PlayerUtility.getMemory(
                        p,
                        plugin
                );
                Map<Material, Integer> dropList = pMemory.getDropList();

                materials = dropList.keySet().toArray(new Material[0]);
            } else {
                materials = Material.values();
            }

            return MaterialUtility.filterMaterialsByName(
                    materials,
                    materialNameToSearch
            );
        }

        return new ArrayList<>();
    }

    @NotNull
    private String executeCommandAction(
            @NotNull PlayerMemory pMemory,
            @NotNull String type,
            @NotNull String[] args
    ) {
        String invalidItemNameMessage = "Invalid item name";
        String invalidItemCountMessage = "Invalid item count";

        switch (type) {
            case ActionType.GET -> {
                return getDropList(pMemory, args);
            }
            case ActionType.CLEAR -> {
                return clearDropList(pMemory, args);
            }
            case ActionType.ADD -> {
                return addItemToDropList(
                        pMemory,
                        args,
                        invalidItemNameMessage,
                        invalidItemCountMessage
                );
            }
            case ActionType.REMOVE -> {
                return removeItemFromDropList(
                        pMemory,
                        args,
                        invalidItemNameMessage,
                        invalidItemCountMessage
                );
            }
            default -> {
                return "Invalid action type";
            }
        }
    }

    @NotNull
    private String removeItemFromDropList(
            @NotNull PlayerMemory pMemory,
            @NotNull String[] args,
            String invalidItemNameMessage,
            String invalidItemCountMessage
    ) {
        if (args.length < MAX_ARGS_LENGTH) {
            return "Invalid args count";
        }

        Material material = Material.getMaterial(args[ArgsIndex.MATERIAL]);
        int count;

        if (material == null) {
            return invalidItemNameMessage;
        }

        try {
            count = getAndValidateItemCount(args);

            Map<Material, Integer> dropList = pMemory.getDropList();
            int materialCountInDropList = dropList.get(material);

            if (materialCountInDropList < count) {
                throw new Exception();
            }

            dropList.put(
                    material,
                    materialCountInDropList - count
            );
        } catch (Exception e) {
            return invalidItemCountMessage;
        }

        return String.format(
                "%s (%d pcs.) successfully removed from drop list",
                material.name(),
                count
        );
    }

    @NotNull
    private String addItemToDropList(
            @NotNull PlayerMemory pMemory,
            @NotNull String[] args,
            String invalidItemNameMessage,
            String invalidItemCountMessage
    ) {
        if (args.length < MAX_ARGS_LENGTH) {
            return "Invalid args count";
        }

        Material material = Material.getMaterial(args[ArgsIndex.MATERIAL]);
        int count;

        if (material == null) {
            return invalidItemNameMessage;
        }

        try {
            count = getAndValidateItemCount(args);

            Map<Material, Integer> dropList = pMemory.getDropList();
            int countToStore;

            if (dropList.containsKey(material)) {
                countToStore = dropList.get(material) + count;
            } else {
                countToStore = count;
            }

            dropList.put(material, countToStore);
        } catch (Exception e) {
            return invalidItemCountMessage;
        }

        return String.format(
                "%s (%d pcs.) successfully added to drop list",
                material.name(),
                count
        );
    }

    private int getAndValidateItemCount(String[] args) throws Exception {
        int count = Integer.parseInt(
                Objects.requireNonNull(args[ArgsIndex.COUNT])
        );

        if (Integer.signum(count) != 1) {
            throw new Exception();
        }

        return count;
    }

    @NotNull
    private String clearDropList(
            @NotNull PlayerMemory pMemory,
            @NotNull String[] args
    ) {
        if (args.length > MINIMAL_ARGS_LENGTH) {
            return "Invalid args count";
        }
        pMemory.setDropList(new EnumMap<>(Material.class));
        return "drop list successfully cleaned";
    }

    @NotNull
    private String getDropList(
            @NotNull PlayerMemory pMemory,
            @NotNull String[] args
    ) {
        if (args.length > MINIMAL_ARGS_LENGTH) {
            return "Invalid args count";
        }

        String message = pMemory.getDropList()
                .toString()
                .replace("{", "")
                .replace("}", "")
                .replace("=", " = ");

        if (message.length() == 0) {
            message = "drop list is empty!";
        }

        return message;
    }

    private static final class ArgsIndex {
        public static final Integer NICKNAME = 0;
        public static final Integer ACTION_TYPE = 1;
        public static final Integer MATERIAL = 2;
        public static final Integer COUNT = 3;

        private ArgsIndex() {  }
    }


    private static final class ActionType {
        public static final String GET = "get";
        public static final String ADD = "add";
        public static final String REMOVE = "remove";
        public static final String CLEAR = "clear";

        private ActionType() {  }
    }
}
