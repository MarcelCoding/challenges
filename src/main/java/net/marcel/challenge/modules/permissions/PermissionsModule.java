package net.marcel.challenge.modules.permissions;

import net.marcel.challenge.modules.Module;
import org.bukkit.plugin.java.JavaPlugin;

public class PermissionsModule extends Module {

    public PermissionsModule(final JavaPlugin plugin) {
        super(plugin, "Permissions", "Controls the Permissions on your Server.");
    }

    @Override
    public void register() {
    }

    @Override
    protected void onEnable() {
    }

    @Override
    protected void onDisable() {
    }
}
