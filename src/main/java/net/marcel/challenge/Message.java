package net.marcel.challenge;

import org.bukkit.ChatColor;

public enum Message {

    PREFIX(ChatColor.DARK_GRAY + "[" + Color.PRIMARY + "Challenge" + ChatColor.DARK_GRAY + "] " + Color.SECONDARY),
    ERROR_PREFIX(ChatColor.DARK_GRAY + "[" + Color.PRIMARY + "Challenge" + ChatColor.DARK_GRAY + "] " + Color.ERROR),

    NO_PERMISSION(ERROR_PREFIX + "You don't have permissions for that."),
    WRONG_USAGE(ERROR_PREFIX + "Wrong Usage: "),
    NAN(ERROR_PREFIX + "Please only type a number.");

    final String content;

    Message(final String content) {
        this.content = content;
    }

    public String append(final String content) {
        return this.content + content;
    }

    @Override
    public String toString() {
        return this.content;
    }
}
