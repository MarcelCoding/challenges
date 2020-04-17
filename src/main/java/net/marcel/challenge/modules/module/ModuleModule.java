package net.marcel.challenge.modules.module;

import lombok.EqualsAndHashCode;
import net.marcel.challenge.modules.Module;
import net.marcel.challenge.modules.ModuleData;
import org.bukkit.plugin.java.JavaPlugin;

@EqualsAndHashCode
public class ModuleModule extends Module {

    public ModuleModule(final JavaPlugin plugin) {
        super(plugin, "Modules", "Configure other Modules.");

        this.moduleHandler.setData(new ModuleData(this));
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
