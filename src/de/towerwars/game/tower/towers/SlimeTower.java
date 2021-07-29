package de.towerwars.game.tower.towers;
import de.towerwars.game.tower.pathfinder.PathFinderGoalTowerSlime;
import de.towerwars.game.tower.pathfinder.PathfinderGoalLookAtMonster;
import de.towerwars.managers.ModuleManager;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.util.List;

public class SlimeTower extends EntitySlime {

    public SlimeTower(ModuleManager moduleManager, World world, Location location, int level, Player towerPlacer) {
        super(world);
        List goalB = (List) getPrivateField("b", goalSelector);
        goalB.clear();
        List goalC = (List) getPrivateField("c", goalSelector);
        goalC.clear();
        List targetB = (List) getPrivateField("b", targetSelector);
        targetB.clear();
        List targetC = (List) getPrivateField("c", targetSelector);
        targetC.clear();
        ((LivingEntity) this.getBukkitEntity()).setRemoveWhenFarAway(false);
        setCustomName("_");
        setCustomNameVisible(false);
        setLocation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
        setCustomNameVisible(true);
        setSize(2);
        this.goalSelector.a(6, new PathfinderGoalLookAtMonster(this));
        if(level == 1) {
            this.setCustomName("Schleim - I");
            this.goalSelector.a(9, new PathFinderGoalTowerSlime(moduleManager, this, 6, 6, 30, 1, towerPlacer));
        } else if(level == 2) {
            this.goalSelector.a(9, new PathFinderGoalTowerSlime(moduleManager, this, 25, 7, 25, 2, towerPlacer));
            EntitySlime entitySlime = new EntitySlime(this.world);
            ((LivingEntity) entitySlime.getBukkitEntity()).setRemoveWhenFarAway(false);
            entitySlime.setPositionRotation(this.locX, this.locY, this.locZ, this.yaw, 0.0F);
            this.world.addEntity(entitySlime);
            entitySlime.mount(this);
            EntitySlime entitySlime2 = new EntitySlime(this.world);
            entitySlime2.setPositionRotation(this.locX, this.locY, this.locZ, this.yaw, 0.0F);
            ((LivingEntity) entitySlime2.getBukkitEntity()).setRemoveWhenFarAway(false);
            this.world.addEntity(entitySlime2);
            entitySlime2.setCustomName("Schleim - II");
            entitySlime2.setCustomNameVisible(true);
            entitySlime2.mount(entitySlime);
        } else if(level == 3) {
            this.goalSelector.a(9, new PathFinderGoalTowerSlime(moduleManager, this, 150, 7, 20, 3, towerPlacer));
            EntitySlime entitySlime = new EntitySlime(this.world);
            ((LivingEntity) entitySlime.getBukkitEntity()).setRemoveWhenFarAway(false);
            entitySlime.setPositionRotation(this.locX, this.locY, this.locZ, this.yaw, 0.0F);
            this.world.addEntity(entitySlime);
            entitySlime.mount(this);
            EntitySlime entitySlime2 = new EntitySlime(this.world);
            ((LivingEntity) entitySlime2.getBukkitEntity()).setRemoveWhenFarAway(false);
            entitySlime2.setPositionRotation(this.locX, this.locY, this.locZ, this.yaw, 0.0F);
            this.world.addEntity(entitySlime2);
            entitySlime2.setCustomNameVisible(true);
            entitySlime2.mount(entitySlime);
            EntitySlime entitySlime3 = new EntitySlime(this.world);
            ((LivingEntity) entitySlime3.getBukkitEntity()).setRemoveWhenFarAway(false);
            entitySlime3.setPositionRotation(this.locX, this.locY, this.locZ, this.yaw, 0.0F);
            this.world.addEntity(entitySlime3);
            entitySlime3.setCustomName("Schleim - III");
            entitySlime3.setCustomNameVisible(true);
            entitySlime3.mount(entitySlime2);
        }
    }

    private Object getPrivateField(String fieldName, Object object) {
        Object o = null;
        try {
            Field field = PathfinderGoalSelector.class.getDeclaredField(fieldName);
            field.setAccessible(true);
            o = field.get(object);
        } catch (IllegalAccessException | NoSuchFieldException var6) {
            var6.printStackTrace();
        }
        return o;
    }

}
