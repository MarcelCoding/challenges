package net.marcel.challenge.modules.teams;

import lombok.RequiredArgsConstructor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

@RequiredArgsConstructor
public class TeamsEventHandler implements Listener {

    private final TeamsModule teamsModule;

    @EventHandler
    public void onJoin(final PlayerJoinEvent event) {
        this.teamsModule.setupTeams(event.getPlayer());
    }

    @EventHandler
    public void onQuit(final PlayerQuitEvent event) {
        this.teamsModule.resetTeams(event.getPlayer());
    }
}
