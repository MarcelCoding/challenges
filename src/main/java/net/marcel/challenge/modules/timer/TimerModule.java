package net.marcel.challenge.modules.timer;

import net.marcel.challenge.modules.Module;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;

public class TimerModule extends Module {

    private final TimerHandler timerHandler;

    public TimerModule(final JavaPlugin plugin) {
        super(plugin, "Timer", Material.CLOCK, "The timer stops the time you need\nfor a challenge or runs the time\nbackwards if you set a time limit.");
        this.timerHandler = new TimerHandler(this.plugin, this);
    }

    @Override
    public void register() {
        this.registerCommand(new TimerCommand(this.timerHandler));
        this.registerEventHandler(new TimerEventHandler(this.timerHandler));
    }

    @Override
    protected void onEnable() {
        this.timerHandler.start();
    }

    @Override
    protected void onDisable() {
    }
}
