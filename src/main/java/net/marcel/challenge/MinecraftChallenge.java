package net.marcel.challenge;

import lombok.Getter;
import net.marcel.challenge.data.Data;
import net.marcel.challenge.modules.Module;
import net.marcel.challenge.modules.ModuleHandler;
import net.marcel.challenge.modules.chat.ChatModule;
import net.marcel.challenge.modules.gamemode.GameModeModule;
import net.marcel.challenge.modules.invsee.InvseeModule;
import net.marcel.challenge.modules.module.ModuleModule;
import net.marcel.challenge.modules.scoreboard.ScoreboardModule;
import net.marcel.challenge.modules.teams.TeamsModule;
import net.marcel.challenge.modules.timer.TimerModule;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MinecraftChallenge extends JavaPlugin {

    private final Logger logger;
    @Getter
    private final ModuleHandler moduleHandler;

    private Data data;

    public MinecraftChallenge() {
        this.logger = this.getLogger();
        this.moduleHandler = new ModuleHandler(this.logger, this.getServer());


        try {
            this.data = new Data(this.getDataFolder());
        } catch (IOException | InvalidConfigurationException e) {
            this.logger.log(Level.SEVERE, "Unable to load Data System.", e);
        }
    }

    @Override
    public void onEnable() {
        super.onEnable();

        this.moduleHandler.addModule(new ModuleModule(this));
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
        for (final Module module : this.moduleHandler.getModules()) {
            module.onDisable0();
        }

        try {
            this.data.save();
        } catch (IOException e) {
            this.logger.log(Level.SEVERE, "Unable to save Data System.", e);
        }
    }
}
