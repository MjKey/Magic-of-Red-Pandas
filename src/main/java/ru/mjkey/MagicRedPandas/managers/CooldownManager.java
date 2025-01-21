package ru.mjkey.MagicRedPandas.managers;

import org.bukkit.entity.Player;
import ru.mjkey.MagicRedPandas.Main;

import java.util.HashMap;
import java.util.UUID;

public class CooldownManager {
    private final HashMap<UUID, Long> exchangeCooldowns = new HashMap<>();
    private final HashMap<UUID, Long> infiniteLivesCooldowns = new HashMap<>();
    
    public boolean canExchange(Player player) {
        UUID uuid = player.getUniqueId();
        long now = System.currentTimeMillis();
        
        // Проверяем, есть ли у игрока бесконечные жизни
        if (Main.getInstance().getPlayerManager().hasInfiniteLives(player)) {
            if (!infiniteLivesCooldowns.containsKey(uuid)) {
                return true;
            }
            
            long cooldownTime = infiniteLivesCooldowns.get(uuid);
            long hoursLeft = (cooldownTime - now) / (1000 * 60 * 60);
            
            if (now >= cooldownTime) {
                infiniteLivesCooldowns.remove(uuid);
                return true;
            }
            
            player.sendMessage(formatMessage("cooldown", "§f%time%ч.".formatted(hoursLeft)));
            return false;
        }
        
        // Обычный кулдаун
        if (!exchangeCooldowns.containsKey(uuid)) {
            return true;
        }
        
        long cooldownTime = exchangeCooldowns.get(uuid);
        long minutesLeft = (cooldownTime - now) / (1000 * 60);
        
        if (now >= cooldownTime) {
            exchangeCooldowns.remove(uuid);
            return true;
        }
        
        player.sendMessage(formatMessage("cooldown", "§f%time%мин.".formatted(minutesLeft)));
        return false;
    }
    
    public void setExchangeCooldown(Player player) {
        UUID uuid = player.getUniqueId();
        long cooldownTime;
        
        if (Main.getInstance().getPlayerManager().hasInfiniteLives(player)) {
            cooldownTime = System.currentTimeMillis() + 
                    (Main.getInstance().getConfig().getLong("infinite-lives-cooldown") * 60 * 60 * 1000);
            infiniteLivesCooldowns.put(uuid, cooldownTime);
        } else {
            cooldownTime = System.currentTimeMillis() + 
                    (Main.getInstance().getConfig().getLong("exchange-cooldown") * 60 * 1000);
            exchangeCooldowns.put(uuid, cooldownTime);
        }
    }
    
    private String formatMessage(String path, String time) {
        return Main.getInstance().getConfig().getString("messages." + path, "")
                .replace("%prefix%", Main.getInstance().getConfig().getString("messages.prefix", ""))
                .replace("%time%", time);
    }
} 