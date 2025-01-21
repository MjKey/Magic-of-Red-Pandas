package ru.mjkey.MagicRedPandas.managers;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PlayerManager {
    
    public void addLife(Player target, int amount) {
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "7life " + target.getName() + " add " + amount);
    }
    
    public void removeLife(Player target) {
        String livesStr = PlaceholderAPI.setPlaceholders(target, "%7life_lives%");
        if (livesStr.equals("∞")) return;
        
        try {
            int currentLives = Integer.parseInt(livesStr);
            int newLives = Math.max(0, currentLives - 1);
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "7life " + target.getName() + " set " + newLives);
        } catch (NumberFormatException e) {
            Bukkit.getLogger().warning("Не удалось получить количество жизней игрока " + target.getName());
        }
    }
    
    public boolean hasInfiniteLives(Player player) {
        String lives = PlaceholderAPI.setPlaceholders(player, "%7life_lives%");
        return lives.equals("∞");
    }
    
    public void addMagic(Player target, int amount) {
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "rep-admin give " + target.getName() + " " + amount);
    }
    
    public void removeMagic(Player target, int amount) {
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "rep-admin take " + target.getName() + " " + amount);
    }
} 