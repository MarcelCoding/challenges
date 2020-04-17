package net.marcel.challenge.modules.chat;

import lombok.EqualsAndHashCode;
import net.marcel.challenge.modules.Module;
import org.bukkit.plugin.java.JavaPlugin;

@EqualsAndHashCode
public class ChatModule extends Module {

    public ChatModule(final JavaPlugin plugin) {
        super(plugin, "Chat", "Makes your Chat better.");
    }

    @Override
    public void register() {
        this.registerEventHandler(new ChatEventHandler(this.moduleHandler));
    }

    @Override
    protected void onEnable() {
    }

    @Override
    protected void onDisable() {
    }
}
