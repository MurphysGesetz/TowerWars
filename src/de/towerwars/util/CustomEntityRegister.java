package de.towerwars.util;

import de.towerwars.game.monster.monsters.*;
import de.towerwars.game.tower.towers.ArcherTower;
import de.towerwars.game.tower.towers.SlimeTower;
import net.minecraft.server.v1_8_R3.EntityInsentient;
import net.minecraft.server.v1_8_R3.EntityTypes;

import java.lang.reflect.Field;
import java.util.Map;

public class CustomEntityRegister {

    private void registerEntity(int id, String entityName, Class<? extends EntityInsentient> entityClass) {
        try {
            Field c = EntityTypes.class.getDeclaredField("c");
            Field f = EntityTypes.class.getDeclaredField("f");
            c.setAccessible(true);
            f.setAccessible(true);
            ((Map)c.get(null)).put(entityClass, entityName);
            ((Map)f.get(null)).put(entityClass, id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadEntities() {
        registerEntity(51, "Skeleton", ArcherTower.class);
        registerEntity(55, "Slime", SlimeTower.class);

        registerEntity(60, "Silverfish", SilverfishMob.class);
        registerEntity(93, "Chicken", ChickenMob.class);
        registerEntity(59, "Cave Spider", CaveSpiderMob.class);
        registerEntity(91, "Sheep", SheepMob.class);
        registerEntity(54, "Zombie", ZombieMob.class);
        registerEntity(54, "Zombie", BabyZombieMob.class);
        registerEntity(95, "Wolf", WolfMob.class);
        registerEntity(52, "Spider", SpiderMob.class);
    }

}
