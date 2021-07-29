package de.towerwars.game.monster.monsters;

import de.towerwars.game.monster.pathfinder.PathFinderGoalAttackerBabyZombie;
import de.towerwars.game.team.TowerWarsTeam;
import net.minecraft.server.v1_8_R3.EntityZombie;
import net.minecraft.server.v1_8_R3.PathfinderGoalSelector;
import net.minecraft.server.v1_8_R3.World;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class BabyZombieMob extends EntityZombie {

    public BabyZombieMob(World world, Location location, Location targetLocation, TowerWarsTeam towerWarsTeam, TowerWarsTeam attackerTowerWarsTeam, int skippedCheckpoints) {
        super(world);
        ((List) Objects.requireNonNull(getPrivateField("b", this.goalSelector))).clear();
        ((List) Objects.requireNonNull(getPrivateField("c", this.goalSelector))).clear();
        ((List) Objects.requireNonNull(getPrivateField("b", this.targetSelector))).clear();
        ((List) Objects.requireNonNull(getPrivateField("c", this.targetSelector))).clear();
        ((LivingEntity) this.getBukkitEntity()).setRemoveWhenFarAway(false);
        ((LivingEntity) this.getBukkitEntity()).setMaximumNoDamageTicks(0);
        setCustomName("_");
        setCustomNameVisible(false);
        int live = 50;
        ((LivingEntity) this.getBukkitEntity()).setMaxHealth(live);
        this.setHealth(live);
        float[] moveValue = randFloat();
        this.goalSelector.a(9, new PathFinderGoalAttackerBabyZombie(this, targetLocation, (float) 1.0, towerWarsTeam, attackerTowerWarsTeam, towerWarsTeam.getPath(), moveValue, skippedCheckpoints));
        this.setLocation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
        this.setCustomName("aa" + live);
        this.setBaby(true);
        ArmorStand armorStand = (ArmorStand) location.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);
        armorStand.setVisible(false);
        armorStand.setCustomNameVisible(true);
        armorStand.setSmall(true);
        armorStand.setCustomName(attackerTowerWarsTeam.getColoredMemberName() + " §f" + live + " §c❤");
        this.getBukkitEntity().setPassenger(armorStand);
    }

    public float[] randFloat() {
        float[] move = new float[2];
        final Random rand = new Random();
        move[0] = rand.nextFloat() * (1.5f - -1.5f) + -1.5f;
        move[1] = rand.nextFloat() * (1.5f - -1.5f) + -1.5f;
        return move;
    }

    private Object getPrivateField(String fieldName, Object object) {
        try {
            Field field = PathfinderGoalSelector.class.getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(object);
        } catch (IllegalAccessException | NoSuchFieldException var6) {
            var6.printStackTrace();
            return null;
        }
    }
}
