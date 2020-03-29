package net.marcel.challenge;

import org.bukkit.ChatColor;

public enum Color {

    PRIMARY(ChatColor.GOLD),
    SECONDARY(ChatColor.GRAY),
    ERROR(ChatColor.RED);

    final String content;

    Color(final String content) {
        this.content = content;
    }

    Color(final ChatColor content) {
        this.content = content.toString();
    }

    @Override
    public String toString() {
        return this.content;
    }
}
