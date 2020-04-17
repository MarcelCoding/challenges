package net.marcel.challenge.modules.teams;

import net.marcel.challenge.MinecraftChallenge;
import net.marcel.challenge.modules.Module;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TeamsModule extends Module {

    private final Logger logger;
    private final Set<Team> teams;

    public TeamsModule(final JavaPlugin plugin) {
        super(plugin, "Teams", Material.PURPLE_BANNER, "Controls Teams for some Challenges.");

        this.logger = JavaPlugin.getPlugin(MinecraftChallenge.class).getLogger();
        this.teams = new HashSet<>();
    }

    @Override
    public void register() {
        this.registerEventHandler(new TeamsEventHandler(this));
    }

    @Override
    protected void onEnable() {
        final Team team = new Team(ChatColor.GOLD);
        team.addPlayer(Bukkit.getPlayer("Tiiiimoo"));
        team.addPlayer(Bukkit.getPlayer("TNT2k"));

        this.teams.add(team);

        Bukkit.getOnlinePlayers().forEach(this::setupTeams);
    }

    @Override
    protected void onDisable() {
        Bukkit.getOnlinePlayers().forEach(this::resetTeams);
    }

    public void setupTeams(final Player player) {
        this.teams.forEach(team -> {
            if (player.getScoreboard().getTeam(team.getName()) == null) team.createVanillaTeam(player);
        });
    }

    public void resetTeams(final Player player) {
        this.teams.forEach(team -> {
            final org.bukkit.scoreboard.Team scoreboardTeam = player.getScoreboard().getTeam(team.getName());
            if (scoreboardTeam != null) scoreboardTeam.unregister();
            else {
                this.logger.log(Level.WARNING, "Unable to delete Team \"" + team.getName() + "\" for Player \"" + player.getName() + "\".");
            }
        });
    }
}
