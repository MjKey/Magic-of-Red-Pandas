package ru.mjkey.MagicRedPandas.managers;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import ru.mjkey.MagicRedPandas.Main;

import java.io.File;
import java.io.IOException;

public class LocationManager {
    private Location takeLifePos;
    private Location giveLifePos;
    private final File configFile;
    private FileConfiguration config;
    
    public LocationManager() {
        configFile = new File(Main.getInstance().getDataFolder(), "locations.yml");
        loadLocations();
    }
    
    private void loadLocations() {
        if (!configFile.exists()) {
            Main.getInstance().saveResource("locations.yml", false);
        }
        config = YamlConfiguration.loadConfiguration(configFile);
        
        if (config.contains("locations.take-life")) {
            takeLifePos = config.getLocation("locations.take-life");
        }
        if (config.contains("locations.give-life")) {
            giveLifePos = config.getLocation("locations.give-life");
        }
    }
    
    private void saveLocations() {
        config.set("locations.take-life", takeLifePos);
        config.set("locations.give-life", giveLifePos);
        try {
            config.save(configFile);
        } catch (IOException e) {
            Main.getInstance().getLogger().warning("Не удалось сохранить локации: " + e.getMessage());
        }
    }
    
    public void setTakeLifePos(Location location) {
        this.takeLifePos = location;
        saveLocations();
    }
    
    public void setGiveLifePos(Location location) {
        this.giveLifePos = location;
        saveLocations();
    }
    
    public boolean isPlayerAtTakeLifePos(Player player) {
        if (takeLifePos == null) return false;
        Location playerLoc = player.getLocation();
        return playerLoc.getWorld().equals(takeLifePos.getWorld()) &&
               playerLoc.distance(takeLifePos) <= 1.5;
    }
    
    public boolean isPlayerAtGiveLifePos(Player player) {
        if (giveLifePos == null) return false;
        Location playerLoc = player.getLocation();
        return playerLoc.getWorld().equals(giveLifePos.getWorld()) &&
               playerLoc.distance(giveLifePos) <= 1.5;
    }
} 