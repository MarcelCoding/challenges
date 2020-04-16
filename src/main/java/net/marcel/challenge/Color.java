package net.marcel.challenge;

import lombok.RequiredArgsConstructor;
import org.bukkit.ChatColor;

@RequiredArgsConstructor
public enum Color {

    PRIMARY(ChatColor.GOLD),
    SECONDARY(ChatColor.GRAY),
    ERROR(ChatColor.RED);

    final String content;

    Color(final ChatColor content) {
        this(content.toString());
    }

    @Override
    public String toString() {
        return this.content;
    }
}
