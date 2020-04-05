package net.marcel.challenge.handler.timer;

import net.marcel.challenge.data.Data;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

public class Timer {

    private final LocalDateTime start;
    private final Duration duration;
    private final TimerType type;

    private LocalDateTime pauseStart;
    private Duration wholePauseTime;

    private Timer(final LocalDateTime start, final Duration duration, final TimerType type) {
        this.start = start;
        this.duration = duration;
        this.type = type;

        this.wholePauseTime = Duration.of(0, ChronoUnit.SECONDS);
        this.pauseStart = LocalDateTime.now();
    }

    public Timer(final LocalDateTime start, final Duration duration) {
        this(start, duration, TimerType.DESCENDING);
    }

    public Timer(final LocalDateTime start) {
        this(start, null, TimerType.ASCENDING);
    }

    public Timer(final Data data, final String path) {
        this.start = ZonedDateTime.parse(data.getAsString(path + ".start")).toLocalDateTime();
        this.duration = Duration.of(data.getAsLong(path + ".duration"), ChronoUnit.SECONDS);
        this.type = TimerType.valueOf(data.getAsString(path + ".type"));

        if (data.has(path + ".pause.start")) {
            this.pauseStart = ZonedDateTime.parse(data.getAsString(path + ".pause.start")).toLocalDateTime();
        }
        this.wholePauseTime = Duration.of(data.getAsLong(path + ".pause.whole_time"), ChronoUnit.SECONDS);
    }

    public void save(final Data data, final String path) {
        data.set(path + ".start", this.start.atOffset(ZoneOffset.UTC).toString());
        data.set(path + ".duration", this.duration.getSeconds());
        data.set(path + ".type", this.type.name());

        if (this.pauseStart != null) {
            data.set(path + ".pause.start", this.pauseStart.atOffset(ZoneOffset.UTC).toString());
        } else {
            data.set(path + ".pause.start", null);
        }
        data.set(path + ".pause.whole_time", this.wholePauseTime.getSeconds());
    }

    public void pause() throws IllegalStateException {
        if (this.pauseStart != null) {
            throw new IllegalStateException("Timer is already paused.");
        }
        this.pauseStart = LocalDateTime.now();
    }

    public void resume() throws IllegalStateException {
        if (this.pauseStart == null) {
            throw new IllegalStateException("Timer is not paused.");
        }

        this.wholePauseTime = this.wholePauseTime.plus(Duration.between(this.pauseStart, LocalDateTime.now()));
        this.pauseStart = null;
    }

    public boolean isFinished() throws IllegalStateException {
        if (this.type == TimerType.ASCENDING) {
            return false;
        } else {
            if (this.duration == null) {
                throw new IllegalStateException("Wrong timer type.");
            }
            return !LocalDateTime.now().isBefore(this.start.plus(this.getWholeDuration()));
        }
    }

    public Duration getPlayingTimer() {
        final LocalDateTime currentEnd = this.pauseStart == null ? LocalDateTime.now() : this.pauseStart;
        return Duration.between(this.start, currentEnd).minus(this.wholePauseTime);
    }

    public Duration getLeftTime() {
        final LocalDateTime currentEnd = this.pauseStart == null ? LocalDateTime.now() : this.pauseStart;
        return Duration.between(currentEnd, this.start.plus(this.duration).plus(this.wholePauseTime));
    }

    public boolean isPause() {
        return this.pauseStart != null;
    }

    public TimerType getType() {
        return this.type;
    }

    private Duration getWholeDuration() {
        if (this.duration == null) {
            throw new IllegalStateException("Wrong timer type.");
        }

        return this.duration.plus(this.wholePauseTime);
    }
}
