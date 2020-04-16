package net.marcel.challenge.modules.invsee;

import net.marcel.challenge.Color;
import net.marcel.challenge.Message;
import net.marcel.challenge.Permission;
import net.marcel.challenge.modules.ModuleCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.Collectors;

public class InvseeCommand extends ModuleCommand {

    public InvseeCommand() {
        super("invsee",
                "Shows the Inventory od other Players.",
                "/invsee <Player>",
                Permission.INVSEE_COMMAND);
    }

    @Override
    protected boolean onCommand0(final CommandSender sender, final String[] args) {
        final Player player = this.checkForPlayer(sender);
        if (player == null) return true;

        if (args.length == 1) {
            final Player target = Bukkit.getPlayer(args[0]);

            if (target == null) {
                sender.sendMessage(Message.PLAYER_NOT_FOUND.toString());
                return true;
            }

            player.sendMessage(Message.PREFIX + "You now see the " + Color.PRIMARY + "Inventory" + Color.SECONDARY + " of " + Color.PRIMARY + target.getName() + Color.SECONDARY + ".");
            player.openInventory(target.getInventory());

            return true;

        } else {
            return false;
        }
    }

    @Override
    protected void onTabComplete0(final List<String> results, final CommandSender sender, final String[] args) {
        if (args.length == 1) {
            results.addAll(Bukkit.getOnlinePlayers().stream().map(HumanEntity::getName).collect(Collectors.toList()));
        }
    }
}
