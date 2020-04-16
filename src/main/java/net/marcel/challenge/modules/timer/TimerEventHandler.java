package net.marcel.challenge.modules.timer;

import lombok.RequiredArgsConstructor;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

@RequiredArgsConstructor
public class TimerEventHandler implements Listener {

    public final TimerHandler timerHandler;

    @EventHandler
    public void onInteract(final PlayerInteractEvent event) {
        if (event.getPlayer().getGameMode().equals(GameMode.SURVIVAL) || event.getPlayer().getGameMode().equals(GameMode.ADVENTURE))

            // TODO: Remove ME / Disable Events when Module is disabeld.
            if (this.timerHandler != null) {

                if (!this.timerHandler.isRunning()) {
                    event.setCancelled(true);
                }
            }
    }

    @EventHandler
    public void onDamage(final EntityDamageEvent event) {

        if (!event.getCause().equals(EntityDamageEvent.DamageCause.VOID)) {


            // TODO: Remove ME / Disable Events when Module is disabeld.
            if (this.timerHandler != null) {

                if (!this.timerHandler.isRunning()) {
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onExplode(final EntityExplodeEvent event) {
        // TODO: Remove ME / Disable Events when Module is disabeld.
        if (this.timerHandler != null) {

            if (!this.timerHandler.isRunning()) {
                event.setCancelled(true);
                event.blockList().clear();
            }
        }
    }
}
