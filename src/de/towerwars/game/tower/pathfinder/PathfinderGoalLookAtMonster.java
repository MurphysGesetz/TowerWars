package de.towerwars.game.tower.pathfinder;

import net.minecraft.server.v1_8_R3.EntityInsentient;
import net.minecraft.server.v1_8_R3.PathfinderGoal;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

import java.util.List;
import java.util.Optional;

public class PathfinderGoalLookAtMonster extends PathfinderGoal {

    private final EntityInsentient entityInsentient;

    public PathfinderGoalLookAtMonster(EntityInsentient entityInsentient) {
        this.entityInsentient = entityInsentient;
        this.a(2);
    }

    @Override
    public boolean a() {
        final List<Entity> entities = entityInsentient.getBukkitEntity().getNearbyEntities(10, 4, 10);
        final Optional<Entity> target = entities.stream().filter(entity -> !(entity.isCustomNameVisible()) && !(entity.getType().equals(EntityType.PLAYER)) && !(entity.getType().equals(EntityType.ARMOR_STAND)) && entityInsentient.getBukkitEntity().getLocation().getY() > entity.getLocation().getY()).findFirst();
        target.ifPresent(entity ->
                entityInsentient.getControllerLook().a(entity.getLocation().getX(), entity.getLocation().getY(), entity.getLocation().getZ(), 999999, 999999));
        return true;
    }

    @Override
    public void e() {
        final List<Entity> entities = entityInsentient.getBukkitEntity().getNearbyEntities(10, 4, 10);
        final Optional<Entity> target = entities.stream().filter(entity -> !(entity.isCustomNameVisible()) && !(entity.getType().equals(EntityType.PLAYER)) && !(entity.getType().equals(EntityType.ARMOR_STAND)) && entityInsentient.getBukkitEntity().getLocation().getY() > entity.getLocation().getY()).findFirst();
        target.ifPresent(entity ->
                entityInsentient.getControllerLook().a(entity.getLocation().getX(), entity.getLocation().getY(), entity.getLocation().getZ(), 999999, 999999));
    }
}
