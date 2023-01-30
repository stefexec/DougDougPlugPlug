package de.gurkenwerfer.dougdougplugplug.listeners;

import de.gurkenwerfer.dougdougplugplug.DougDougPlugPlug;
import de.gurkenwerfer.dougdougplugplug.filemanager.buildModePermConfig;
import de.gurkenwerfer.dougdougplugplug.utils.buildMenuUtils;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.data.DataMutateResult;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.Node;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

import static org.bukkit.Bukkit.getLogger;

public class buildInventoryListener implements Listener {
    final private DougDougPlugPlug plugin;
    private final LuckPerms luckPerms;

    public buildInventoryListener(DougDougPlugPlug plugin, LuckPerms luckPerms) {
        this.plugin = plugin;
        this.luckPerms = luckPerms;
    }
    String perm = "dougdougplugplug.buildmode.allow";

    @EventHandler
    public void onAddMenuClick(InventoryClickEvent e){
        Player player = (Player) e.getWhoClicked();

        if (e.getView().getTitle().equalsIgnoreCase(ChatColor.BLUE + "Add Player List") && e.getCurrentItem() != null){
            if (Objects.requireNonNull(e.getCurrentItem()).getType() == Material.PLAYER_HEAD){
                Player whoToAdd = DougDougPlugPlug.getPlugin().getServer().getPlayerExact(ChatColor.stripColor(Objects.requireNonNull(e.getCurrentItem().getItemMeta()).getDisplayName().split(" ")[1]));
                buildMenuUtils.openPlayerAddMenu(player, whoToAdd);
            }
        }else if(e.getView().getTitle().equalsIgnoreCase("Give BuildMode Permissions") && e.getCurrentItem() != null){
            switch (Objects.requireNonNull(e.getCurrentItem()).getType()) {
                case BARRIER -> buildMenuUtils.openAddMenu(player);
                case EMERALD_BLOCK -> {
                    String[] name_whole = Objects.requireNonNull(Objects.requireNonNull(Objects.requireNonNull(e.getClickedInventory()).getItem(4)).getItemMeta()).getDisplayName().split(" ");
                    String name = name_whole[1];
                    OfflinePlayer po = this.plugin.getServer().getOfflinePlayer(UUID.fromString(name));
                    if (!po.hasPlayedBefore()) {
                        player.sendMessage(ChatColor.RED + name + " has never joined the server!");
                        return;
                    }
                    Node node = Node.builder(perm).build();
                    luckPerms.getUserManager().modifyUser(po.getUniqueId(), (User user) -> {

                        DataMutateResult result = user.data().add(node);

                        if (result.wasSuccessful()) {
                            player.sendMessage("\u00A77Permission \u00A7a" + perm + "\u00A77 has been added!");
                        } else {
                            player.sendMessage(ChatColor.GOLD + user.getUsername() + " already has that permission!");
                        }
                    });
                    player.sendMessage(ChatColor.GREEN + "Gave " + po.getName() + " Build Mode permission!");

                    ArrayList<String> defaultPerms = new ArrayList<>();
                    defaultPerms.add("cmi.command");
                    defaultPerms.add("cmi.command.gm");
                    defaultPerms.add("cmi.command.creative");
                    defaultPerms.add("cmi.command.survival");
                    defaultPerms.add("cmi.command.gm.creative");
                    defaultPerms.add("cmi.command.gm.survival");
                    defaultPerms.add("cmi.command.fly");
                    defaultPerms.add("cmi.command.allowflight");

                    buildModePermConfig.get().set(po.getName(), defaultPerms);
                    buildModePermConfig.save();

                }
            }
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onRemoveMenuClick(InventoryClickEvent e){
        Player player = (Player) e.getWhoClicked();

        if (e.getView().getTitle().equalsIgnoreCase(ChatColor.RED + "Remove Player List") && e.getCurrentItem() != null){

            if (Objects.requireNonNull(e.getCurrentItem()).getType() == Material.PLAYER_HEAD){

                Player whoToRemove = DougDougPlugPlug.getPlugin().getServer().getPlayerExact(ChatColor.stripColor(Objects.requireNonNull(e.getCurrentItem().getItemMeta()).getDisplayName().split(" ")[1]));

                buildMenuUtils.openPlayerRemoveMenu(player, whoToRemove);
            }

        }else if(e.getView().getTitle().equalsIgnoreCase("Revoke BuildMode Permissions") && e.getCurrentItem() != null){
            switch (Objects.requireNonNull(e.getCurrentItem()).getType()) {
                case BARRIER -> buildMenuUtils.openRemoveMenu(player);
                case REDSTONE_BLOCK -> {
                    String[] name_whole = Objects.requireNonNull(Objects.requireNonNull(Objects.requireNonNull(e.getClickedInventory()).getItem(4)).getItemMeta()).getDisplayName().split(" ");
                    String name = name_whole[1];
                    getLogger().info(name_whole + " " + name);
                    OfflinePlayer po = this.plugin.getServer().getOfflinePlayer(player.getUniqueId());
                    if (!po.hasPlayedBefore()) {
                        player.sendMessage(ChatColor.RED + name + " has never joined the server!");
                        return;
                    }
                    Node node = Node.builder(perm).build();
                    luckPerms.getUserManager().modifyUser(po.getUniqueId(), (User user) -> {

                        DataMutateResult result = user.data().remove(node);

                        if (result.wasSuccessful()) {
                            player.sendMessage("\u00A77Permission \u00A76" + perm + "\u00A77 has been removed!");
                        } else {
                            player.sendMessage(ChatColor.GOLD + user.getUsername() + " never had that permission!");
                        }
                    });
                    player.performCommand("cmi gm 0 " + po.getName());
                    player.sendMessage(ChatColor.RED + "Revoked " + po.getName() + "'s Build Mode access!");
                }
            }
            e.setCancelled(true);
        }
    }
}
