package org.ex1de.dropitem.command.dropitem;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.ex1de.dropitem.core.player.PlayerUtility;
import org.ex1de.dropitem.core.player.playermemory.PlayerMemory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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

        Player p = (Player) sender;
        PlayerMemory pMemory = PlayerUtility.getMemory(p, plugin);

        String rawMessage = executeCommandAction(
                pMemory,
                args[CommandArgsLocation.ACTION_TYPE],
                args
        );

        PlayerUtility.setMemory(p, pMemory, plugin);

        sender.sendMessage(
                String.format(
                        ChatColor.BLUE + "DropItem "
                                + ChatColor.GREEN + "(%s) "
                                + ChatColor.GOLD + "- "
                                + ChatColor.GRAY + "%s",
                        p.getName(), rawMessage)
        );

        return true;
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
            case ActionType.CLEAR -> {
                if (args.length > MINIMAL_ARGS_LENGTH) {
                    return "Invalid args count";
                }
                pMemory.setDropList(new EnumMap<>(Material.class));
                return "dropList successfully cleaned";
            }
            case ActionType.ADD -> {
                if (args.length < MAX_ARGS_LENGTH) {
                    return "Invalid args count";
                }

                Material material = Material.getMaterial(args[CommandArgsLocation.MATERIAL]);
                int count;

                if (material == null) {
                    return invalidItemNameMessage;
                }

                try {
                    count = Integer.parseInt(
                            args[CommandArgsLocation.COUNT]
                    );

                    if (Integer.signum(count) != 1) {
                        throw new Exception();
                    }

                    Map<Material, Integer> dropList = pMemory.getDropList();
                    int countToStore = count;

                    if (dropList.containsKey(material)) {
                        countToStore = dropList.get(material) + count;
                    }

                    dropList.put(material, countToStore);
                } catch (Exception e) {
                    return invalidItemCountMessage;
                }

                return String.format(
                        "%s (%d pcs.) successfully added to dropList",
                        material.name(),
                        count
                );
            }
            case ActionType.REMOVE -> {
                if (args.length < MAX_ARGS_LENGTH) {
                    return "Invalid args count";
                }

                Material material = Material.getMaterial(args[CommandArgsLocation.MATERIAL]);
                int count;

                if (material == null) {
                    return invalidItemNameMessage;
                }

                try {
                    count = Integer.parseInt(
                            args[CommandArgsLocation.COUNT]
                    );

                    if (Integer.signum(count) != 1) {
                        throw new Exception();
                    }

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
                        "%s (%d pcs.) successfully removed from dropList",
                        material.name(),
                        count
                );
            }
            default -> {
                return "Invalid action type";
            }
        }
    }

    public @Nullable List<String> onTabComplete(
            @NotNull CommandSender sender,
            @NotNull Command command,
            @NotNull String label,
            @NotNull String[] args
    ) {

        if (args.length == 1) {
            List<Player> players = new ArrayList<>(plugin.getServer().getOnlinePlayers());

            return players.stream()
                    .map(Player::getName)
                    .filter(name ->
                            name.startsWith(args[CommandArgsLocation.NICKNAME])
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
            String itemNameToSearch = args[CommandArgsLocation.MATERIAL];
            boolean isRemoveAction = Objects.equals(
                    args[CommandArgsLocation.ACTION_TYPE],
                    ActionType.REMOVE
            );

            if (isRemoveAction) {
                PlayerMemory pMemory = PlayerUtility.getMemory(
                        (Player) sender,
                        plugin
                );
                Map<Material, Integer> dropList = pMemory.getDropList();


                Material[] materials = dropList.keySet().toArray(new Material[0]);

                return Arrays.stream(materials)
                        .map(Material::name)
                        .filter(materialName ->
                                materialName.startsWith(itemNameToSearch)
                        ).toList();
            }

            Material[] materials = Material.values();

            return Arrays.stream(materials)
                    .map(Material::name)
                    .filter(materialName ->
                            materialName.startsWith(itemNameToSearch)
                    ).toList();
        }

        return new ArrayList<>();
    }


    private static final class CommandArgsLocation {
        public static final Integer NICKNAME = 0;
        public static final Integer ACTION_TYPE = 1;
        public static final Integer MATERIAL = 2;
        public static final Integer COUNT = 3;

        private CommandArgsLocation() {  }
    }


    private static final class ActionType {
        public static final String GET = "get";
        public static final String ADD = "add";
        public static final String REMOVE = "remove";
        public static final String CLEAR = "clear";

        private ActionType() {  }
    }
}
