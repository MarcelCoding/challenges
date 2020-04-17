package net.marcel.challenge.modules.scoreboard;

import lombok.EqualsAndHashCode;
import net.marcel.challenge.modules.Module;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.ScoreboardManager;

@EqualsAndHashCode
public class ScoreboardModule extends Module {

    private static final ScoreboardManager scoreboardManager = Bukkit.getScoreboardManager();

    public ScoreboardModule(final JavaPlugin plugin) {
        super(plugin, "Scoreboard", "Controls the Scoreboard of your Server.");
    }

    @Override
    public void register() {
        this.registerEventHandler(new ScoreboardEventHandler(this));
    }

    @Override
    protected void onEnable() {
        Bukkit.getOnlinePlayers().forEach(this::setupScoreboard);
    }

    @Override
    protected void onDisable() {
        Bukkit.getOnlinePlayers().forEach(this::resetScoreboard);
    }

    public void setupScoreboard(final Player player) {
        player.setScoreboard(scoreboardManager.getNewScoreboard());
    }

    public void resetScoreboard(final Player player) {
        player.setScoreboard(scoreboardManager.getMainScoreboard());
    }
}
