package net.marcel.challenge.modules.chat;

import lombok.RequiredArgsConstructor;
import net.marcel.challenge.Color;
import net.marcel.challenge.modules.ModuleHandler;
import net.marcel.challenge.modules.teams.Team;
import net.marcel.challenge.modules.teams.TeamsModule;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

@RequiredArgsConstructor
public class ChatEventHandler implements Listener {

    private final ModuleHandler moduleHandler;
    private TeamsModule teamsModule;

    @EventHandler
    public void onChat(final AsyncPlayerChatEvent event) {
        if (this.teamsModule == null) this.teamsModule = this.moduleHandler.getModule(TeamsModule.class);

        String color = Color.PRIMARY.toString();
        final String message = event.getMessage();
        final boolean global = message.toLowerCase().startsWith("@a");

        if (this.teamsModule.isEnabled()) {
            final Team team = this.teamsModule.getTeam(event.getPlayer());
            if (team != null) color = String.valueOf(team.getColor());
        }

        event.setFormat((global ? ChatColor.DARK_GRAY + "[" + ChatColor.GRAY + "@all" + ChatColor.DARK_GRAY + "] " : "") + color + "%s" + ChatColor.DARK_GRAY + ":" + ChatColor.RESET + " %s");
    }
}
