package net.marcel.challenge.modules.module;

import net.marcel.challenge.modules.Module;
import org.bukkit.plugin.java.JavaPlugin;

public class ModuleModule extends Module {

    public ModuleModule(final JavaPlugin plugin) {
        super(plugin, "Modules", "Configure other Modules.");
    }

    @Override
    public void register() {
        this.registerCommand(new ModulesCommand(this.moduleHandler.getModules()));
        this.registerEventHandler(new ModulesEventHandler(this.moduleHandler.getModules()));
    }

    @Override
    protected void onEnable() {
    }

    @Override
    protected void onDisable() {
    }
}
