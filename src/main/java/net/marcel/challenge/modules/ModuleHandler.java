package net.marcel.challenge.modules;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.PluginManager;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ModuleHandler {

    private final Logger logger;
    private final Server server;
    private final PluginManager pluginManager;
    @Getter
    private final Set<Module> modules;

    public ModuleHandler(final Logger logger, final Server server) {
        this.logger = logger;
        this.server = server;
        this.pluginManager = Bukkit.getPluginManager();
        this.modules = new HashSet<>();
    }

    public void addModule(final Module module) {
        this.modules.add(module);
        this.bootstrapModule(module);
    }

    private void bootstrapModule(final Module module) {
        module.register();
        module.getCommands().forEach(this::registerCommand);
        module.getEventHandlers().forEach(eventHandler -> this.pluginManager.registerEvents(eventHandler, module.getPlugin()));
    }

    private void registerCommand(final ModuleCommand command) {
        final PluginCommand pluginCommand = this.server.getPluginCommand(command.getName());
        if (pluginCommand == null) {
            this.logger.log(Level.SEVERE, "Command \"" + command.getName() + "\" is not the the \"plugin.yml\" registered.");
        } else command.register(pluginCommand);
    }
}
