package de.towerwars.game.tower;

import de.towerwars.game.tower.towers.ArcherTower;
import de.towerwars.game.tower.towers.SlimeTower;
import de.towerwars.managers.ModuleManager;
import net.minecraft.server.v1_8_R3.World;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class TowerEngineer {

    public Tower createArcherTower(ModuleManager moduleManager, World world, Location location, int level, Player towerPlacer, int price) {
        ArcherTower archerTower = new ArcherTower(moduleManager, world, location, level, towerPlacer);
        world.addEntity(archerTower);
        return new Tower(archerTower.getEntity(), "Archer", level, archerTower.getBukkitEntity().getLocation(), price);
    }

    public Tower createSlimeTower(ModuleManager moduleManager, World world, Location location, int level, Player towerPlacer, int price) {
        SlimeTower slimeTower = new SlimeTower(moduleManager, world, location, level, towerPlacer);
        world.addEntity(slimeTower);
        return new Tower(slimeTower.getBukkitEntity(), "Slime", level, slimeTower.getBukkitEntity().getLocation(), price);
    }
}
