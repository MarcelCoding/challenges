package net.marcel.challenge.timer;

import net.marcel.challenge.Color;
import net.marcel.challenge.Utils;
import net.marcel.challenge.data.Data;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collection;

public class TimerHandler {

    private final Plugin plugin;
    private final Data data;
    private Timer timer;

    public TimerHandler(final Plugin plugin, final Data data) {
        this.plugin = plugin;
        this.data = data;

        if (data.has("timer")) this.timer = new Timer(this.data, "timer");
    }

    public void save() {
        this.timer.save(this.data, "timer");
    }

    public void start() {
        Bukkit.getScheduler().runTaskTimerAsynchronously(this.plugin, this::run, 5 * 10, 20);
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
            throw new IllegalStateException("Timer is not configured.");
        }
        this.timer.pause();
    }

    public void reset() {
        if (this.timer == null) {
            throw new IllegalStateException("Timer is not configured.");
        }
        this.timer = null;
    }

    private String getMessage() {
        if (this.timer == null) {
            return Color.SECONDARY + "The " + Color.PRIMARY + "Timer" + Color.SECONDARY + " is not configured.";
        }

        if (this.timer.getType().equals(TimerType.ASCENDING)) {
            return Color.SECONDARY + "Playing since " + Color.PRIMARY + Utils.formatDuration(this.timer.getPlayingTimer()) + " Time" + Color.SECONDARY + " the challenge.";
        } else {
            if (this.timer.isFinished()) {
                return Color.SECONDARY + "The time is " + Color.PRIMARY + "over" + Color.SECONDARY + ".";
            } else {
                return Color.SECONDARY + "You have " + Color.PRIMARY + Utils.formatDuration(this.timer.getLeftTime()) + " Time" + Color.SECONDARY + " left.";
            }
        }
    }

    private void run() {
        final Collection<? extends Player> players = Bukkit.getOnlinePlayers();
        if (players.size() > 0) {
            final TextComponent textComponent = new TextComponent(this.getMessage());
            players.forEach(player -> player.spigot().sendMessage(ChatMessageType.ACTION_BAR, textComponent));
        }
    }
}
