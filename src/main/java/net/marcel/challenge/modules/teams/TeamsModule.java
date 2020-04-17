package net.marcel.challenge.modules.teams;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import net.marcel.challenge.MinecraftChallenge;
import net.marcel.challenge.modules.Module;
import net.marcel.challenge.modules.ModuleData;
import net.marcel.challenge.utils.JsonUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

@EqualsAndHashCode
public class TeamsModule extends Module {

    private final Logger logger;
    private final ModuleData moduleData;
    @Getter
    private final Set<Team> teams;

    public TeamsModule(final JavaPlugin plugin) {
        super(plugin, "Teams", Material.PURPLE_BANNER, "Controls Teams for some Challenges.");

        this.logger = JavaPlugin.getPlugin(MinecraftChallenge.class).getLogger();
        this.moduleData = new ModuleData(this);
        this.teams = new HashSet<>();
    }

    @Override
    public void register() {
        this.registerCommand(new TeamCommand(this));
        this.registerEventHandler(new TeamsEventHandler(this));
    }

    @Override
    protected void onEnable() {
        Bukkit.getOnlinePlayers().forEach(this::setupTeams);

        final JsonArray jsonTeams = this.moduleData.get("teams", JsonArray.class);
        if (jsonTeams != null) jsonTeams.forEach(jsonTeamElement -> {
            if (jsonTeamElement.isJsonPrimitive()) {
                final String colorName = jsonTeamElement.getAsString();

                try {
                    this.createTeam(ChatColor.valueOf(colorName.strip().toUpperCase()));
                } catch (IllegalArgumentException e) {
                    this.logger.log(Level.WARNING, "Unable to parse Team Color \"" + colorName + "\" from Data file.");
                }
            }
        });

        final JsonArray jsonPlayers = this.moduleData.get("players", JsonArray.class);
        if (jsonTeams != null && jsonPlayers != null) jsonPlayers.forEach(playerJsonElement -> {
            if (playerJsonElement.isJsonObject()) {
                final JsonObject jsonPlayer = playerJsonElement.getAsJsonObject();

                final String uuidString = JsonUtils.get(jsonPlayer, "uuid", String.class);
                final String teamName = JsonUtils.get(jsonPlayer, "team", String.class);

                try {
                    if (uuidString != null && teamName != null)
                        this.getTeam(teamName).join(UUID.fromString(uuidString));
                } catch (IllegalArgumentException e) {
                    this.logger.log(Level.WARNING, "Unable to parse UUID \"" + uuidString + "\" from Data file.");
                }
            }
        });
    }

    @Override
    protected void onDisable() {
        final JsonArray teamsJsonArray = new JsonArray();
        final JsonArray playersJsonArray = new JsonArray();

        this.teams.forEach(team -> {
            teamsJsonArray.add(team.getName());

            team.getPlayers().forEach(player -> {
                final JsonObject playerJson = new JsonObject();
                playerJson.addProperty("uuid", player.getUniqueId().toString());
                playerJson.addProperty("team", team.getName());
                playersJsonArray.add(playerJson);
            });
        });

        this.moduleData.set("teams", teamsJsonArray);
        this.moduleData.set("players", playersJsonArray);

        this.moduleData.save();
        Bukkit.getOnlinePlayers().forEach(this::resetTeams);
        this.teams.clear();
    }

    void setupTeams(final Player player) {
        this.teams.forEach(team -> {
            if (!team.isEmpty() && player.getScoreboard().getTeam(team.getName()) == null) {
                team.createVanillaTeam(player);
            }
        });
    }

    void resetTeams(final Player player) {
        this.teams.forEach(team -> {
            final org.bukkit.scoreboard.Team scoreboardTeam = player.getScoreboard().getTeam(team.getName());
            if (scoreboardTeam != null) scoreboardTeam.unregister();
            else {
                this.logger.log(Level.WARNING, "Unable to delete Team \"" + team.getName() + "\" for Player \"" + player.getName() + "\".");
            }
        });
    }

    void updateTeams() {
        Bukkit.getOnlinePlayers().forEach(player -> {
            this.resetTeams(player);
            this.setupTeams(player);
        });
        this.teams.removeIf(Team::isEmpty);
    }

    public Team createTeam(final ChatColor color) {
        final Team team = new Team(this, color);
        this.teams.add(team);
        return team;
    }

    public Team getTeam(final String name) {
        for (final Team team : this.teams) if (team.getName().equalsIgnoreCase(name)) return team;
        return null;
    }

    public Team getTeam(final UUID playerUuid) {
        return this.getTeam(Bukkit.getOfflinePlayer(playerUuid));
    }

    public Team getTeam(final OfflinePlayer player) {
        for (final Team team : this.teams) if (team.contains(player)) return team;
        return null;
    }
}
