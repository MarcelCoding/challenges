package net.marcel.challenge.modules.timer;

import net.marcel.challenge.modules.Module;
import net.marcel.challenge.modules.ModuleData;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;

public class TimerModule extends Module {

    private final ModuleData moduleData;
    private final TimerHandler timerHandler;

    public TimerModule(final JavaPlugin plugin) {
        super(plugin, "Timer", Material.CLOCK, "The timer stops the time you need\nfor a challenge or runs the time\nbackwards if you set a time limit.");

        this.moduleData = new ModuleData(this);
        this.timerHandler = new TimerHandler(this, this.moduleData);
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
        if (this.timerHandler.isRunning()) this.timerHandler.pause();
        this.timerHandler.save();
        this.moduleData.save();
    }
}
