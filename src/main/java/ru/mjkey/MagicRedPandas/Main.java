package ru.mjkey.MagicRedPandas;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import ru.mjkey.MagicRedPandas.managers.PlayerManager;
import ru.mjkey.MagicRedPandas.managers.LocationManager;
import ru.mjkey.MagicRedPandas.commands.TakeLifePosCommand;
import ru.mjkey.MagicRedPandas.commands.GiveLifePosCommand;
import ru.mjkey.MagicRedPandas.managers.CooldownManager;

public final class Main extends JavaPlugin {
    
    private static Main instance;
    private PlayerManager playerManager;
    private LocationManager locationManager;
    private CooldownManager cooldownManager;

    @Override
    public void onEnable() {
        instance = this;
        
        // Сохраняем конфиг по умолчанию
        saveDefaultConfig();
        
        playerManager = new PlayerManager();
        locationManager = new LocationManager();
        cooldownManager = new CooldownManager();
        
        // Регистрация команд
        getCommand("takelifepos").setExecutor(new TakeLifePosCommand());
        getCommand("givelifepos").setExecutor(new GiveLifePosCommand());
        
        // Запуск проверки позиций каждую секунду
        Bukkit.getScheduler().runTaskTimer(this, this::checkPositions, 20L, 20L);

        getLogger().info("╔════════════════════════════════════╗");
        getLogger().info("║   Магия Красных Панд включена!     ║");
        getLogger().info("║         Автор: MjKey               ║");
        getLogger().info("╚════════════════════════════════════╝");
    }

    private void checkPositions() {
        Player takingPlayer = null;
        Player givingPlayer = null;

        for (Player player : Bukkit.getOnlinePlayers()) {
            if (locationManager.isPlayerAtTakeLifePos(player)) {
                takingPlayer = player;
            } else if (locationManager.isPlayerAtGiveLifePos(player)) {
                givingPlayer = player;
            }
        }

        if (takingPlayer != null && givingPlayer != null) {
            // Проверяем кулдауны обоих игроков
            if (!cooldownManager.canExchange(takingPlayer) || !cooldownManager.canExchange(givingPlayer)) {
                return;
            }
            
            playerManager.removeLife(givingPlayer);
            playerManager.addLife(takingPlayer, 1);
            
            // Устанавливаем кулдауны
            cooldownManager.setExchangeCooldown(takingPlayer);
            cooldownManager.setExchangeCooldown(givingPlayer);
            
            // Отправляем сообщения
            String takingMessage = getConfig().getString("messages.exchange-success-take", "")
                    .replace("%prefix%", getConfig().getString("messages.prefix", ""))
                    .replace("%player%", givingPlayer.getName());
            
            String givingMessage = getConfig().getString("messages.exchange-success-give", "")
                    .replace("%prefix%", getConfig().getString("messages.prefix", ""))
                    .replace("%player%", takingPlayer.getName());
            
            takingPlayer.sendMessage(takingMessage);
            givingPlayer.sendMessage(givingMessage);
        }
    }

    @Override
    public void onDisable() {
        getLogger().info("╔════════════════════════════════════╗");
        getLogger().info("║   Магия Красных Панд выключена!    ║");
        getLogger().info("╚════════════════════════════════════╝");
    }
    
    public static Main getInstance() {
        return instance;
    }
    
    public PlayerManager getPlayerManager() {
        return playerManager;
    }
    
    public LocationManager getLocationManager() {
        return locationManager;
    }
    
    public CooldownManager getCooldownManager() {
        return cooldownManager;
    }
}
