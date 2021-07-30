package de.towerwars.game.tower.towers;

import de.towerwars.game.monster.InheritEntity;
import de.towerwars.game.tower.pathfinder.PathfinderGoalLookAtMonster;
import de.towerwars.game.tower.pathfinder.PathfinderGoalTowerArcher;
import de.towerwars.managers.ModuleManager;
import de.towerwars.util.ItemBuilder;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class ArcherTower extends InheritEntity {

    public ArcherTower(ModuleManager moduleManager, World world, Location location, int level, Player towerPlacer) {
        super(world);
        this.setLocation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
        this.setCustomNameVisible(true);
        ((LivingEntity) this.getBukkitEntity()).getEquipment().setItemInHand(new ItemBuilder(Material.BOW).build());
        this.goalSelector.a(6, new PathfinderGoalLookAtMonster(this));
        if (level == 1) {
            this.setCustomName("Bogenschütze - I");
            this.goalSelector.a(9, new PathfinderGoalTowerArcher(moduleManager, this, 15, 8, 40, 1, towerPlacer));
        } else if (level == 2) {
            this.setCustomName("Bogenschütze - II");
            this.goalSelector.a(9, new PathfinderGoalTowerArcher(moduleManager, this, 50, 9, 35, 2, towerPlacer));
            ((LivingEntity) this.getBukkitEntity()).getEquipment().setChestplate(new ItemBuilder(Material.LEATHER_CHESTPLATE).build());
            ((LivingEntity) this.getBukkitEntity()).getEquipment().setLeggings(new ItemBuilder(Material.LEATHER_LEGGINGS).build());
            ((LivingEntity) this.getBukkitEntity()).getEquipment().setBoots(new ItemBuilder(Material.LEATHER_BOOTS).build());
        } else if (level == 3) {
            this.setCustomName("Bogenschütze - III");
            this.goalSelector.a(9, new PathfinderGoalTowerArcher(moduleManager, this, 250, 10, 30, 3, towerPlacer));
            ((LivingEntity) this.getBukkitEntity()).getEquipment().setChestplate(new ItemBuilder(Material.IRON_CHESTPLATE).build());
            ((LivingEntity) this.getBukkitEntity()).getEquipment().setLeggings(new ItemBuilder(Material.IRON_LEGGINGS).build());
            ((LivingEntity) this.getBukkitEntity()).getEquipment().setBoots(new ItemBuilder(Material.IRON_BOOTS).build());
        }
    }
}
