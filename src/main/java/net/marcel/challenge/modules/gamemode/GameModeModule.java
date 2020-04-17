package net.marcel.challenge.modules.gamemode;

import lombok.EqualsAndHashCode;
import net.marcel.challenge.modules.Module;
import org.bukkit.plugin.java.JavaPlugin;

@EqualsAndHashCode
public class GameModeModule extends Module {

    public GameModeModule(final JavaPlugin plugin) {
        super(plugin, "GameMode", "Controls your GameMode.");
    }

    @Override
    public void register() {
        this.registerCommand(new GameModeCommand());
    }

    @Override
    protected void onEnable() {
    }

    @Override
    protected void onDisable() {
    }
}
