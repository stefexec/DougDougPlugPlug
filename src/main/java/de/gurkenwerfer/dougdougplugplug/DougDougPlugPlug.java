package de.gurkenwerfer.dougdougplugplug;

import de.gurkenwerfer.dougdougplugplug.commands.buildModeCommand;
import de.gurkenwerfer.dougdougplugplug.listeners.buildInventoryListener;
import de.gurkenwerfer.dougdougplugplug.listeners.buildModeListener;
import de.gurkenwerfer.dougdougplugplug.utils.buildModeCommandTabCompleter;
import net.luckperms.api.LuckPerms;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Objects;

public final class DougDougPlugPlug extends JavaPlugin {
    public static DougDougPlugPlug getPlugin() {
        return plugin;
    }
    private static DougDougPlugPlug plugin;
    private LuckPerms luckPerms;

    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info("\u00A73DougDougPlugPlug has been enabled!");

        plugin = this;
        luckPerms = getServer().getServicesManager().load(LuckPerms.class);

        ArrayList<String> perms = new ArrayList<>();
        perms.add("cmi.command");
        perms.add("cmi.command.gm");
        perms.add("cmi.command.creative");
        perms.add("cmi.command.survival");
        perms.add("cmi.command.gm.creative");
        perms.add("cmi.command.gm.survival");
        perms.add("cmi.command.fly");
        perms.add("cmi.command.allowflight");
        perms.add("cmi.command.afk.kickbypass");

        Objects.requireNonNull(getCommand("buildmode")).setExecutor(new buildModeCommand(this, this.luckPerms));
        getServer().getPluginManager().registerEvents(new buildModeListener(), this);
        getServer().getPluginManager().registerEvents(new buildInventoryListener(this, this.luckPerms), this);

        buildModeCommandTabCompleter tc = new buildModeCommandTabCompleter();
        Objects.requireNonNull(this.getCommand("buildmode")).setTabCompleter(tc);

        // Setup default config file
        getConfig().options().copyDefaults();
        saveDefaultConfig();

        // Setup config file for buildmode
        de.gurkenwerfer.dougdougplugplug.filemanager.buildModePermConfig.setup();
        de.gurkenwerfer.dougdougplugplug.filemanager.buildModePermConfig.get().addDefault("Gurkenwerfer_", perms);
        de.gurkenwerfer.dougdougplugplug.filemanager.buildModePermConfig.get().options().copyDefaults(true);
        de.gurkenwerfer.dougdougplugplug.filemanager.buildModePermConfig.save();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("\u00A75DougDougPlugPlug has been disabled!");
    }
}
