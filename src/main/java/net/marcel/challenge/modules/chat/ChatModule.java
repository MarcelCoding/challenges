package net.marcel.challenge.modules.chat;

import net.marcel.challenge.modules.Module;
import org.bukkit.plugin.java.JavaPlugin;

public class ChatModule extends Module {

    public ChatModule(final JavaPlugin plugin) {
        super(plugin, "Chat", "Makes your Chat better.");
    }

    @Override
    public void register() {
        this.registerEventHandler(new ChatEventHandler());
    }

    @Override
    protected void onEnable() {
    }

    @Override
    protected void onDisable() {
    }
}
