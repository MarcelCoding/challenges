package net.marcel.challenge.modules.invsee;

import net.marcel.challenge.modules.Module;
import org.bukkit.plugin.java.JavaPlugin;

public class InvseeModule extends Module {

    public InvseeModule(final JavaPlugin plugin) {
        super(plugin, "invsee", "A way to see other players inventories.");
    }

    @Override
    public void register() {
        this.registerCommand(new InvseeCommand());
        this.registerEventHandler(new InvseeEventHandler());
    }

    @Override
    protected void onEnable() {

    }

    @Override
    protected void onDisable() {

    }
}
