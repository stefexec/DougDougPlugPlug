package de.gurkenwerfer.dougdougplugplug.listeners;

import de.gurkenwerfer.dougdougplugplug.DougDougPlugPlug;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.persistence.PersistentDataType;

public class buildModeListener implements Listener {

    @EventHandler
    public void onPLayerJoin(PlayerJoinEvent e) {

        Player p = e.getPlayer();

        if (!p.getPersistentDataContainer().has(new NamespacedKey(DougDougPlugPlug.getPlugin(), "inBuildMode"), PersistentDataType.STRING)) {
            p.getPersistentDataContainer().set(new NamespacedKey(DougDougPlugPlug.getPlugin(), "inBuildMode"), PersistentDataType.STRING, "false");

        }
    }
}
