package de.gurkenwerfer.dougdougplugplug.filemanager;

import de.gurkenwerfer.dougdougplugplug.DougDougPlugPlug;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.util.Objects;

public class buildModePermConfig {

    private DougDougPlugPlug plugin;
    private static File file;
    private static FileConfiguration buildModePermFile;


    public static void setup() {
        file = new File(Objects.requireNonNull(Bukkit.getServer().getPluginManager().getPlugin("DougDougPlugPlug")).getDataFolder(), "buildModePerms.yml");

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        buildModePermFile = org.bukkit.configuration.file.YamlConfiguration.loadConfiguration(file);
    }

    public static FileConfiguration get() {
        return buildModePermFile;
    }

    public static void save() {
        try {
            buildModePermFile.save(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void reloadBuildModeConfig() {
        buildModePermFile = org.bukkit.configuration.file.YamlConfiguration.loadConfiguration(file);
    }

}
