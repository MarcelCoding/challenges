package net.marcel.challenge.modules.timer;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@AllArgsConstructor
@Getter
public class Timer {

    private final LocalDateTime start;
    private final Duration duration;
    private final TimerType type;

    private LocalDateTime pauseStart;
    private Duration wholePauseTime;

    public Timer(final LocalDateTime start) {
        this(start, null, TimerType.ASCENDING);
    }

    public Timer(final LocalDateTime start, final Duration duration) {
        this(start, duration, TimerType.DESCENDING);
    }

    public Timer(final LocalDateTime start, final Duration duration, final TimerType type) {
        this(start, duration, type, LocalDateTime.now(), Duration.of(0, ChronoUnit.SECONDS));
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

    private Duration getWholeDuration() {
        if (this.duration == null) {
            throw new IllegalStateException("Wrong timer type.");
        }

        return this.duration.plus(this.wholePauseTime);
    }
}
