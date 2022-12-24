package de.gurkenwerfer.dougdougplugplug.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;

public class buildMenuUtils {

    static String perm = "dougdougplugplug.buildmode.allow";

    public static void openAddMenu(Player player){

        ArrayList<Player> list = new ArrayList<>();

        Bukkit.getOnlinePlayers().stream()
                .filter(p -> !(p.hasPermission(perm)))
                .forEach(list::add);

        Inventory addgui = Bukkit.createInventory(player, 45, ChatColor.BLUE + "Add Player List");

        for (Player value : list) {
            ItemStack playerHead = new ItemStack(Material.PLAYER_HEAD, 1);

            SkullMeta skullMeta = (SkullMeta) playerHead.getItemMeta();

            assert skullMeta != null;
            skullMeta.setDisplayName(value.getDisplayName());
            ArrayList<String> lore = new ArrayList<>();
            lore.add(ChatColor.GOLD + "Player Health: " + ChatColor.RED + value.getHealth());
            lore.add(ChatColor.GOLD + "EXP: " + ChatColor.AQUA + value.getExp());
            skullMeta.setOwningPlayer(Bukkit.getOfflinePlayer(value.getUniqueId()));
            skullMeta.setLore(lore);
            playerHead.setItemMeta(skullMeta);

            addgui.addItem(playerHead);
        }
        player.openInventory(addgui);
    }

    public static void openRemoveMenu(Player player){

        ArrayList<Player> list = new ArrayList<>();

        Bukkit.getOnlinePlayers().stream()
                .filter(p -> p.hasPermission(perm))
                .forEach(list::add);

        Inventory removegui = Bukkit.createInventory(player, 45, ChatColor.RED + "Remove Player List");

        for (Player value : list) {
            ItemStack playerHead = new ItemStack(Material.PLAYER_HEAD);
            SkullMeta skullMeta = (SkullMeta) playerHead.getItemMeta();

            assert skullMeta != null;
            skullMeta.setDisplayName(value.getDisplayName());
            ArrayList<String> lore = new ArrayList<>();
            lore.add(ChatColor.GOLD + "Player Health: " + ChatColor.RED + value.getHealth());
            lore.add(ChatColor.GOLD + "EXP: " + ChatColor.AQUA + value.getExp());
            skullMeta.setOwningPlayer(Bukkit.getOfflinePlayer(value.getUniqueId()));
            skullMeta.setLore(lore);
            playerHead.setItemMeta(skullMeta);

            removegui.addItem(playerHead);
        }
        player.openInventory(removegui);
    }

    public static void openPlayerAddMenu(Player player, Player whoToAdd){

        Player addme = whoToAdd;

        Inventory addPlayerMenu = Bukkit.createInventory(player, 9, "Give BuildMode Permissions");

        ItemStack addItem = new ItemStack(Material.EMERALD_BLOCK, 1);
        ItemMeta ban_meta = addItem.getItemMeta();
        assert ban_meta != null;
        ban_meta.setDisplayName(ChatColor.DARK_GREEN + "Add");
        addItem.setItemMeta(ban_meta);
        addPlayerMenu.setItem(0, addItem);

        ItemStack playerHead = new ItemStack(Material.PLAYER_HEAD, 1);
        SkullMeta player_meta = (SkullMeta) playerHead.getItemMeta();
        assert player_meta != null;
        player_meta.setDisplayName(addme.getDisplayName());
        player_meta.setOwningPlayer(Bukkit.getOfflinePlayer(addme.getUniqueId()));
        playerHead.setItemMeta(player_meta);
        addPlayerMenu.setItem(4, playerHead);

        ItemStack cancel = new ItemStack(Material.BARRIER, 1);
        ItemMeta cancel_meta = cancel.getItemMeta();
        assert cancel_meta != null;
        cancel_meta.setDisplayName(ChatColor.RED + "Go Back!");
        cancel.setItemMeta(cancel_meta);
        addPlayerMenu.setItem(8, cancel);

        player.openInventory(addPlayerMenu);
    }

    public static void openPlayerRemoveMenu(Player player, Player whoToRemove){

        Player removeme = whoToRemove;

        Inventory removePlayerMenu = Bukkit.createInventory(player, 9, "Revoke BuildMode Permissions");

        ItemStack removeItem = new ItemStack(Material.REDSTONE_BLOCK, 1);
        ItemMeta ban_meta = removeItem.getItemMeta();
        assert ban_meta != null;
        ban_meta.setDisplayName(ChatColor.DARK_RED + "Remove");
        removeItem.setItemMeta(ban_meta);
        removePlayerMenu.setItem(0, removeItem);

        ItemStack playerHead = new ItemStack(Material.PLAYER_HEAD, 1);
        SkullMeta player_meta = (SkullMeta) playerHead.getItemMeta();
        assert player_meta != null;
        player_meta.setDisplayName(removeme.getDisplayName());
        player_meta.setOwningPlayer(Bukkit.getOfflinePlayer(removeme.getUniqueId()));
        playerHead.setItemMeta(player_meta);
        removePlayerMenu.setItem(4, playerHead);

        ItemStack cancel = new ItemStack(Material.BARRIER, 1);
        ItemMeta cancel_meta = cancel.getItemMeta();
        assert cancel_meta != null;
        cancel_meta.setDisplayName(ChatColor.RED + "Go Back!");
        cancel.setItemMeta(cancel_meta);
        removePlayerMenu.setItem(8, cancel);

        player.openInventory(removePlayerMenu);
    }

}
