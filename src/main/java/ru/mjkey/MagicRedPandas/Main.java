package ru.mjkey.MagicRedPandas;

import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("§d╔════════════════════════════════════╗");
        getLogger().info("§d║   Магия Красных Панд включена!     ║");
        getLogger().info("§d║        Автор: MjKey               ║");
        getLogger().info("§d╚════════════════════════════════════╝");
    }

    @Override
    public void onDisable() {
        getLogger().info("§c╔════════════════════════════════════╗");
        getLogger().info("§c║   Магия Красных Панд выключена!    ║");
        getLogger().info("§c╚════════════════════════════════════╝");
    }
}
