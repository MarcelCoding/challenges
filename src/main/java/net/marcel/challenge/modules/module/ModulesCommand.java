package net.marcel.challenge.modules.module;

import net.marcel.challenge.modules.Module;
import net.marcel.challenge.modules.ModuleCommand;
import net.marcel.challenge.modules.ModuleInventoryBuilder;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Set;

public class ModulesCommand extends ModuleCommand {

    private final Set<Module> modules;

    public ModulesCommand(final Set<Module> modules) {
        super("modules", "Opens a window to enable or disable Modules.");
        this.modules = modules;
    }

    @Override
    protected boolean onCommand0(final CommandSender sender, final String[] args) {
        final Player player = this.checkForPlayer(sender);
        if (player == null) return true;

        player.openInventory(ModuleInventoryBuilder.buildInventory(1, this.modules));
        player.playSound(player.getLocation(), Sound.BLOCK_CHEST_OPEN, 1, 1);

        return true;
    }

    @Override
    protected void onTabComplete0(final List<String> results, final CommandSender sender, final String[] args) {
    }
}
