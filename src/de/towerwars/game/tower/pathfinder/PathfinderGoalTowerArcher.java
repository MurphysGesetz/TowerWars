package de.towerwars.game.tower.pathfinder;

import de.towerwars.managers.ModuleManager;
import net.minecraft.server.v1_8_R3.EntityInsentient;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class PathfinderGoalTowerArcher extends PathFinderGoalTower {

    public PathfinderGoalTowerArcher(ModuleManager moduleManager, EntityInsentient entityInsentient, int damage, int range, int shootdelay, int level, Player towerPlacer) {
        super(moduleManager, entityInsentient, damage, range, shootdelay, level, false, 0, towerPlacer);
    }

    @Override
    public void towerEffectQueryDeath(Entity target) {

    }
}
