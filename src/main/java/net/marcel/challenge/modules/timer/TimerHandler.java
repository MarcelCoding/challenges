package net.marcel.challenge.modules.timer;

import lombok.RequiredArgsConstructor;
import net.marcel.challenge.Color;
import net.marcel.challenge.Utils;
import net.marcel.challenge.modules.Module;
import net.marcel.challenge.modules.ModuleData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Collection;

@RequiredArgsConstructor
public class TimerHandler {

    private final Module module;
    private final ModuleData moduleData;
    private Timer timer;

    public void start() {
        this.module.registerTask(Bukkit.getScheduler().runTaskTimerAsynchronously(this.module.getPlugin(), this::run, 30, 20));
    }

    private void load() {
        final Long start = this.moduleData.get("start", Long.class);
        final Long duration = this.moduleData.get("duration", Long.class);
        final TimerType timerType = this.moduleData.get("type", TimerType.class);

        final Long pauseStart = this.moduleData.get("pause_start", Long.class);
        final Long wholePauseTime = this.moduleData.get("whole_pause_time", Long.class);

        if (start != null && duration != null && timerType != null && pauseStart != null && wholePauseTime != null) {
            this.timer = new Timer(LocalDateTime.ofInstant(Instant.ofEpochMilli(start), ZoneOffset.UTC), Duration.ofMillis(duration), timerType, LocalDateTime.ofInstant(Instant.ofEpochMilli(pauseStart), ZoneOffset.UTC), Duration.ofMillis(wholePauseTime));
        }
    }

    public void save() {
        if (this.timer != null) {
            this.moduleData.set("start", this.timer.getStart().atZone(ZoneOffset.UTC).toInstant().toEpochMilli());
            final Duration duration = this.timer.getDuration();
            this.moduleData.set("duration", duration == null ? null : this.timer.getDuration().getSeconds());
            this.moduleData.set("type", this.timer.getType());

            this.moduleData.set("pause_start", this.timer.getPauseStart().atZone(ZoneOffset.UTC).toInstant().getEpochSecond());
            this.moduleData.set("whole_pause_time", this.timer.getWholePauseTime().getSeconds());

        } else this.moduleData.clean();
    }

    public void set(final Duration duration) {
        this.load();
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
