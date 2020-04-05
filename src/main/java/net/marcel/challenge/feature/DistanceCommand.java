package net.marcel.challenge.feature;

import net.marcel.challenge.Color;
import net.marcel.challenge.Message;
import net.marcel.challenge.commands.ChallengeCommand;
import net.marcel.challenge.handler.SpectatorHandler;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.Collectors;

public class DistanceCommand extends ChallengeCommand {

    private final SpectatorHandler spectatorHandler;

    public DistanceCommand(final SpectatorHandler spectatorHandler) {
        super("distance",
                "Shows the Distance between your and another or tow other players.",
                "/destance <Player1> [Player2]");
        this.spectatorHandler = spectatorHandler;
    }

    @Override
    public boolean onCommand0(final CommandSender sender, final String[] args) {
        final Player player = this.checkForPlayer(sender);
        if (player == null) return true;

        if (spectatorHandler.isSpectator(player.getUniqueId())) {

            if (args.length == 1) {

                final Player target = Bukkit.getPlayer(args[0]);
                if (target == null) {
                    player.sendMessage(Message.PLAYER_NOT_FOUND.format(args[0]));
                    return true;
                }

                final long distance = Math.round(player.getLocation().distance(target.getLocation()));

                player.sendMessage(Message.PREFIX + "You are " + Color.PRIMARY + distance + " blocks" + Color.SECONDARY + " away from " + Color.PRIMARY + target.getName() + Color.SECONDARY + ".");

            } else if (args.length == 2) {

                final Player target1 = Bukkit.getPlayer(args[0]);
                if (target1 == null) {
                    player.sendMessage(Message.PLAYER_NOT_FOUND.format(args[0]));
                    return true;
                }

                final Player target2 = Bukkit.getPlayer(args[1]);
                if (target2 == null) {
                    player.sendMessage(Message.PLAYER_NOT_FOUND.format(args[1]));
                    return true;
                }

                final long distance = Math.round(target1.getLocation().distance(target2.getLocation()));

                player.sendMessage(Message.PREFIX.toString() + Color.PRIMARY + target1.getName() + Color.SECONDARY + " and " + Color.PRIMARY + target2.getName() + Color.SECONDARY + " are " + Color.PRIMARY + distance + " blocks" + Color.SECONDARY + " apart.");

            } else {
                player.sendMessage(Message.WRONG_USAGE.format("/distance <Player1> [Player2]"));
            }
        } else {
            player.sendMessage(Message.ERROR_PREFIX + "You're not a " + Color.PRIMARY + "Spectator" + Color.ERROR + ".");
        }

        return true;
    }

    @Override
    public void onTabComplete0(final List<String> results, final CommandSender sender, final String[] args) {
        if (args.length == 0 || args.length == 1) {
            results.addAll(Bukkit.getOnlinePlayers().stream().map(HumanEntity::getName).collect(Collectors.toList()));
        }
    }
}
