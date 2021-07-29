package de.towerwars.game.monster.pathfinder;

import de.towerwars.game.team.TowerWarsTeam;
import net.minecraft.server.v1_8_R3.EntityInsentient;
import net.minecraft.server.v1_8_R3.PathfinderGoal;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PathFinderGoalAttackerBabyZombie extends PathfinderGoal {

    private final EntityInsentient entityInsentient;
    private final Location targetLocation;
    private final float normalSpeed;
    private float speed;
    private final TowerWarsTeam towerWarsTeam;
    private final TowerWarsTeam attackerTowerWarsTeam;
    private int checkpoint;
    private final Location[] path;
    private final float moveX;
    private final float moveZ;

    public PathFinderGoalAttackerBabyZombie(EntityInsentient entityInsentient, Location targetLocation, float speed, TowerWarsTeam towerWarsTeam, TowerWarsTeam attackerTowerWarsTeam, Location[] path, float[] move, int skippedCheckpoints) {
        this.entityInsentient = entityInsentient;
        this.targetLocation = targetLocation;
        this.normalSpeed = speed;
        this.towerWarsTeam = towerWarsTeam;
        this.attackerTowerWarsTeam = attackerTowerWarsTeam;
        this.checkpoint = skippedCheckpoints + 1;
        this.path = path;
        this.moveX = move[0];
        this.moveZ = move[1];
    }

    @Override
    public boolean a() {
        return true;
    }

    @Override
    public boolean b() {
        return false;
    }

    @Override
    public void e() {
        setMoveSpeed();
        final Location location = entityInsentient.getBukkitEntity().getLocation();
        if (Math.floor(location.getX()) == Math.floor(targetLocation.getX()) && Math.floor(location.getZ()) == Math.floor(targetLocation.getZ() + moveZ)) {
            this.entityInsentient.getBukkitEntity().getPassenger().remove();
            this.entityInsentient.getBukkitEntity().remove();
            if (attackerTowerWarsTeam.getMember() != null && towerWarsTeam.getTeamLife() > 0) {
                this.towerWarsTeam.setTeamLife(towerWarsTeam.getTeamLife() - 1);
                attackerTowerWarsTeam.getMember().sendMessage("§7Du hast Team " + towerWarsTeam.getTeamName() + " 1§c❤ §7gestohlen§8!");
                attackerTowerWarsTeam.setTeamLife(attackerTowerWarsTeam.getTeamLife() + 1);
            }
            if (towerWarsTeam.getMember() == null || towerWarsTeam.getTeamLife() < 1) return;
            towerWarsTeam.getMember().sendMessage("§7" + attackerTowerWarsTeam.getTeamName() + " hat dir 1§c❤ §7gestohlen§8! §7(Noch " + towerWarsTeam.getTeamLife() + " Leben)");
        } else if ((path.length - 1) < checkpoint) {
            this.entityInsentient.getNavigation().a(this.entityInsentient.getNavigation().a(targetLocation.getX(), targetLocation.getY(), targetLocation.getZ() + moveZ), speed);
        } else {
            if (Math.floor(location.getX()) == Math.floor(path[checkpoint].getX() + moveX) && Math.floor(location.getZ()) == Math.floor(path[checkpoint].getZ() + moveZ)) {
                checkpoint++;
                if ((path.length - 1) < checkpoint) {
                    this.entityInsentient.getNavigation().a(this.entityInsentient.getNavigation().a(targetLocation.getX() + moveX, targetLocation.getY(), targetLocation.getZ() + moveZ), speed);
                    return;
                }
            }
            this.entityInsentient.getNavigation().a(this.entityInsentient.getNavigation().a(path[checkpoint].getX() + moveX, path[checkpoint].getY(), path[checkpoint].getZ() + moveZ), speed);
        }
    }

    private void setMoveSpeed() {
        if (((LivingEntity) this.entityInsentient.getBukkitEntity()).getActivePotionEffects().isEmpty()) {
            this.speed = normalSpeed;
        } else {
            for (PotionEffect potionEffect : ((LivingEntity) this.entityInsentient.getBukkitEntity()).getActivePotionEffects()) {
                if (potionEffect.getType().equals(PotionEffectType.WATER_BREATHING)) {
                    if (potionEffect.getAmplifier() == 1) {
                        this.speed = (float) (normalSpeed / 1.3);
                    } else if (potionEffect.getAmplifier() == 2) {
                        this.speed = (float) (normalSpeed / 1.5);
                    } else if (potionEffect.getAmplifier() == 3) {
                        this.speed = (float) (normalSpeed / 1.7);
                    }
                }
            }
        }
    }
}
