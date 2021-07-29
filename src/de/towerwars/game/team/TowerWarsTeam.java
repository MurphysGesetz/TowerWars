package de.towerwars.game.team;

import de.towerwars.game.gamestate.GameState;
import de.towerwars.game.monster.monsters.*;
import de.towerwars.game.tower.Tower;
import de.towerwars.managers.ModuleManager;
import de.towerwars.player.TowerPlayer;
import net.minecraft.server.v1_8_R3.WorldServer;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class TowerWarsTeam {

    private final ModuleManager moduleManager;
    private int coins;
    private int income;
    private int teamLife;
    private Player member;
    private boolean alive;
    private ArmorStand armorStand;
    private String coloredMemberName;
    private final String teamName;
    private final Location[] path;
    private final String teamColor;
    public final Location endPoint;
    private final Location minPoint;
    private final Location maxPoint;
    private final Location startPoint;
    private final Location spawnLocation;
    private final Map<Location, Tower> placedTower;

    public TowerWarsTeam(ModuleManager moduleManager, String teamColor, String teamName, Location spawnLocation, Location minPoint, Location maxPoint, Location startPoint, Location endPoint, Location[] path) {
        this.moduleManager = moduleManager;
        this.coins = 75;
        this.income = 5;
        this.alive = false;
        this.teamColor = teamColor;
        this.teamLife = 0;
        this.teamName = teamName;
        this.minPoint = minPoint;
        this.coloredMemberName = "";
        this.maxPoint = maxPoint;
        this.endPoint = endPoint;
        this.startPoint = startPoint;
        this.spawnLocation = spawnLocation;
        this.path = path;
        this.placedTower = new HashMap<>();
    }

    public void summonMonster(TowerWarsTeam invader, String entityName) {
        final WorldServer world = ((CraftWorld) startPoint.getWorld()).getHandle();
        switch (entityName) {
            case "silverfish":
                world.addEntity(new SilverfishMob(world, startPoint, endPoint, this, invader));
                break;
            case "chicken":
                world.addEntity(new ChickenMob(world, startPoint, endPoint, this, invader));
                break;
            case "sheep":
                world.addEntity(new SheepMob(world, startPoint, endPoint, this, invader));
                break;
            case "cavespider":
                world.addEntity(new CaveSpiderMob(world, startPoint, endPoint, this, invader));
                break;
            case "zombie":
                world.addEntity(new ZombieMob(world, startPoint, endPoint, this, invader));
                break;
            case "wolf":
                world.addEntity(new WolfMob(world, startPoint, endPoint, this, invader));
                break;
            case "spider":
                world.addEntity(new SpiderMob(world, startPoint, endPoint, this, invader));
                break;
        }
    }

    public void summonBabyZombie(TowerWarsTeam invader, Location location, int skippedCheckpoints, Player plotOwner) {
        TowerPlayer towerPlayer = moduleManager.getTowerPlayerHelper().getTowerPlayer(plotOwner);
        TowerWarsTeam towerWarsTeam = towerPlayer.getTowerWarsTeam();
        final WorldServer world = ((CraftWorld) startPoint.getWorld()).getHandle();
        world.addEntity(new BabyZombieMob(world, location, towerWarsTeam.getEndPoint(), towerWarsTeam, invader, skippedCheckpoints));
    }

    private void killTeam() {
        this.alive = false;
        this.income = 0;
        this.placedTower.clear();
        getMember().setGameMode(GameMode.SPECTATOR);
        moduleManager.getTitles().sendTitle(getMember(), "§4Du bist gestorben", "", 50);
        this.setMember(null);
    }

    public String getTeamColor() {
        return teamColor;
    }

    public void setColoredMemberName(String name) {
        this.coloredMemberName = teamColor + name;
    }

    public void addPlacedTower(Location location, Tower tower) {
        this.placedTower.put(location, tower);
    }

    public int getCoins() {
        return coins;
    }

    public int getIncome() {
        return income;
    }

    public void addIncome(int income) {
        this.income = this.income + income;
        moduleManager.getScoreboardHelper().updateScoreboard(member);
    }

    public void addCoins(int coins) {
        this.coins = this.coins + coins;
        moduleManager.getScoreboardHelper().updateScoreboard(member);
    }

    public void removeCoins(int coins) {
        this.coins = this.coins - coins;
        moduleManager.getScoreboardHelper().updateScoreboard(member);
    }

    public void setTeamLife(int teamLife) {
        moduleManager.getScoreboardHelper().updateAllScoreboards();
        if(!isAlive()) return;
        this.teamLife = teamLife;
        moduleManager.getScoreboardHelper().updateAllScoreboards();
        if(teamLife == 0) {
            killTeam();
            if(!moduleManager.getTowerWarsTeamHelper().areEnoughTeansAlive()) {
                moduleManager.getGameStateHelper().setGameState(GameState.ENDING_STATE);
            }
        }
    }

    public String getColoredMemberName() {
        return coloredMemberName;
    }

    public int getTeamLife() {
        return teamLife;
    }

    public void setArmorStand() {
        this.armorStand = createArmorStand();
    }

    public void setMember(Player member) {
        this.member = member;
    }

    public String getTeamName() {
        return teamName;
    }

    public Player getMember() {
        return member;
    }

    public ArmorStand getArmorStand() {
        return armorStand;
    }

    public Location getSpawnLocation() {
        return spawnLocation;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public boolean isAlive() {
        return alive;
    }

    public Location[] getPath() {
        return path;
    }

    public Location getEndPoint() { return endPoint; }

    public Map<Location, Tower> getPlacedTower() {
        return placedTower;
    }

    public void removeTower(Tower tower, Location location) {
        tower.removeTower();
        placedTower.remove(location);
    }

    public ArmorStand createArmorStand() {
        ArmorStand armorStand = (ArmorStand) this.getSpawnLocation().getWorld().spawnEntity(getSpawnLocation(), EntityType.ARMOR_STAND);
        armorStand.setBasePlate(true);
        armorStand.setGravity(false);
        armorStand.setArms(true);
        armorStand.setSmall(true);
        armorStand.setVisible(false);
        armorStand.setMarker(true);
        armorStand.setCustomName(teamName);
        return armorStand;
    }

    public boolean isInsideArena(Location location) {
        if (location == null) return false;
        if (location.getX() >= this.minPoint.getX() && location.getX() <= this.maxPoint.getX() || location.getX() <= this.minPoint.getX() && location.getX() >= this.maxPoint.getX()) {
            return location.getZ() >= this.minPoint.getZ() && location.getZ() <= this.maxPoint.getZ() || location.getZ() <= this.minPoint.getZ() && location.getZ() >= this.maxPoint.getZ();
        }
        return false;
    }
}