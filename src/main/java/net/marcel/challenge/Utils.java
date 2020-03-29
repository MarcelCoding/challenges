package net.marcel.challenge;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.time.Duration;
import java.util.List;

public class Utils {

    public static boolean checkPermissions(final CommandSender sender, final Permission permission) {
        if (!sender.hasPermission(permission.toString())) {
            sender.sendMessage(Message.NO_PERMISSION.toString());
            return false;
        }
        return true;
    }

    public static void resetWorlds(final Plugin plugin) {
        final List<World> worlds = Bukkit.getWorlds();

        worlds.forEach(world -> Bukkit.unloadWorld(world, false));

        Bukkit.getScheduler().runTaskLater(plugin, () -> worlds.forEach(world -> {

            Utils.deleteFolder(world.getWorldFolder());

            final WorldCreator worldCreator = new WorldCreator(world.getName());
            worldCreator.environment(world.getEnvironment());
            worldCreator.seed(System.currentTimeMillis());
            worldCreator.createWorld();

        }), 20 * 5);
    }

    public static void deleteFolder(final File folder) {
        final File[] files = folder.listFiles();
        if (files != null) for (final File file : files) {
            if (file.isDirectory()) Utils.deleteFolder(file);
            else file.delete();
        }
        folder.delete();
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
