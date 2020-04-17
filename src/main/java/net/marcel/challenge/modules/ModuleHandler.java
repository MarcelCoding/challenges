package net.marcel.challenge.modules;

import com.google.gson.JsonArray;
import lombok.Getter;
import lombok.Setter;
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
    private final Set<String> enabeld;
    @Setter
    private ModuleData data;

    public ModuleHandler(final Logger logger, final Server server) {
        this.logger = logger;
        this.server = server;
        this.pluginManager = Bukkit.getPluginManager();
        this.modules = new HashSet<>();
        this.enabeld = new HashSet<>();
    }

    public void addModule(final Module module) {
        this.modules.add(module);
        this.bootstrapModule(module);
    }

    public <M> M getModule(final Class<M> moduleClass) {
        for (final Module module : this.modules) {
            if (moduleClass.isAssignableFrom(module.getClass())) return moduleClass.cast(module);
        }
        return null;
    }

    private void bootstrapModule(final Module module) {
        module.register();
        module.getCommands().forEach(this::registerCommand);
        module.getEventHandlers().forEach(eventHandler -> this.pluginManager.registerEvents(eventHandler, module.getPlugin()));
        if (!module.isHide() && !module.isEnabled() && this.enabeld.contains(module.getName())) module.setEnabled(true);
    }

    private void registerCommand(final ModuleCommand command) {
        final PluginCommand pluginCommand = this.server.getPluginCommand(command.getName());
        if (pluginCommand == null) {
            this.logger.log(Level.SEVERE, "Command \"" + command.getName() + "\" is not the the \"plugin.yml\" registered.");
        } else command.register(pluginCommand);
    }

    public void loadState() {
        final JsonArray modulesJson = this.data.get("enabled", JsonArray.class);

        if (modulesJson != null) modulesJson.forEach(moduleNameJson -> {
            if (moduleNameJson.isJsonPrimitive()) {
                this.enabeld.add(moduleNameJson.getAsString());
            }
        });
    }

    public void saveState() {
        final JsonArray modulesJson = new JsonArray();

        this.modules.forEach(module -> {
            if (!module.isHide() && module.isEnabled()) modulesJson.add(module.getName());
        });

        this.data.set("enabled", modulesJson);
        this.data.save();
    }
}
