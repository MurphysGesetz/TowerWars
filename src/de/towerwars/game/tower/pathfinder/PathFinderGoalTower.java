package de.towerwars.game.tower.pathfinder;

import de.towerwars.game.team.TowerWarsTeam;
import de.towerwars.managers.ModuleManager;
import de.towerwars.player.TowerPlayer;
import net.minecraft.server.v1_8_R3.EntityInsentient;
import net.minecraft.server.v1_8_R3.PathfinderGoal;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class PathFinderGoalTower extends PathfinderGoal {

    private final EntityInsentient entityInsentient;
    private final int damage;
    private final int range;
    private final int shootdelay;
    private final Player towerPlacer;
    private final TowerPlayer towerPlayer;
    private boolean shooting;
    public final int level;
    private boolean catched;
    private final boolean splashDamage;
    private final int splashDamageRange;
    private final ModuleManager moduleManager;

    public PathFinderGoalTower(ModuleManager moduleManager, EntityInsentient entityInsentient, int damage, int range, int shootdelay, int level, boolean splashDamage, int splashDamageRange, Player towerPlacer) {
        this.moduleManager = moduleManager;
        this.entityInsentient = entityInsentient;
        this.damage = damage;
        this.range = range;
        this.level = level;
        this.shootdelay = shootdelay;
        this.splashDamage = splashDamage;
        this.splashDamageRange = splashDamageRange;
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

    public abstract void towerEffectQueryDeath(Entity target);

    @Override
    public void e() {
        if(!entityInsentient.getBukkitEntity().getWorld().getBlockAt(entityInsentient.getBukkitEntity().getLocation().add(0, 2, 0)).isEmpty()) return;
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
            case "aa":
            case "ab":
            case "ac":
            case "ad":
                if (lives <= 0) {
                    towerPlayer.showKillOutput(target.getLocation(), prefix, target);
                    target.getPassenger().remove();
                    target.remove();
                } else {
                    towerEffectQueryDeath(target);
                    livingEntity.damage(damage);
                    target.setCustomName(prefix + lives);
                    target.getPassenger().setCustomName(target.getPassenger().getCustomName().split("\\s+")[0] + " §f" + lives + " §c❤");
                }
                if(splashDamage)
                splashDamage(target.getNearbyEntities(splashDamageRange, 0, splashDamageRange));
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
                    towerEffectQueryDeath(target);
                    livingEntity.damage(damage);
                    target.setCustomName(prefix + lives);
                    target.getPassenger().setCustomName(target.getPassenger().getCustomName().split("\\s+")[0] + " §f" + lives + " §c❤");
                }
                if(splashDamage)
                splashDamage(target.getNearbyEntities(splashDamageRange, 0, splashDamageRange));
                break;
            case "af":
                if (lives <= 0) {
                    towerPlayer.showKillOutput(target.getLocation(), prefix, target);
                    target.getPassenger().remove();
                    target.remove();
                } else {
                    towerEffectQueryDeath(target);
                    livingEntity.damage(damage);
                    target.setCustomName(prefix + lives);
                    target.getPassenger().setCustomName(target.getPassenger().getCustomName().split("\\s+")[0] + " §f" + lives + " §c❤");
                }
                if(splashDamage && Math.random() < 0.7)
                    splashDamage(target.getNearbyEntities(splashDamageRange, 0, splashDamageRange));
                break;
            case "ag":
                catchTower(target);
                if (lives <= 0) {
                    towerPlayer.showKillOutput(target.getLocation(), prefix, target);
                    target.getPassenger().remove();
                    target.remove();
                } else {
                    towerEffectQueryDeath(target);
                    livingEntity.damage(damage);
                    target.setCustomName(prefix + lives);
                    target.getPassenger().setCustomName(target.getPassenger().getCustomName().split("\\s+")[0] + " §f" + lives + " §c❤");
                }
                if(splashDamage)
                splashDamage(target.getNearbyEntities(splashDamageRange, 0, splashDamageRange));
                break;
        }
        Bukkit.getScheduler().runTaskLater(moduleManager.getPlugin(), () -> shooting = false, shootdelay);
    }

    private void splashDamage(List<Entity> splashEntites) {
        final List<Entity> entities = splashEntites.stream().filter(entity -> !(entity instanceof ArmorStand)).collect(Collectors.toList());
        for (Entity entity : entities) {
            String prefix = "" + entity.getCustomName().charAt(0) + entity.getCustomName().charAt(1);
            LivingEntity livingEntity = ((LivingEntity) entity);
            int lives = (int) (livingEntity.getHealth() - damage);
            switch (prefix) {
                case "aa":
                case "ab":
                case "ac":
                case "ad":
                    if (lives <= 0) {
                        towerPlayer.showKillOutput(entity.getLocation(), prefix, entity);
                        entity.getPassenger().remove();
                        entity.remove();
                    } else {
                        towerEffectQueryDeath(entity);
                        livingEntity.damage(damage);
                        entity.setCustomName(prefix + lives);
                        entity.getPassenger().setCustomName(entity.getPassenger().getCustomName().split("\\s+")[0] + " §f" + lives + " §c❤");
                    }
                    break;
                case "ae":
                    if (lives <= 0) {
                        towerPlayer.showKillOutput(entity.getLocation(), prefix, entity);
                        TowerPlayer towerPlayer = moduleManager.getTowerPlayerHelper().getTowerPlayer(Bukkit.getPlayer(entity.getPassenger().getCustomName().split("\\s+")[0].substring(2)));
                        TowerWarsTeam towerWarsTeam = towerPlayer.getTowerWarsTeam();
                        towerWarsTeam.summonBabyZombie(towerWarsTeam, entity.getLocation(), getSkippedCheckpoints(entity), towerPlacer);
                        towerWarsTeam.summonBabyZombie(towerWarsTeam, entity.getLocation(), getSkippedCheckpoints(entity), towerPlacer);
                        towerWarsTeam.summonBabyZombie(towerWarsTeam, entity.getLocation(), getSkippedCheckpoints(entity), towerPlacer);
                        entity.getPassenger().remove();
                        entity.remove();
                    } else {
                        towerEffectQueryDeath(entity);
                        livingEntity.damage(damage);
                        entity.setCustomName(prefix + lives);
                        entity.getPassenger().setCustomName(entity.getPassenger().getCustomName().split("\\s+")[0] + " §f" + lives + " §c❤");
                    }
                    break;
                case "ag":
                    catchTower(entity);
                    if (lives <= 0) {
                        towerPlayer.showKillOutput(entity.getLocation(), prefix, entity);
                        entity.getPassenger().remove();
                        entity.remove();
                    } else {
                        towerEffectQueryDeath(entity);
                        livingEntity.damage(damage);
                        entity.setCustomName(prefix + lives);
                        entity.getPassenger().setCustomName(entity.getPassenger().getCustomName().split("\\s+")[0] + " §f" + lives + " §c❤");
                    }
                    splashDamage(entity.getNearbyEntities(2, 0, 2));
                    break;
            }
        }
    }

    private int getSkippedCheckpoints(Entity entity) {
        for (PotionEffect potionEffect : ((LivingEntity) entity).getActivePotionEffects()) {
            if (potionEffect.getType().equals(PotionEffectType.JUMP)) {
                return potionEffect.getAmplifier();
            }
        }
        return 0;
    }

    private void catchTower(Entity entity) {
        if(catched) return;
        for (PotionEffect potionEffect : ((LivingEntity) entity).getActivePotionEffects()) {
            if (potionEffect.getType().equals(PotionEffectType.BLINDNESS)) {
                return;
            }
        }
        ((LivingEntity) entity).addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 60, 1));
        this.catched = true;
        entityInsentient.getBukkitEntity().getWorld().getBlockAt(entityInsentient.getBukkitEntity().getLocation().add(0, 2, 0)).setType(Material.WEB);
        Bukkit.getScheduler().runTaskLater(moduleManager.getPlugin(), () -> {
            entityInsentient.getBukkitEntity().getWorld().getBlockAt(entityInsentient.getBukkitEntity().getLocation().add(0, 2, 0)).setType(Material.AIR);
            this.catched = false;
        }, 60L);
    }
}
