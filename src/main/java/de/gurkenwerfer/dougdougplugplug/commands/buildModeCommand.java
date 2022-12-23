package de.gurkenwerfer.dougdougplugplug.commands;

import de.gurkenwerfer.dougdougplugplug.DougDougPlugPlug;
import de.gurkenwerfer.dougdougplugplug.filemanager.buildModePermConfig;
import de.gurkenwerfer.dougdougplugplug.utils.buildMenuUtils;
import de.gurkenwerfer.dougdougplugplug.utils.discordWebhookUtil;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.data.DataMutateResult;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.Node;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class buildModeCommand implements CommandExecutor {
    private final LuckPerms luckPerms;
    private final DougDougPlugPlug plugin;
    public buildModeCommand(DougDougPlugPlug plugin, LuckPerms luckPerms) {
        this.plugin = plugin;
        this.luckPerms = luckPerms;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player p = sender.getServer().getPlayer(sender.getName());

        if (sender instanceof Player) {
            PersistentDataContainer isInBuildMode = ((Player) sender).getPersistentDataContainer();
            String isInBuildModeString = isInBuildMode.get(new NamespacedKey(DougDougPlugPlug.getPlugin(), "inBuildMode"), PersistentDataType.STRING);

            assert p != null;
            if (p.hasPermission("dougdougplugplug.buildmode.allow") || p.hasPermission("dougdougplugplug.buildmode.admin") || p.isOp()) {

                if (args.length == 0) {
                    assert isInBuildModeString != null;
                    if (isInBuildModeString.equals("false")) {
                        isInBuildMode.set(new NamespacedKey(DougDougPlugPlug.getPlugin(), "inBuildMode"), PersistentDataType.STRING, "true");
                        p.sendMessage("\u00A7aBuildmode has been enabled!");
                        discordWebhookUtil.sendUserWarning("Info", p.getName() + " has enabled Build Mode!", 16727808, p.getUniqueId());

                        for (String perm : buildModePermConfig.get().getStringList(p.getName())) {

                            OfflinePlayer player = this.plugin.getServer().getOfflinePlayer(p.getUniqueId());

                            if (!player.hasPlayedBefore()) {
                                sender.sendMessage(ChatColor.RED + p.getName() + " has never joined the server!");
                                return true;
                            }
                            Node node = Node.builder(perm).build();

                            try {
                                Thread.sleep(200);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }

                            luckPerms.getUserManager().modifyUser(player.getUniqueId(), (User user) -> {
                                DataMutateResult result = user.data().add(node);

                                if (result.wasSuccessful()) {
                                    sender.sendMessage(user.getUsername() + " \u00A77now has permission \u00A7a" + perm + "!");
                                } else {
                                    sender.sendMessage(ChatColor.GOLD + user.getUsername() + " already has that permission!");
                                }
                            });
                        }
                    } else {
                        isInBuildMode.set(new NamespacedKey(DougDougPlugPlug.getPlugin(), "inBuildMode"), PersistentDataType.STRING, "false");
                        p.sendMessage("\u00A7cBuildmode has been disabled!");
                        discordWebhookUtil.sendUserWarning("Info", p.getName() + " has disabled Build Mode!", 18175, p.getUniqueId());

                        for (String perm : buildModePermConfig.get().getStringList(p.getName())) {

                            OfflinePlayer player = this.plugin.getServer().getOfflinePlayer(p.getUniqueId());

                            if (!player.hasPlayedBefore()) {
                                sender.sendMessage(ChatColor.RED + p.getName() + " has never joined the server!");
                                return true;
                            }

                            Node node = Node.builder(perm).build();

                            try {
                                Thread.sleep(200);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }

                            luckPerms.getUserManager().modifyUser(player.getUniqueId(), (User user) -> {

                                DataMutateResult result = user.data().remove(node);

                                if (result.wasSuccessful()) {
                                    sender.sendMessage("\u00A77Permission \u00A76" + perm + "\u00A77 has been removed!");
                                } else {
                                    sender.sendMessage(ChatColor.GOLD + user.getUsername() + " never had that permission!");
                                }
                            });
                        }
                    }
                } else if (args.length == 1) {
                    if (args[0].equalsIgnoreCase("help")) {
                        p.sendMessage("This command grants you whatever permissions Roach allows you to have.");
                    } else if (args[0].equalsIgnoreCase("reload")) {
                        p.sendMessage(ChatColor.DARK_RED + "BuildMode plugin reloaded");
                        buildModePermConfig.reloadBuildModeConfig();
                    } else if (args[0].equalsIgnoreCase("add") && p.hasPermission("dougdougplugplug.buildmode.admin")) {
                        buildMenuUtils.openAddMenu(p);
                    } else if (args[0].equalsIgnoreCase("remove") && p.hasPermission("dougdougplugplug.buildmode.admin")) {
                        buildMenuUtils.openRemoveMenu(p);
                    } else {
                        p.sendMessage(ChatColor.RED + "These aren't the Droids you're looking for...");
                    }
                } else {
                    p.sendMessage("\u00A7cPlease use \u00A77/buildmode");
                }
            } else {
                p.sendMessage("\u00A7cYou do not have permission to use this command!");
            }
        } else {
            sender.sendMessage("\u00A7cError! Not a player!");
        }
        return true;
    }
}
