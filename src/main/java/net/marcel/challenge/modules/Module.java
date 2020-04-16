package net.marcel.challenge.modules;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import net.marcel.challenge.Color;
import net.marcel.challenge.Message;
import net.marcel.challenge.MinecraftChallenge;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode
public abstract class Module {

    protected final ModuleHandler moduleHandler;

    @Getter
    protected final JavaPlugin plugin;
    @Getter
    private final String name;
    @Getter
    private final Material symbol;
    @Getter
    private final String description;
    @Getter
    private final boolean hide;

    @Getter
    private final Set<ModuleCommand> commands;
    @Getter
    private final Set<Listener> eventHandlers;
    private final Set<BukkitTask> tasks;

    @Getter
    private boolean enabled;

    public Module(final JavaPlugin plugin, final String name, final String description) {
        this(plugin, name, null, description);
    }

    public Module(final JavaPlugin plugin, final String name, final Material symbol, final String description) {
        this.moduleHandler = JavaPlugin.getPlugin(MinecraftChallenge.class).getModuleHandler();

        this.plugin = plugin;
        this.name = name;
        this.symbol = symbol;
        this.description = description;
        this.hide = this.symbol == null;

        this.commands = new HashSet<>();
        this.eventHandlers = new HashSet<>();
        this.tasks = new HashSet<>();

        this.enabled = this.hide;
    }

    public abstract void register();

    protected abstract void onEnable();

    protected abstract void onDisable();

    public void onDisable0() {
        this.tasks.forEach(task -> {
            if (!task.isCancelled()) task.cancel();
        });
        this.onDisable();
    }

    protected void registerCommand(final ModuleCommand command) {
        command.setModule(this);
        this.commands.add(command);
    }

    protected void registerEventHandler(final Listener eventHandler) {
        this.eventHandlers.add(eventHandler);
    }

    public void registerTask(final BukkitTask task) {
        this.tasks.add(task);
    }

    public void setEnabled(final boolean enabled) {
        if (this.isEnabled()) {
            Bukkit.getOnlinePlayers().forEach(player -> player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, 1, 0));
            this.onDisable0();
        } else {
            Bukkit.getOnlinePlayers().forEach(player -> player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, 1, 2));
            this.onEnable();
        }
        this.enabled = enabled;
        Bukkit.broadcastMessage(Message.PREFIX + "The Module " + Color.PRIMARY + this.getName() + Color.SECONDARY + " is now " + (this.isEnabled() ? ChatColor.GREEN + "enabled" : ChatColor.RED + "disabled") + Color.SECONDARY + ".");
    }
}
