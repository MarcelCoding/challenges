package net.marcel.challenge;

import org.bukkit.ChatColor;

public enum Message {

    PREFIX(ChatColor.DARK_GRAY + "[" + Color.PRIMARY + "Server" + ChatColor.DARK_GRAY + "] " + Color.SECONDARY),
    ERROR_PREFIX(ChatColor.DARK_GRAY + "[" + Color.PRIMARY + "Server" + ChatColor.DARK_GRAY + "] " + Color.ERROR),

    NO_PERMISSION(ERROR_PREFIX + "You don't have permissions for that."),
    WRONG_USAGE(ERROR_PREFIX + "Wrong Usage: %s"),
    NAN(ERROR_PREFIX + "Please only type a number."),
    ONLY_PLAYER(ERROR_PREFIX + "This command is only for players."),
    PLAYER_NOT_FOUND(ERROR_PREFIX + "The Player " + Color.PRIMARY + "%s" + Color.SECONDARY + " cannot found.");

    final String content;

    Message(final String content) {
        this.content = content;
    }

    public String format(final String content) {
        return String.format(this.content, content);
    }

    @Override
    public String toString() {
        return this.content;
    }
}
