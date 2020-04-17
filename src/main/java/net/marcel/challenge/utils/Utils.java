package net.marcel.challenge.utils;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

public class Utils {

    private Utils() {
    }

    public static boolean createFile(final File file, final boolean folder) throws IOException {
        if (!file.exists()) {
            final File parent = file.getParentFile();
            if (parent != null && !parent.exists()) createFile(parent, true);
            if (folder) return file.mkdirs();
            else return file.createNewFile();
        }
        return false;
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
