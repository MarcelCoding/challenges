package net.marcel.challenge.modules.teams;

import lombok.Getter;
import org.apache.commons.lang.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.UUID;

@Getter
public class Team {

    private final TeamsModule teamsModule;

    private final String name;
    private final String displayName;
    private final ChatColor color;

    private final HashSet<OfflinePlayer> players;

    public Team(final TeamsModule teamsModule, final ChatColor color) {
        this.teamsModule = teamsModule;

        this.name = color.name().toLowerCase();
        this.displayName = WordUtils.capitalize(this.name.replace("_", " "));
        this.color = color;

        this.players = new HashSet<>();
    }

    public void join(final UUID uuid) {
        this.join(Bukkit.getOfflinePlayer(uuid));
    }

    public void join(final OfflinePlayer player) {
        final Team oldTeam = this.teamsModule.getTeam(player);
        if (oldTeam != null) oldTeam.getPlayers().remove(player);

        this.players.add(player);
        this.teamsModule.updateTeams();
    }

    public void leave(final UUID uuid) {
        this.leave(Bukkit.getOfflinePlayer(uuid));
    }

    public void leave(final OfflinePlayer player) {
        this.players.remove(player);
        this.teamsModule.updateTeams();
    }

    public boolean contains(final UUID uuid) {
        return this.contains(Bukkit.getOfflinePlayer(uuid));
    }

    public boolean contains(final OfflinePlayer player) {
        return this.players.contains(player);
    }

    public boolean isEmpty() {
        return this.players.isEmpty();
    }

    void createVanillaTeam(final Player player) {
        final org.bukkit.scoreboard.Team team = player.getScoreboard().registerNewTeam(this.name);

        team.setColor(this.color);
        team.setAllowFriendlyFire(false);
        team.setDisplayName(this.displayName);
        team.setPrefix(team.getDisplayName() + ChatColor.DARK_GRAY + " | ");

        this.players.forEach(p -> {
            final String playerName = p.getName();
            if (playerName != null) team.addEntry(playerName);
        });
    }
}
