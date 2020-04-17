package net.marcel.challenge;

import lombok.Getter;
import net.marcel.challenge.modules.Module;
import net.marcel.challenge.modules.ModuleHandler;
import net.marcel.challenge.modules.chat.ChatModule;
import net.marcel.challenge.modules.gamemode.GameModeModule;
import net.marcel.challenge.modules.invsee.InvseeModule;
import net.marcel.challenge.modules.module.ModuleModule;
import net.marcel.challenge.modules.scoreboard.ScoreboardModule;
import net.marcel.challenge.modules.teams.TeamsModule;
import net.marcel.challenge.modules.timer.TimerModule;
import org.bukkit.plugin.java.JavaPlugin;

public class Challenges extends JavaPlugin {

    @Getter
    private final ModuleHandler moduleHandler;

    public Challenges() {
        this.moduleHandler = new ModuleHandler(this.getLogger(), this.getServer());
    }

    @Override
    public void onEnable() {
        super.onEnable();

        this.moduleHandler.addModule(new ModuleModule(this));
        this.moduleHandler.loadState();

        /* Init Modules */
        this.moduleHandler.addModule(new TimerModule(this));
        this.moduleHandler.addModule(new GameModeModule(this));
        this.moduleHandler.addModule(new ChatModule(this));
        this.moduleHandler.addModule(new InvseeModule(this));
        this.moduleHandler.addModule(new TeamsModule(this));
        this.moduleHandler.addModule(new ScoreboardModule(this));
    }

    @Override
    public void onDisable() {
        super.onDisable();

        this.moduleHandler.saveState();
        for (final Module module : this.moduleHandler.getModules()) {
            module.onDisable0();
        }
    }
}
