package net.marcel.challenge;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {

    TIMER_COMMAND("minecraft_challenge.command.timer"),
    INVSEE_COMMAND("minecraft_challenge.command.invsee"),
    GAMEMODE_COMMAND("minecraft_challenge.command.gamemode"),
    MODULES("minecraft_challenge.modules");

    final String content;

    @Override
    public String toString() {
        return this.content;
    }
}
