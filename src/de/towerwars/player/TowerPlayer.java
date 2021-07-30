package de.towerwars.player;

import de.towerwars.game.team.TowerWarsTeam;
import de.towerwars.managers.ModuleManager;
import net.minecraft.server.v1_8_R3.EntityArmorStand;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_8_R3.PacketPlayOutSpawnEntityLiving;
import net.minecraft.server.v1_8_R3.WorldServer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.UUID;

public class TowerPlayer {

    private final UUID uuid;
    private final Player player;
    private final ModuleManager moduleManager;
    private int currentSkill;
    private int mobSpawnAmount;
    private Location towerLocation;
    private final Inventory skillsInventory;
    private final Inventory towerMenuInventory;
    private TowerWarsTeam towerWarsTeam;
    private final Inventory teleporterInventory;
    private final Inventory monsterInventory;

    public TowerPlayer(UUID uuid, ModuleManager moduleManager) {
        this.uuid = uuid;
        this.player = Bukkit.getPlayer(uuid);
        this.moduleManager = moduleManager;
        this.mobSpawnAmount = 0;
        this.towerLocation = null;
        this.currentSkill = moduleManager.getMySqlTableHelper().getSkill(uuid.toString());
        this.skillsInventory = Bukkit.createInventory(null, 27, "§6▰§e▰ Kompetenzen");
        this.teleporterInventory = Bukkit.createInventory(null, 27, "§6▰§e▰ Teleportieren");
        this.monsterInventory = Bukkit.createInventory(null, 27, "§6▰§e▰ Monster");
        this.towerMenuInventory = Bukkit.createInventory(null, 27, "§6▰§e▰ Turm-Menü");
    }

    public Player getPlayer() {
        return player;
    }

    public void updateTowerMenu() {
        moduleManager.getInventoryCustomizer().customizeTowerMenuInventory(Bukkit.getPlayer(uuid));
    }

    public void setTowerWarsTeam(TowerWarsTeam towerWarsTeam) {
        this.towerWarsTeam = towerWarsTeam;
    }

    public TowerWarsTeam getTowerWarsTeam() {
        return towerWarsTeam;
    }

    public int getCurrentSkill() {
        return currentSkill;
    }

    public void setCurrentSkill(int currentSkill) {
        this.currentSkill = currentSkill;
    }

    public void updateSkillsInventory() {
        moduleManager.getInventoryCustomizer().customizeSkillsInventory(Bukkit.getPlayer(uuid));
    }

    public void updateMonsterInventory() {
        moduleManager.getInventoryCustomizer().customizeMonsterInventory(Bukkit.getPlayer(uuid));
    }

    public void updateTeleporterInventory() {
        moduleManager.getInventoryCustomizer().customizeTeleporterInventory(Bukkit.getPlayer(uuid));
    }

    public Inventory getTeleporterInventory() {
        return teleporterInventory;
    }

    public void addMobSpawnAmount() {
        if(mobSpawnAmount == 30) return;
        this.mobSpawnAmount = mobSpawnAmount + 1;
        updateMonsterInventory();
    }

    public void removeMobSpawnAmount() {
        if(mobSpawnAmount == 0) return;
        this.mobSpawnAmount = mobSpawnAmount - 1;
        updateMonsterInventory();
    }

    public Inventory getSkillsInventory() {
        return skillsInventory;
    }

    public Inventory getMonsterInventory() {
        return monsterInventory;
    }

    public int getMobSpawnAmount() {
        return mobSpawnAmount;
    }

    public void setTowerLocation(Location towerLocation) {
        this.towerLocation = towerLocation;
    }

    public Location getTowerLocation() {
        return towerLocation;
    }

    public Inventory getTowerMenuInventory() {
        return towerMenuInventory;
    }

    public void showKillOutput(Location location, String prefix, Entity entity) {
        if(entity.isDead()) return;
        final WorldServer s = ((CraftWorld) location.getWorld()).getHandle();
        final EntityArmorStand line = new EntityArmorStand(s, location.getX(), location.getY(), location.getZ());
        if(entity.isDead()) return;
        switch (prefix) {
            case "aa":
                towerWarsTeam.addIncome(0);
                towerWarsTeam.addCoins(1);
                line.setCustomName("§6+1 Gold §7(§b+0 Lohn§7)");
                break;
            case "ab":
                towerWarsTeam.addIncome(0);
                towerWarsTeam.addCoins(2);
                line.setCustomName("§6+2 Gold §7(§b+0 Lohn§7)");
                break;
            case "ac":
                towerWarsTeam.addIncome(1);
                towerWarsTeam.addCoins(4);
                line.setCustomName("§6+4 Gold §7(§b+1 Lohn§7)");
                break;
            case "ad":
                towerWarsTeam.addIncome(2);
                towerWarsTeam.addCoins(10);
                line.setCustomName("§6+10 Gold §7(§b+2 Lohn§7)");
                break;
            case "ae":
                towerWarsTeam.addIncome(3);
                towerWarsTeam.addCoins(15);
                line.setCustomName("§6+15 Gold §7(§b+3 Lohn§7)");
                break;
            case "af":
                towerWarsTeam.addIncome(4);
                towerWarsTeam.addCoins(20);
                line.setCustomName("§6+20 Gold §7(§b+4 Lohn§7)");
                break;
            case "ag":
                towerWarsTeam.addIncome(5);
                towerWarsTeam.addCoins(40);
                line.setCustomName("§6+40 Gold §7(§b+5 Lohn§7)");
                break;
        }
        line.setCustomNameVisible(true);
        line.setGravity(false);
        line.setInvisible(true);
        PacketPlayOutSpawnEntityLiving packet = new PacketPlayOutSpawnEntityLiving(line);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
        Bukkit.getScheduler().runTaskLater(moduleManager.getPlugin(), () -> {
            PacketPlayOutEntityDestroy destroy = new PacketPlayOutEntityDestroy(line.getId());
            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(destroy);
        }, 50L);
    }
}
