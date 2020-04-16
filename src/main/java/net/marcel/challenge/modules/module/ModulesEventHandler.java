package net.marcel.challenge.modules.module;

import lombok.RequiredArgsConstructor;
import net.marcel.challenge.Permission;
import net.marcel.challenge.modules.Module;
import net.marcel.challenge.modules.ModuleInventoryBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class ModulesEventHandler implements Listener {

    private final Set<Module> modules;

//    @EventHandler
//    public void onClose(final InventoryCloseEvent event) {
//        if (!ChatColor.stripColor(event.getView().getTitle()).startsWith("Modules [Page ")) return;
//
//        if (event.getPlayer() instanceof Player) {
//            final Player player = (Player) event.getPlayer();
//            if (!player.hasPermission(Permission.MODULES.toString())) return;
//            player.playSound(player.getLocation(), Sound.BLOCK_CHEST_CLOSE, 1, 1);
//        }
//    }

    @EventHandler
    public void onClick(final InventoryClickEvent event) {
        if (!ChatColor.stripColor(event.getView().getTitle()).startsWith("Modules [Page ")) return;
        event.setCancelled(true);

        if (event.getWhoClicked() instanceof Player) {
            final Player player = (Player) event.getWhoClicked();
            if (!player.hasPermission(Permission.MODULES.toString())) return;
            final String moduleName = ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName());

            if (moduleName.isBlank()) return;

            if (moduleName.equals("Previous Page")) {
                event.getWhoClicked().openInventory(ModuleInventoryBuilder.buildInventory(-1, this.modules));
                player.playSound(player.getLocation(), Sound.BLOCK_CHEST_OPEN, 1, 1);
            } else if (moduleName.equals("Next Page")) {
                event.getWhoClicked().openInventory(ModuleInventoryBuilder.buildInventory(2, this.modules));
                player.playSound(player.getLocation(), Sound.BLOCK_CHEST_OPEN, 1, 1);
            } else {
                final List<Module> filteredModules = this.modules.stream().filter(currentModule -> currentModule.getName().equals(moduleName)).collect(Collectors.toList());
                if (filteredModules.isEmpty()) return;
                final Module module = filteredModules.get(0);
                module.setEnabled(!module.isEnabled());
                event.getWhoClicked().openInventory(ModuleInventoryBuilder.buildInventory(1, this.modules));
            }
        }
    }
}
