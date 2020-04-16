package net.marcel.challenge.modules.timer;

import net.marcel.challenge.Color;
import net.marcel.challenge.Message;
import net.marcel.challenge.Permission;
import net.marcel.challenge.modules.ModuleCommand;
import org.bukkit.command.CommandSender;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class TimerCommand extends ModuleCommand {

    private final TimerHandler timerHandler;

    public TimerCommand(final TimerHandler timerHandler) {
        super("timer",
                "Help's you to control the timer.",
                "/timer <resume|pause|reset|set> [minutes]",
                Permission.TIMER_COMMAND);

        this.timerHandler = timerHandler;
    }

    @Override
    public boolean onCommand0(final CommandSender sender, final String[] args) {
        if (args.length == 1) {
            switch (args[0].toLowerCase()) {
                case "resume":
                    try {
                        this.timerHandler.resume();
                        sender.sendMessage(Message.PREFIX + "The " + Color.PRIMARY + "Timer" + Color.SECONDARY + " is now " + Color.PRIMARY + "resumed" + Color.SECONDARY + ".");
                    } catch (IllegalStateException e) {
                        sender.sendMessage(Message.ERROR_PREFIX + "The " + Color.PRIMARY + "Timer" + Color.ERROR + " is already " + Color.PRIMARY + "resumed" + Color.ERROR + ".");
                    }
                    return true;
                case "pause":
                    try {
                        this.timerHandler.pause();
                        sender.sendMessage(Message.PREFIX + "The " + Color.PRIMARY + "Timer" + Color.SECONDARY + " is now " + Color.PRIMARY + "paused" + Color.SECONDARY + ".");
                    } catch (IllegalStateException e) {
                        sender.sendMessage(Message.ERROR_PREFIX + "The " + Color.PRIMARY + "Timer" + Color.ERROR + " is not " + Color.PRIMARY + "configured" + Color.ERROR + ".");
                    }
                    return true;
                case "reset":
                    try {
                        this.timerHandler.reset();
                        sender.sendMessage(Message.PREFIX + "The " + Color.PRIMARY + "Timer" + Color.SECONDARY + " was " + Color.PRIMARY + "reset" + Color.SECONDARY + ".");
                    } catch (IllegalStateException e) {
                        sender.sendMessage(Message.ERROR_PREFIX + "The " + Color.PRIMARY + "Timer" + Color.ERROR + " has already been " + Color.PRIMARY + "reset" + Color.ERROR + ".");
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
        return false;
    }

    @Override
    public void onTabComplete0(final List<String> results, final CommandSender sender, final String[] args) {
        if (args.length == 1) {
            results.add("resume");
            results.add("pause");
            results.add("reset");
            results.add("set");
        }
    }
}
