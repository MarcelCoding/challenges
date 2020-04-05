package net.marcel.challenge.handler;

import net.marcel.challenge.data.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class SpectatorHandler {

    private final Data data;

    private final List<UUID> spectators;

    public SpectatorHandler(final Data data) {
        this.data = data;

        if (this.data.has("spectators")) {
            this.spectators = this.data.getAsStringList("spectators").stream().map(UUID::fromString).collect(Collectors.toList());
        } else {
            this.spectators = new ArrayList<>();
        }
    }

    public void save() {
        this.data.set("spectators", this.spectators.stream().map(UUID::toString).collect(Collectors.toList()));
    }

    public void addSpectator(final UUID uuid) {
        if (!this.spectators.contains(uuid)) this.spectators.add(uuid);
    }

    public void removeSpectator(final UUID uuid) {
        this.spectators.remove(uuid);
    }


    public boolean isSpectator(final UUID uuid) {
        return this.spectators.contains(uuid);
    }
}
