package de.towerwars.game.team;

import de.towerwars.TowerWars;
import de.towerwars.managers.ModuleManager;
import de.towerwars.player.TowerPlayer;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class TowerWarsTeamHelper {

    private final ModuleManager moduleManager;
    private final TowerWarsTeam[] towerWarsTeams;
    private int teamShareCounter = 0;
    public static final int BLUE = 0, RED = 1, GREEN = 2, AQUA = 3, ORANGE = 4, YELLOW = 5;
    private final Location lobbySpawn;

    public TowerWarsTeamHelper(ModuleManager moduleManager) {
        this.moduleManager = moduleManager;
        this.towerWarsTeams = new TowerWarsTeam[6];
        String worldName = moduleManager.getPlugin().getConfig().getString("worldname");
        towerWarsTeams[0] = new TowerWarsTeam(moduleManager, "§9", "§9Blau§7", moduleManager.getMySqlTableHelper().getLocation(worldName, "blauspawn"), moduleManager.getMySqlTableHelper().getLocation(worldName, "blaumin"), moduleManager.getMySqlTableHelper().getLocation(worldName, "blaumax"), moduleManager.getMySqlTableHelper().getLocation(worldName, "blaustart"), moduleManager.getMySqlTableHelper().getLocation(worldName, "blauend"), addPath("blau"));
        towerWarsTeams[1] = new TowerWarsTeam(moduleManager, "§c", "§cRot§7", moduleManager.getMySqlTableHelper().getLocation(worldName, "rotspawn"), moduleManager.getMySqlTableHelper().getLocation(worldName, "rotmin"), moduleManager.getMySqlTableHelper().getLocation(worldName, "rotmax"), moduleManager.getMySqlTableHelper().getLocation(worldName, "rotstart"), moduleManager.getMySqlTableHelper().getLocation(worldName, "rotend"), addPath("rot"));
        towerWarsTeams[2] = new TowerWarsTeam(moduleManager, "§2", "§2Grün§7", moduleManager.getMySqlTableHelper().getLocation(worldName, "gruenspawn"), moduleManager.getMySqlTableHelper().getLocation(worldName, "gruenmin"), moduleManager.getMySqlTableHelper().getLocation(worldName, "gruenmax"), moduleManager.getMySqlTableHelper().getLocation(worldName, "gruenstart"), moduleManager.getMySqlTableHelper().getLocation(worldName, "gruenend"), addPath("gruen"));
        towerWarsTeams[3] = new TowerWarsTeam(moduleManager, "§b", "§bAqua§7", moduleManager.getMySqlTableHelper().getLocation(worldName, "aquaspawn"), moduleManager.getMySqlTableHelper().getLocation(worldName, "aquamin"), moduleManager.getMySqlTableHelper().getLocation(worldName, "aquamax"), moduleManager.getMySqlTableHelper().getLocation(worldName, "aquastart"), moduleManager.getMySqlTableHelper().getLocation(worldName, "aquaend"), addPath("aqua"));
        towerWarsTeams[4] = new TowerWarsTeam(moduleManager, "§6", "§6Orange§7", moduleManager.getMySqlTableHelper().getLocation(worldName, "orangespawn"), moduleManager.getMySqlTableHelper().getLocation(worldName, "orangemin"), moduleManager.getMySqlTableHelper().getLocation(worldName, "orangemax"), moduleManager.getMySqlTableHelper().getLocation(worldName, "orangestart"), moduleManager.getMySqlTableHelper().getLocation(worldName, "orangeend"), addPath("orange"));
        towerWarsTeams[5] = new TowerWarsTeam(moduleManager, "§e", "§eGelb§7", moduleManager.getMySqlTableHelper().getLocation(worldName, "gelbspawn"), moduleManager.getMySqlTableHelper().getLocation(worldName, "gelbmin"), moduleManager.getMySqlTableHelper().getLocation(worldName, "gelbmax"), moduleManager.getMySqlTableHelper().getLocation(worldName, "gelbstart"), moduleManager.getMySqlTableHelper().getLocation(worldName, "gelbend"), addPath("gelb"));
        lobbySpawn = moduleManager.getMySqlTableHelper().getLocation(worldName, "lobby");
    }

    public Location[] addPath(String teamName) {
        String worldName = moduleManager.getPlugin().getConfig().getString("worldname");
        Location[] newPath = new Location[getPathPointAmount() + 1];
        Location[] path = subtractPath();
        for (int i = 1; i < path.length; i++) {
            Location location = moduleManager.getMySqlTableHelper().getLocation(worldName, teamName + "start");
            if (location.getYaw() != 90.0F) {
                double x = path[i].getX() + location.getX();
                double z = path[i].getZ() + location.getZ();
                location.setX(x);
                location.setZ(z);
            } else {
                double x = location.getX() - path[i].getX();
                double z = location.getZ() - path[i].getZ();
                location.setX(x);
                location.setZ(z);
            }
            newPath[i] = location;
        }
        return newPath;
    }

    public Location[] subtractPath() {
        String worldName = moduleManager.getPlugin().getConfig().getString("worldname");
        Location[] newPath = new Location[getPathPointAmount() + 1];
        Location[] path = getPath();
        for (int i = 1; i < path.length; i++) {
            Location location = moduleManager.getMySqlTableHelper().getLocation(worldName, "blaustart");
            double x = path[i].getX() - location.getX();
            double z = path[i].getZ() - location.getZ();
            location.setX(x);
            location.setZ(z);
            newPath[i] = location;
        }
        return newPath;
    }

    public Location[] getPath() {
        String worldName = moduleManager.getPlugin().getConfig().getString("worldname");
        Location[] path = new Location[getPathPointAmount() + 1];
        for (int i = 1; i < path.length; i++) {
            path[i] = moduleManager.getMySqlTableHelper().getLocation(worldName, i + "path");
        }
        return path;
    }

    public int getPathPointAmount() {
        String worldName = moduleManager.getPlugin().getConfig().getString("worldname");
        for (int i = 1; i < 100; i++) {
            if (!moduleManager.getMySqlTableHelper().locationExist(worldName, i + "path")) {
                return i - 1;
            }
        }
        return 0;
    }

    public boolean areEnoughTeansAlive() {
        int aliveTeams = 0;
        for (TowerWarsTeam towerWarsTeam : towerWarsTeams) {
            if (towerWarsTeam.getMember() != null)
                if (towerWarsTeam.isAlive()) {
                    aliveTeams++;
                }
        }
        return aliveTeams >= 2;
    }

    public void summonMonster(TowerWarsTeam invaderTeam, String entityName) {
        for (TowerWarsTeam towerWarsTeam : towerWarsTeams) {
            if (towerWarsTeam != invaderTeam && towerWarsTeam.getTeamLife() > 0) {
                towerWarsTeam.summonMonster(invaderTeam, entityName);
            }
        }
    }

    public void teamAddPlayer(Player player) {
        player.sendMessage(TowerWars.PREFIX + "Du bist in Team §e" + towerWarsTeams[teamShareCounter].getTeamName() + "§8!");
        moduleManager.getTitles().sendTitle(player, "§7Team", "§d" + towerWarsTeams[teamShareCounter].getTeamName(), 50);
        towerWarsTeams[teamShareCounter].setMember(player);
        towerWarsTeams[teamShareCounter].setArmorStand();
        towerWarsTeams[teamShareCounter].setAlive(true);
        towerWarsTeams[teamShareCounter].setTeamLife(20);
        towerWarsTeams[teamShareCounter].setColoredMemberName(player.getName());
        TowerPlayer towerPlayer = moduleManager.getTowerPlayerHelper().getTowerPlayer(player);
        towerPlayer.setTowerWarsTeam(towerWarsTeams[teamShareCounter]);
        teamShareCounter++;
    }

    public void teleportTeamSpawn() {
        for (TowerWarsTeam towerWarsTeam : towerWarsTeams) {
            if (towerWarsTeam.getMember() != null) {
                towerWarsTeam.getMember().teleport(towerWarsTeam.getSpawnLocation());
            }
        }
    }

    public Location getLobbySpawn() {
        return lobbySpawn;
    }

    public TowerWarsTeam[] getTowerWarsTeams() {
        return towerWarsTeams;
    }
}
