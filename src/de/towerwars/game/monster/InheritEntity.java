package de.towerwars.game.monster;

import net.minecraft.server.v1_8_R3.EntityChicken;
import net.minecraft.server.v1_8_R3.GenericAttributes;
import net.minecraft.server.v1_8_R3.PathfinderGoalSelector;
import net.minecraft.server.v1_8_R3.World;
import org.bukkit.entity.LivingEntity;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class InheritEntity extends EntityChicken {

    public InheritEntity(World world) {
        super(world);
        ((List) Objects.requireNonNull(getPrivateField("b", this.goalSelector))).clear();
        ((List) Objects.requireNonNull(getPrivateField("c", this.goalSelector))).clear();
        ((List) Objects.requireNonNull(getPrivateField("b", this.targetSelector))).clear();
        ((List) Objects.requireNonNull(getPrivateField("c", this.targetSelector))).clear();
        ((LivingEntity) this.getBukkitEntity()).setRemoveWhenFarAway(false);
        ((LivingEntity) this.getBukkitEntity()).setMaximumNoDamageTicks(0);
        setCustomName("_");
        setCustomNameVisible(false);
    }

    public float[] randFloat() {
        float[] move = new float[2];
        final Random rand = new Random();
        move[0] = rand.nextFloat() * (1.9f - -1.9f) + -1.9f;
        move[1] = rand.nextFloat() * (1.9f - -1.9f) + -1.9f;
        return move;
    }

    @Override
    public void initAttributes() {
        super.initAttributes();
        this.getAttributeInstance(GenericAttributes.FOLLOW_RANGE).setValue(100D);
    }

    private Object getPrivateField(String fieldName, Object object) {
        try {
            Field field;
            (field = PathfinderGoalSelector.class.getDeclaredField(fieldName)).setAccessible(true);
            return field.get(object);
        } catch (NoSuchFieldException | IllegalAccessException var2) {
            var2.printStackTrace();
            return null;
        }
    }


    public LivingEntity getEntity() {
        return ((LivingEntity) this.getBukkitEntity());
    }

    @Override
    public void m() {
        super.m();
        this.bs = 100;
    }

    @Override
    protected String z() {
        return "";
    }

    @Override
    protected String bo() {
        return "";
    }

    @Override
    protected String bp() {
        return "";
    }
}
