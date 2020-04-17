package net.marcel.challenge.modules.gamemode;

import net.marcel.challenge.Color;
import net.marcel.challenge.Message;
import net.marcel.challenge.Permission;
import net.marcel.challenge.modules.ModuleCommand;
import org.apache.commons.lang.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.Collectors;

public class GameModeCommand extends ModuleCommand {

    public GameModeCommand() {
        super("gamemode",
                "Changes your or the GameMode of other players.",
                "/gamemode <GameMode> [Player]",
                Permission.GAMEMODE_COMMAND);
    }

    @Override
    protected boolean onCommand0(final CommandSender sender, final String[] args) {
        if (args.length == 1) {

            final Player player = this.checkForPlayer(sender);

            if (player != null) {
                try {
                    final GameMode gameMode = GameMode.valueOf(args[0].toUpperCase());
                    final String gameModeName = gameMode.name().substring(0, 1) + gameMode.name().substring(1).toLowerCase();

                    player.setGameMode(gameMode);
                    player.sendMessage(Message.PREFIX + "You set your " + Color.PRIMARY + "Gamemode" + Color.SECONDARY + " to " + Color.PRIMARY + gameModeName + Color.SECONDARY + ".");
                } catch (IllegalArgumentException e) {
                    player.sendMessage(Message.ERROR_PREFIX + "A " + Color.PRIMARY + "Gamemode" + Color.ERROR + " with the name " + Color.PRIMARY + args[0] + Color.ERROR + " cannot be found.");
                    return true;
                }
            }

            return true;

        } else if (args.length == 2) {
            try {
                final GameMode gameMode = GameMode.valueOf(args[0].toUpperCase());
                final String gameModeName = WordUtils.capitalize(gameMode.name().toLowerCase());

                final Player target = Bukkit.getPlayer(args[1]);

                if (target == null) {
                    sender.sendMessage(Message.PLAYER_NOT_FOUND.format(args[1]));
                    return true;
                }

                target.setGameMode(gameMode);
                sender.sendMessage(Message.PREFIX + "You set the " + Color.PRIMARY + "Gamemode" + Color.SECONDARY + " from " + Color.PRIMARY + target.getName() + Color.SECONDARY + " to " + Color.PRIMARY + gameModeName + Color.SECONDARY + ".");
                target.sendMessage(Message.PREFIX + "You " + Color.PRIMARY + "Gamemode" + Color.SECONDARY + " wars set by " + Color.PRIMARY + sender.getName() + Color.SECONDARY + " to " + Color.PRIMARY + gameModeName + Color.SECONDARY + ".");

                return true;
            } catch (IllegalArgumentException e) {
                sender.sendMessage(Message.ERROR_PREFIX + "A " + Color.PRIMARY + "Gamemode" + Color.ERROR + " with the name " + Color.PRIMARY + args[0] + Color.ERROR + " cannot be found.");
                return true;
            }
        }

        return false;
    }

    @Override
    protected void onTabComplete0(final List<String> results, final CommandSender sender, final String[] args) {
        if (args.length == 1) {
            for (final GameMode gameMode : GameMode.values()) results.add(gameMode.name().toLowerCase());
        } else if (args.length == 2) {
            results.addAll(Bukkit.getOnlinePlayers().stream().map(HumanEntity::getName).collect(Collectors.toList()));
        }
    }
}
