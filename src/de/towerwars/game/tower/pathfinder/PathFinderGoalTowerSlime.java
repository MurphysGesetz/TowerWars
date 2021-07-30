package de.towerwars.game.tower.pathfinder;

import de.towerwars.managers.ModuleManager;
import net.minecraft.server.v1_8_R3.EntityInsentient;
import org.bukkit.entity.*;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PathFinderGoalTowerSlime extends PathFinderGoalTower {

    public PathFinderGoalTowerSlime(ModuleManager moduleManager, EntityInsentient entityInsentient, int damage, int range, int shootdelay, int level, Player towerPlacer) {
        super(moduleManager, entityInsentient, damage, range, shootdelay, level, true, 2, towerPlacer);
    }

    @Override
    public void towerEffectQueryDeath(Entity target) {
        if (level == 1) {
            ((LivingEntity) target).addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, 20, 1));
        } else if (level == 2) {
            ((LivingEntity) target).addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, 30, 2));
        } else if (level == 3) {
            ((LivingEntity) target).addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, 40, 3));
        }
    }
}
