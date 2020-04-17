package net.marcel.challenge.modules.teams;

import net.marcel.challenge.Color;
import net.marcel.challenge.Message;
import net.marcel.challenge.modules.ModuleCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class TeamCommand extends ModuleCommand {

    private final TeamsModule teamsModule;

    public TeamCommand(final TeamsModule teamsModule) {
        super("team", "Controls your Team.", "/team <join/leave/create> [Name]");
        this.teamsModule = teamsModule;
    }

    @Override
    protected boolean onCommand0(CommandSender sender, String[] args) {
        final Player player = this.checkForPlayer(sender);
        if (player == null) return true;

        if (args.length == 1) {

            if (args[0].equalsIgnoreCase("leave")) {
                final Team team = this.teamsModule.getTeam(player);
                if (team == null) {
                    player.sendMessage(Message.ERROR_PREFIX + "You're not on any team.");
                } else {
                    team.leave(player);
                    player.sendMessage(Message.PREFIX + "You have laved team " + Color.PRIMARY + team.getName() + Color.SECONDARY + ".");
                }
                return true;
            }

        } else if (args.length == 2) {

            if (args[0].equalsIgnoreCase("join")) {
                final Team oldTeam = this.teamsModule.getTeam(player);
                if (oldTeam != null) oldTeam.leave(player);

                final Team newTeam = this.teamsModule.getTeam(args[1]);
                if (newTeam == null) {
                    player.sendMessage(Message.ERROR_PREFIX + "Team " + Color.PRIMARY + args[1] + Color.ERROR + " does not exist.");
                } else {
                    newTeam.join(player);
                    player.sendMessage(Message.PREFIX + "You have joined team " + Color.PRIMARY + newTeam.getName() + Color.SECONDARY + ".");
                }

                return true;
            } else if (args[0].equalsIgnoreCase("create")) {

                if (this.teamsModule.getTeam(args[1]) != null) {
                    player.sendMessage(Message.ERROR_PREFIX + "A team with the name " + Color.PRIMARY + args[1] + Color.ERROR + " already exists.");
                } else {
                    try {
                        final Team team = this.teamsModule.createTeam(ChatColor.valueOf(args[1].toUpperCase()));
                        team.join(player);
                        player.sendMessage(Message.PREFIX + "You have created and joined team " + Color.PRIMARY + team.getName() + Color.SECONDARY + ".");
                    } catch (IllegalArgumentException e) {
                        player.sendMessage(Message.ERROR_PREFIX + "The name " + Color.PRIMARY + args[1] + Color.ERROR + " is invalid.");
                    }
                }

                return true;
            }
        }
        return false;
    }

    @Override
    protected void onTabComplete0(final List<String> results, final CommandSender sender, final String[] args) {
        final Player player = this.checkForPlayer(sender);
        if (player == null) return;

        if (args.length == 1) {

            results.add("join");
            results.add("leave");
            results.add("create");

        } else if (args.length == 2) {

            if (args[0].equalsIgnoreCase("join")) {

                for (final Team team : this.teamsModule.getTeams()) {
                    if (!team.contains(player)) results.add(team.getName());
                }

            } else if (args[0].equalsIgnoreCase("create")) {

                for (final ChatColor value : ChatColor.values()) {
                    results.add(value.name().toLowerCase());
                }
            }
        }
    }
}
