package net.marcel.challenge.commands;

import net.marcel.challenge.Message;
import net.marcel.challenge.handler.SpectatorHandler;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class SpectatorCommand extends ChallengeCommand {

    private final SpectatorHandler spectatorHandler;

    public SpectatorCommand(final SpectatorHandler spectatorHandler) {
        super("spectator", "Change you Mode.", "/spectate");
        this.spectatorHandler = spectatorHandler;
    }

    @Override
    public boolean onCommand0(final CommandSender sender, final String[] args) {

        if (args.length == 0) {
            final Player player = this.checkForPlayer(sender);
            if (player == null) return true;

            final boolean status = this.spectatorHandler.isSpectator(player.getUniqueId());

            if (status) {
                this.spectatorHandler.removeSpectator(player.getUniqueId());
                player.setGameMode(GameMode.SURVIVAL);
                player.sendMessage(Message.PREFIX + "You're not a Spectator anymore.");
            } else {
                player.setGameMode(GameMode.SPECTATOR);
                this.spectatorHandler.addSpectator(player.getUniqueId());
                player.sendMessage(Message.PREFIX + "You are now a Spectator.");
            }
        }

        return true;
    }

    @Override
    public void onTabComplete0(final List<String> results, final CommandSender sender, final String[] args) {
    }
}
