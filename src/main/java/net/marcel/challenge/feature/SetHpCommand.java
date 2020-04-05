package net.marcel.challenge.feature;

import net.marcel.challenge.Message;
import net.marcel.challenge.Permission;
import net.marcel.challenge.commands.ChallengeCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class SetHpCommand extends ChallengeCommand {

    public SetHpCommand() {
        super("sethp", "Change the health of you or other players.", "/sethp <Hp> [Player]", Permission.SET_HP_COMMAND);
    }

    @Override
    protected boolean onCommand0(CommandSender sender, String[] args) {

        if (args.length == 1) {

            final Player player = this.checkForPlayer(sender);
            if (player != null) {
                try {
                    player.setHealth(Double.parseDouble(args[0]));
                } catch (NumberFormatException e) {
                    player.sendMessage(Message.NAN.toString());
                }
            }

        } else if (args.length == 2) {

        }

        return false;
    }

    @Override
    protected void onTabComplete0(List<String> results, CommandSender sender, String[] args) {

    }
}
