package net.marcel.challenge.modules.timer;

import lombok.RequiredArgsConstructor;
import net.marcel.challenge.Color;
import net.marcel.challenge.Utils;
import net.marcel.challenge.modules.Module;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collection;

@RequiredArgsConstructor
public class TimerHandler {

    private final Plugin plugin;
    private final Module module;
    private Timer timer;

    public void save() {
//        if (this.timer != null) this.timer.save(this.data, "timer");
//        else this.data.set("timer", null);
    }

    public void start() {
        this.module.registerTask(Bukkit.getScheduler().runTaskTimerAsynchronously(this.plugin, this::run, 30, 20));
    }

    public void set(final Duration duration) {
        this.timer = new Timer(LocalDateTime.now(), duration);
    }

    public void resume() {
        if (this.timer == null) this.timer = new Timer(LocalDateTime.now());
        this.timer.resume();
    }

    public void pause() {
        if (this.timer == null) {
            throw new IllegalStateException();
        }
        this.timer.pause();
    }

    public void reset() {
        if (this.timer == null) {
            throw new IllegalStateException();
        }
        this.timer = null;
    }

    private String getMessage() {
        if (this.timer == null) {
            return Color.SECONDARY + "The " + Color.PRIMARY + "Timer" + Color.SECONDARY + " is not configured.";
        }

        if (this.timer.getType().equals(TimerType.ASCENDING)) {
            return Color.SECONDARY + "Playing since " + Color.PRIMARY + ChatColor.BOLD + Utils.formatDuration(this.timer.getPlayingTimer()) + Color.SECONDARY + " the challenge.";
        } else {
            if (this.timer.isFinished()) {
                return Color.SECONDARY + "The time is " + Color.PRIMARY + "over" + Color.SECONDARY + ".";
            } else {
                return Color.SECONDARY + "You have " + Color.PRIMARY + ChatColor.BOLD + Utils.formatDuration(this.timer.getLeftTime()) + Color.SECONDARY + " left.";
            }
        }
    }

    private void run() {
        final Collection<? extends Player> players = Bukkit.getOnlinePlayers();
        if (!players.isEmpty()) {
            final String message = this.getMessage();
            for (Player player : players) {
                player.sendActionBar(message);
                if (!this.isRunning() && !player.getGameMode().equals(GameMode.SPECTATOR)) {

                    final Location location = player.getLocation().add(0, 0.15, 0);
                    final int particles = 50;
                    final float radius = 1.25f;
                    for (int i = 0; i < particles; i++) {
                        final double angle = 2 * Math.PI * i / particles;
                        final double x = Math.cos(angle) * radius;
                        final double z = Math.sin(angle) * radius;
                        location.add(x, 0, z);
                        player.spawnParticle(Particle.SLIME, location, 1, 0, 0, 0, 0);
                        location.subtract(x, 0, z);
                    }
                }
            }
        }
    }

    public boolean isRunning() {
        return !(this.timer == null || this.timer.isPause() || this.timer.isFinished());
    }
}
