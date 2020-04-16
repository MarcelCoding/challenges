package net.marcel.challenge.modules.chat;

import net.marcel.challenge.Color;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatEventHandler implements Listener {

    @EventHandler
    public void onChat(final AsyncPlayerChatEvent event) {
        event.setFormat(Color.PRIMARY + "%s" + ChatColor.DARK_GRAY + ":" + ChatColor.RESET + " %s");
    }
}
