package net.marcel.challenge.modules.invsee;

import net.marcel.challenge.Permission;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;

public class InvseeEventHandler implements Listener {

    @EventHandler
    public void onClick(final InventoryOpenEvent event) {
        if (!ChatColor.stripColor(event.getView().getTitle()).startsWith("Inventory from ")) return;

        if (event.getPlayer() instanceof Player) {
            final Player player = (Player) event.getPlayer();
            if (!player.hasPermission(Permission.INVSEE_COMMAND.toString())) return;
            player.playSound(player.getLocation(), Sound.BLOCK_CHEST_OPEN, 1, 1);
        }
    }

    @EventHandler
    public void onClick(final InventoryCloseEvent event) {
        if (!ChatColor.stripColor(event.getView().getTitle()).startsWith("Inventory from ")) return;

        if (event.getPlayer() instanceof Player) {
            final Player player = (Player) event.getPlayer();
            if (!player.hasPermission(Permission.INVSEE_COMMAND.toString())) return;
            player.playSound(player.getLocation(), Sound.BLOCK_CHEST_CLOSE, 1, 1);
        }
    }
}
