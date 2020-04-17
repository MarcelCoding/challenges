package net.marcel.challenge.modules.teams;

import lombok.Getter;
import org.apache.commons.lang.WordUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.HashSet;

@Getter
public class Team {

    private final String name;
    private final ChatColor color;

    private final HashSet<Player> players;

    public Team(final ChatColor color) {
        this.name = WordUtils.capitalize(color.name().replaceAll("_", " ").toLowerCase());
        this.color = color;

        this.players = new HashSet<>();
    }

    public void addPlayer(final Player player) {
        this.players.add(player);
    }

    void createVanillaTeam(final Player player) {
        final org.bukkit.scoreboard.Team team = player.getScoreboard().registerNewTeam(this.name);

        team.setColor(this.color);
        team.setAllowFriendlyFire(false);
        team.setDisplayName(this.color + this.name);
        team.setPrefix(team.getDisplayName() + ChatColor.DARK_GRAY + " | ");

        this.players.forEach(p -> team.addEntry(p.getName()));
    }
}
