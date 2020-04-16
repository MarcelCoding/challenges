package net.marcel.challenge.modules;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import net.marcel.challenge.Message;
import net.marcel.challenge.Permission;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class ModuleCommand implements CommandExecutor, TabCompleter {

    @Getter
    private final String name;
    private final String description;
    private final String usage;
    private final Permission permission;

    @Setter(AccessLevel.PACKAGE)
    private Module module;

    public ModuleCommand(final String name, final String description) {
        this(name, description, "/" + name);
    }

    public ModuleCommand(final String name, final String description, final String usage) {
        this(name, description, usage, null);
    }

    public ModuleCommand(final String name, final String description, final String usage, final Permission permission) {
        this.name = name.strip().toLowerCase();
        this.description = description.strip();
        this.usage = usage.strip().toLowerCase();
        this.permission = permission;
    }

    protected abstract boolean onCommand0(final CommandSender sender, final String[] args);

    protected abstract void onTabComplete0(final List<String> results, final CommandSender sender, final String[] args);

    @Override
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        if (this.module.isEnabled()) {
            if (!this.onCommand0(sender, args)) {
                sender.sendMessage(Message.WRONG_USAGE.format(this.usage));
            }
        } else sender.sendMessage(Message.MODULE_DISABLED.format(this.module.getName()));

        return true;
    }

    @Override
    public List<String> onTabComplete(final CommandSender sender, final Command command, final String alias, final String[] args) {
        final List<String> results = new ArrayList<>();
        if (this.module.isEnabled()) this.onTabComplete0(results, sender, args);
        return results.stream().filter(tabComplete -> tabComplete.toLowerCase().startsWith(args[args.length - 1].toLowerCase())).collect(Collectors.toList());
    }

    public void register(final PluginCommand command) {
        command.setExecutor(this);
        command.setDescription(this.description);
        command.setUsage(this.usage);
        if (this.permission != null) {
            command.setPermission(this.permission.toString());
            command.setPermissionMessage(Message.NO_PERMISSION.toString());
        }
        command.setTabCompleter(this);
    }

    protected Player checkForPlayer(final CommandSender sender) {
        if (sender instanceof Player) return (Player) sender;
        else {
            sender.sendMessage(Message.ONLY_PLAYER.toString());
            return null;
        }
    }
}
