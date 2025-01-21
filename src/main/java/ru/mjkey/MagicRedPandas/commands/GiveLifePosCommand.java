package ru.mjkey.MagicRedPandas.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import ru.mjkey.MagicRedPandas.Main;

public class GiveLifePosCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(formatMessage("player-only"));
            return true;
        }

        if (!player.hasPermission("magicredpandas.setpos")) {
            sender.sendMessage(formatMessage("no-permission"));
            return true;
        }

        Main.getInstance().getLocationManager().setGiveLifePos(player.getLocation());
        player.sendMessage(formatMessage("position-set").replace("%type%", "передачи жизни"));
        return true;
    }
    
    private String formatMessage(String path) {
        return Main.getInstance().getConfig().getString("messages." + path, "")
                .replace("%prefix%", Main.getInstance().getConfig().getString("messages.prefix", ""));
    }
} 