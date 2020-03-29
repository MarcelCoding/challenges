package net.marcel.challenge;

public enum Permission {

    TIMER_COMMAND("minecraft_challenge.command.timer"),
    RESET_COMMAND("minecraft_challenge.command.reset");

    final String content;

    Permission(final String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return this.content;
    }
}
