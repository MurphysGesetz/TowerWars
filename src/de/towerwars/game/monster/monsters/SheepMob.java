package de.towerwars.game.monster.monsters;

import de.towerwars.game.team.TowerWarsTeam;
import de.towerwars.game.monster.InheritEntity;
import de.towerwars.game.monster.pathfinder.PathFinderGoalAttacker;
import net.minecraft.server.v1_8_R3.World;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;

public class SheepMob extends InheritEntity {

    public SheepMob(World world, Location location, Location targetLocation, TowerWarsTeam towerWarsTeam, TowerWarsTeam attackerTowerWarsTeam) {
        super(world);
        int live = 88;
        ((LivingEntity) this.getBukkitEntity()).setMaxHealth(live);
        this.setHealth(live);
        final float[] moveValue = randFloat();
        this.goalSelector.a(9, new PathFinderGoalAttacker(this, targetLocation, (float) 1.1, towerWarsTeam, attackerTowerWarsTeam, towerWarsTeam.getPath(), moveValue));
        this.setLocation(location.getX(), location.getY(), location.getZ() + moveValue[1], location.getYaw(), location.getPitch());
        this.setCustomName("ac" + live);
        ArmorStand armorStand = (ArmorStand) location.clone().add(0, 0, moveValue[1]).getWorld().spawnEntity(location, EntityType.ARMOR_STAND);
        armorStand.setVisible(false);
        armorStand.setCustomNameVisible(true);
        armorStand.setSmall(true);
        armorStand.setCustomName(attackerTowerWarsTeam.getColoredMemberName() + " §f" + live + " §c❤");
        this.getBukkitEntity().setPassenger(armorStand);
    }
}
