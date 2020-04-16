package net.marcel.challenge;

import lombok.RequiredArgsConstructor;
import org.bukkit.ChatColor;

@RequiredArgsConstructor
public enum Message {

    PREFIX(ChatColor.DARK_GRAY + "[" + Color.PRIMARY + "Server" + ChatColor.DARK_GRAY + "] " + Color.SECONDARY),
    ERROR_PREFIX(ChatColor.DARK_GRAY + "[" + Color.PRIMARY + "Server" + ChatColor.DARK_GRAY + "] " + Color.ERROR),

    NO_PERMISSION(ERROR_PREFIX + "You don't have permissions for that."),
    WRONG_USAGE(ERROR_PREFIX + "Wrong Usage: %s"),
    NAN(ERROR_PREFIX + "Please only type a number."),
    ONLY_PLAYER(ERROR_PREFIX + "This command is only for players."),
    PLAYER_NOT_FOUND(ERROR_PREFIX + "The Player " + Color.PRIMARY + "%s" + Color.ERROR + " cannot found."),
    MODULE_DISABLED(ERROR_PREFIX + "The Module " + Color.PRIMARY + "%s" + Color.ERROR + " is currently disabled.");

    final String content;

    public String format(final String content) {
        return String.format(this.content, content);
    }

    @Override
    public String toString() {
        return this.content;
    }
}
