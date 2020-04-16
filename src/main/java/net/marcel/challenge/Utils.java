package net.marcel.challenge;

import java.time.Duration;

public class Utils {

    private Utils() {
    }

    public static String formatDuration(final Duration duration) {
        final long seconds = duration.getSeconds();
        final long absSeconds = Math.abs(seconds);
        final String positive = String.format(
                "%02d:%02d:%02d",
                absSeconds / 3600,
                (absSeconds % 3600) / 60,
                absSeconds % 60);
        return seconds < 0 ? "-" + positive : positive;
    }
}
