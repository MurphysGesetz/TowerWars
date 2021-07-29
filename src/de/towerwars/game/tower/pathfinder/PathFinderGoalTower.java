package de.towerwars.game.tower.pathfinder;

import de.towerwars.game.team.TowerWarsTeam;
import de.towerwars.managers.ModuleManager;
import de.towerwars.player.TowerPlayer;
import net.minecraft.server.v1_8_R3.EntityInsentient;
import net.minecraft.server.v1_8_R3.PathfinderGoal;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;
import java.util.Optional;

public class PathFinderGoalTower extends PathfinderGoal {

    private final EntityInsentient entityInsentient;
    private final int damage;
    private final int range;
    private final int shootdelay;
    private final Player towerPlacer;
    private final TowerPlayer towerPlayer;
    private boolean shooting;
    private final ModuleManager moduleManager;

    public PathFinderGoalTower(ModuleManager moduleManager, EntityInsentient entityInsentient, int damage, int range, int shootdelay, Player towerPlacer) {
        this.moduleManager = moduleManager;
        this.entityInsentient = entityInsentient;
        this.damage = damage;
        this.range = range;
        this.shootdelay = shootdelay;
        this.shooting = false;
        this.towerPlacer = towerPlacer;
        this.towerPlayer = moduleManager.getTowerPlayerHelper().getTowerPlayer(towerPlacer);
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
        final List<Entity> entities = entityInsentient.getBukkitEntity().getNearbyEntities(range, 4, range);
        final Optional<Entity> target = entities.stream().filter(entity -> !(entity.isCustomNameVisible()) && !(entity.getType().equals(EntityType.PLAYER)) && !(entity.getType().equals(EntityType.ARMOR_STAND)) && entityInsentient.getBukkitEntity().getLocation().getY() > entity.getLocation().getY()).findFirst();
        if (target.isPresent()) {
            if (shooting) {
                return;
            }
            final String entityName = target.get().getCustomName();
            if (entityName == null) return;
            this.damagedEntity("" + target.get().getCustomName().charAt(0) + target.get().getCustomName().charAt(1), target.get());

        }
    }

    private void damagedEntity(String prefix, Entity target) {
        final LivingEntity livingEntity = ((LivingEntity) target);
        final int lives = (int) (livingEntity.getHealth() - damage);
        shooting = true;
        switch (prefix) {
            case "aa": case "ab": case "ac": case "ad":
                if (lives <= 0) {
                    towerPlayer.showKillOutput(target.getLocation(), prefix, target);
                    target.getPassenger().remove();
                    target.remove();
                } else {
                    livingEntity.damage(damage);
                    target.setCustomName(prefix + lives);
                    target.getPassenger().setCustomName(target.getPassenger().getCustomName().split("\\s+")[0] + " §f" + lives + " §c❤");
                }
                break;
            case "ae":
                if (lives <= 0) {
                    towerPlayer.showKillOutput(target.getLocation(), prefix, target);
                    TowerPlayer towerPlayer = moduleManager.getTowerPlayerHelper().getTowerPlayer(Bukkit.getPlayer(target.getPassenger().getCustomName().split("\\s+")[0].substring(2)));
                    TowerWarsTeam towerWarsTeam = towerPlayer.getTowerWarsTeam();
                    towerWarsTeam.summonBabyZombie(towerWarsTeam, target.getLocation(), getSkippedCheckpoints(target), towerPlacer);
                    towerWarsTeam.summonBabyZombie(towerWarsTeam, target.getLocation(), getSkippedCheckpoints(target), towerPlacer);
                    towerWarsTeam.summonBabyZombie(towerWarsTeam, target.getLocation(), getSkippedCheckpoints(target), towerPlacer);
                    target.getPassenger().remove();
                    target.remove();
                } else {
                    livingEntity.damage(damage);
                    target.setCustomName(prefix + lives);
                    target.getPassenger().setCustomName(target.getPassenger().getCustomName().split("\\s+")[0] + " §f" + lives + " §c❤");
                }
                break;
        }
        Bukkit.getScheduler().runTaskLater(moduleManager.getPlugin(), () -> shooting = false, shootdelay);
    }

    private int getSkippedCheckpoints(Entity entity) {
        for (PotionEffect potionEffect : ((LivingEntity) entity).getActivePotionEffects()) {
            if (potionEffect.getType().equals(PotionEffectType.JUMP)) {
                return potionEffect.getAmplifier();
            }
        }
        return 0;
    }
}
