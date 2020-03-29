package net.marcel.challenge.timer;

import net.marcel.challenge.Color;
import net.marcel.challenge.Message;
import net.marcel.challenge.Permission;
import net.marcel.challenge.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

public class TimerCommand implements CommandExecutor {

    private final TimerHandler timerHandler;

    public TimerCommand(final TimerHandler timerHandler) {
        this.timerHandler = timerHandler;
    }

    @Override
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        if (Utils.checkPermissions(sender, Permission.TIMER_COMMAND)) {

            if (args.length == 1) {
                switch (args[0].toLowerCase()) {
                    case "resume":
                        this.timerHandler.resume();
                        sender.sendMessage(Message.PREFIX + "Timer resumed.");
                        return true;
                    case "pause":
                        try {
                            this.timerHandler.pause();
                            sender.sendMessage(Message.PREFIX + "Timer paused.");
                        } catch (IllegalStateException e) {
                            sender.sendMessage(Message.ERROR_PREFIX.append(e.getMessage()));
                        }
                        return true;
                    case "reset":
                        try {
                            this.timerHandler.reset();
                            sender.sendMessage(Message.PREFIX + "Timer reset.");
                        } catch (IllegalStateException e) {
                            sender.sendMessage(Message.ERROR_PREFIX.append(e.getMessage()));
                        }
                        return true;
                }
            } else if (args.length == 2 && args[0].equalsIgnoreCase("set")) {
                try {
                    final long minutes = Long.parseLong(args[1]);
                    this.timerHandler.set(Duration.of(minutes, ChronoUnit.MINUTES));
                    sender.sendMessage(Message.PREFIX + "The " + Color.PRIMARY + "Timer" + Color.SECONDARY + " is now configured for " + Color.PRIMARY + minutes + " minutes" + Color.SECONDARY + ".");

                } catch (NumberFormatException e) {
                    sender.sendMessage(Message.NAN.toString());
                }
                return true;
            }

            sender.sendMessage(Message.WRONG_USAGE.append("/timer <resume|pause|reset|set> [minutes]"));
        }
        return true;
    }
}
