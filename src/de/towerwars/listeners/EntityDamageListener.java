package de.towerwars.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityTargetEvent;

public class EntityDamageListener implements Listener {

    @EventHandler
    public void handleEntityDamage(EntityDamageEvent event) {
        event.setCancelled(true);
    }
}
