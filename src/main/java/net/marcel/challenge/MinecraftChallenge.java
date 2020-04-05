package net.marcel.challenge;

import net.marcel.challenge.commands.ChallengeCommand;
import net.marcel.challenge.commands.SpectatorCommand;
import net.marcel.challenge.commands.TimerCommand;
import net.marcel.challenge.data.Data;
import net.marcel.challenge.feature.DistanceCommand;
import net.marcel.challenge.feature.GameModeCommand;
import net.marcel.challenge.feature.InvseeCommand;
import net.marcel.challenge.feature.SetHpCommand;
import net.marcel.challenge.handler.SpectatorHandler;
import net.marcel.challenge.handler.TimerHandler;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class MinecraftChallenge extends JavaPlugin {

    private final Set<ChallengeCommand> commands;

    private final TimerHandler timerHandler;
    private final SpectatorHandler spectatorHandler;
    private Data data;

    public MinecraftChallenge() {
        this.commands = new HashSet<>();

        try {
            this.data = new Data(this.getDataFolder());
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }

        this.spectatorHandler = new SpectatorHandler(this.data);
        this.timerHandler = new TimerHandler(this, this.data);

        this.registerCommands();
    }

    private void registerCommands() {
        this.commands.add(new TimerCommand(this.timerHandler));
        this.commands.add(new DistanceCommand(this.spectatorHandler));
        this.commands.add(new SpectatorCommand(this.spectatorHandler));
        this.commands.add(new InvseeCommand());
        this.commands.add(new GameModeCommand());
        this.commands.add(new SetHpCommand());
    }

    @Override
    public void onEnable() {
        super.onEnable();

        this.timerHandler.start();

        this.commands.forEach(command -> command.register(this.getCommand(command.getName())));
    }

    @Override
    public void onDisable() {
        super.onDisable();
        this.timerHandler.save();
        this.spectatorHandler.save();
        try {
            this.data.save();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
