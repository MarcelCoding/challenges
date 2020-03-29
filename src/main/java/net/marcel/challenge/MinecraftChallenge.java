package net.marcel.challenge;

import net.marcel.challenge.data.Data;
import net.marcel.challenge.timer.TimerCommand;
import net.marcel.challenge.timer.TimerHandler;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public class MinecraftChallenge extends JavaPlugin {

    private final TimerHandler timerHandler;
    private Data data;

    public MinecraftChallenge() {
        try {
            this.data = new Data(this.getDataFolder());
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }

        this.timerHandler = new TimerHandler(this, this.data);
    }

    @Override
    public void onEnable() {
        super.onEnable();

        this.timerHandler.start();

        this.getCommand("timer").setExecutor(new TimerCommand(this.timerHandler));
    }

    @Override
    public void onDisable() {
        super.onDisable();
        this.timerHandler.save();
        try {
            this.data.save();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
