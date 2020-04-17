package net.marcel.challenge.modules.timer;

import com.destroystokyo.paper.event.player.PlayerPickupExperienceEvent;
import lombok.RequiredArgsConstructor;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerAttemptPickupItemEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;

@RequiredArgsConstructor
public class TimerEventHandler implements Listener {

    public final TimerHandler timerHandler;

    @EventHandler
    public void onInteract(final PlayerInteractEvent event) {
        // TODO: Remove ME / Disable Events when Module is disabeld.
        if (this.timerHandler != null) {

            if (!this.timerHandler.isRunning() && !event.getPlayer().getGameMode().equals(GameMode.CREATIVE)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onInteract(final EntityInteractEvent event) {
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

    @EventHandler
    public void onFoodLevelChange(final FoodLevelChangeEvent event) {

        // TODO: Remove ME / Disable Events when Module is disabeld.
        if (this.timerHandler != null) {

            if (!this.timerHandler.isRunning()) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onXpChange(final PlayerPickupExperienceEvent event) {

        // TODO: Remove ME / Disable Events when Module is disabeld.
        if (this.timerHandler != null) {

            if (!this.timerHandler.isRunning()) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onDrop(final PlayerDropItemEvent event) {
        if (!event.getPlayer().getGameMode().equals(GameMode.CREATIVE)) {

            // TODO: Remove ME / Disable Events when Module is disabeld.
            if (this.timerHandler != null) {

                if (!this.timerHandler.isRunning()) {
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onPickup(final PlayerAttemptPickupItemEvent event) {
        if (!event.getPlayer().getGameMode().equals(GameMode.CREATIVE)) {

            // TODO: Remove ME / Disable Events when Module is disabeld.
            if (this.timerHandler != null) {

                if (!this.timerHandler.isRunning()) {
                    event.setFlyAtPlayer(false);
                    event.setCancelled(true);
                }
            }
        }
    }
}
