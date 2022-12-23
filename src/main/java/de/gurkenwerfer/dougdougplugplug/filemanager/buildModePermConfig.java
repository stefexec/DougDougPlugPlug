package de.gurkenwerfer.dougdougplugplug.filemanager;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import java.io.File;
import java.util.Objects;

public class buildModePermConfig {
    private static File file;
    public static File config;
    private static FileConfiguration buildModePermFile;
    private static FileConfiguration configFile;

    public static void setup() {
        file = new File(Objects.requireNonNull(Bukkit.getServer().getPluginManager().getPlugin("DougDougPlugPlug")).getDataFolder(), "buildModePerms.yml");
        config = new File(Objects.requireNonNull(Bukkit.getServer().getPluginManager().getPlugin("DougDougPlugPlug")).getDataFolder(), "config.yml");

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        buildModePermFile = org.bukkit.configuration.file.YamlConfiguration.loadConfiguration(file);
        configFile = org.bukkit.configuration.file.YamlConfiguration.loadConfiguration(config);
    }

    public static FileConfiguration get() {
        return buildModePermFile;
    }

    public static FileConfiguration getConfigFile() {
        return configFile;
    }

    public static void save() {
        try {
            buildModePermFile.save(file);
            configFile.save(config);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void reloadBuildModeConfig() {
        buildModePermFile = org.bukkit.configuration.file.YamlConfiguration.loadConfiguration(file);
        configFile = org.bukkit.configuration.file.YamlConfiguration.loadConfiguration(config);
    }
}
