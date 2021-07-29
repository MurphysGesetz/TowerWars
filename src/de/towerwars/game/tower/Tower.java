package de.towerwars.game.tower;

import org.bukkit.Location;
import org.bukkit.entity.Entity;

public class Tower {

    private final int level;
    private final String name;
    private final Location location;
    private final Entity entity;
    private final int price;

    public Tower(Entity entity, String name, int level, Location location, int price) {
        this.name = name;
        this.level = level;
        this.entity = entity;
        this.location = location;
        this.price = price;
    }

    public int getPrice() {
        return price;
    }

    public int getLevel() {
        return level;
    }

    public String getName() {
        return name;
    }

    public Location getLocation() {
        return location;
    }

    public boolean isLevel(int level) {
        return this.level == level;
    }

    public void removeTower() {
        if (entity.getPassenger() == null) {
            entity.remove();
        } else if (entity.getPassenger().getPassenger() == null) {
            entity.getPassenger().remove();
            entity.remove();
        } else if (entity.getPassenger().getPassenger().getPassenger() == null) {
            entity.getPassenger().getPassenger().remove();
            entity.getPassenger().remove();
            entity.remove();
        } else {
            entity.getPassenger().getPassenger().getPassenger().remove();
            entity.getPassenger().getPassenger().remove();
            entity.getPassenger().remove();
            entity.remove();
        }
    }
}
