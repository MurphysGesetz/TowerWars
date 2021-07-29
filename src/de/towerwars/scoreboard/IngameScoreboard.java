package de.towerwars.scoreboard;

import de.towerwars.game.team.TowerWarsTeam;
import de.towerwars.game.team.TowerWarsTeamHelper;
import de.towerwars.managers.ModuleManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.text.SimpleDateFormat;
import java.util.Date;

public class IngameScoreboard implements IScoreboard {

    private final ModuleManager moduleManager;
    private final SimpleDateFormat format;
    private final Date date;

    public IngameScoreboard(ModuleManager moduleManager) {
        this.moduleManager = moduleManager;
        this.format = new SimpleDateFormat("mm:ss");
        this.date = new Date();
    }

    public void updateIncome(Player player, int incomeDelay) {
        player.getScoreboard().getTeam("x2").setSuffix("§e" + incomeDelay);
    }

    public void updatePlayTime(Player player) {
        date.setTime(moduleManager.getGameStateHelper().getTimestamp() - System.currentTimeMillis());
        player.getScoreboard().getTeam("x13").setSuffix("§f" + format.format(date));
    }

    @Override
    public void update(Player player) {
        if (player.getScoreboard() == null || player.getScoreboard().getObjective(DisplaySlot.SIDEBAR) == null) return;
        player.getScoreboard().getTeam("x13").setSuffix("§f" + format.format(date));
        player.getScoreboard().getTeam("x3").setSuffix("§e" + moduleManager.getMessageFormatter().format(moduleManager.getTowerPlayerHelper().getTowerPlayer(player).getTowerWarsTeam().getIncome()));
        player.getScoreboard().getTeam("x4").setSuffix("§e" + moduleManager.getMessageFormatter().format(moduleManager.getTowerPlayerHelper().getTowerPlayer(player).getTowerWarsTeam().getCoins()));
        player.getScoreboard().getTeam("x11").setPrefix("§9▰ Blau × §f" + moduleManager.getTowerWarsTeamHelper().getTowerWarsTeams()[TowerWarsTeamHelper.BLUE].getTeamLife());
        player.getScoreboard().getTeam("x10").setPrefix("§c▰ Rot × §f" + moduleManager.getTowerWarsTeamHelper().getTowerWarsTeams()[TowerWarsTeamHelper.RED].getTeamLife());
        player.getScoreboard().getTeam("x9").setPrefix("§a▰ Grün × §f" + moduleManager.getTowerWarsTeamHelper().getTowerWarsTeams()[TowerWarsTeamHelper.GREEN].getTeamLife());
        player.getScoreboard().getTeam("x8").setPrefix("§e▰ Gelb × §f" + moduleManager.getTowerWarsTeamHelper().getTowerWarsTeams()[TowerWarsTeamHelper.YELLOW].getTeamLife());
        player.getScoreboard().getTeam("x7").setPrefix("§6▰ Orange × §f" + moduleManager.getTowerWarsTeamHelper().getTowerWarsTeams()[TowerWarsTeamHelper.ORANGE].getTeamLife());
        player.getScoreboard().getTeam("x6").setPrefix("§b▰ Aqua × §f" + moduleManager.getTowerWarsTeamHelper().getTowerWarsTeams()[TowerWarsTeamHelper.AQUA].getTeamLife());
        for(TowerWarsTeam towerWarsTeam : moduleManager.getTowerWarsTeamHelper().getTowerWarsTeams()) {
            if(towerWarsTeam.getMember() == null) return;
            switch (towerWarsTeam.getTeamColor()) {
                case "§9":
                    player.getScoreboard().getTeam("00100BLAU").addEntry(towerWarsTeam.getMember().getName());
                    break;
                case "§c":
                    player.getScoreboard().getTeam("00000ROT").addEntry(towerWarsTeam.getMember().getName());
                    break;
                case "§2":
                    player.getScoreboard().getTeam("00500GRUEN").addEntry(towerWarsTeam.getMember().getName());
                    break;
                case "§6":
                    player.getScoreboard().getTeam("00400ORANGE").addEntry(towerWarsTeam.getMember().getName());
                    break;
                case "§b":
                    player.getScoreboard().getTeam("00200AQUA").addEntry(towerWarsTeam.getMember().getName());
                    break;
                case "§e":
                    player.getScoreboard().getTeam("00300GELB").addEntry(towerWarsTeam.getMember().getName());
                    break;
            }
        }
    }

    @Override
    public void set(Player player) {
        player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective objective = scoreboard.registerNewObjective("aaa", "dummy");

        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName("§7▰ §a§lTower§2§lWars §7▰");
        objective.getScore("§a§8§m---------------").setScore(14);

        {
            Team team = scoreboard.registerNewTeam("x13");
            team.setPrefix("§7Spielende §8× ");
            team.setSuffix("§f59:59");
            team.addEntry("§c");
            objective.getScore("§c").setScore(13);
        }

        objective.getScore("   ").setScore(12);

        {
            Team team = scoreboard.registerNewTeam("x11");
            team.setPrefix("§9▰ Blau × §f" + moduleManager.getTowerWarsTeamHelper().getTowerWarsTeams()[TowerWarsTeamHelper.BLUE].getTeamLife());
            if (moduleManager.getTowerPlayerHelper().getTowerPlayer(player).getTowerWarsTeam().getTeamName().equals("§9Blau§7")) {
                team.setSuffix(" §2✔");
            }
            team.addEntry("§b");
            objective.getScore("§b").setScore(11);
        }

        {
            Team team = scoreboard.registerNewTeam("x10");
            team.setPrefix("§c▰ Rot × §f" + moduleManager.getTowerWarsTeamHelper().getTowerWarsTeams()[TowerWarsTeamHelper.RED].getTeamLife());
            if (moduleManager.getTowerPlayerHelper().getTowerPlayer(player).getTowerWarsTeam().getTeamName().equals("§cRot§7")) {
                team.setSuffix(" §2✔");
            }
            team.addEntry("§a");
            objective.getScore("§a").setScore(10);
        }

        {
            Team team = scoreboard.registerNewTeam("x9");
            team.setPrefix("§a▰ Grün × §f" + moduleManager.getTowerWarsTeamHelper().getTowerWarsTeams()[TowerWarsTeamHelper.GREEN].getTeamLife());
            if (moduleManager.getTowerPlayerHelper().getTowerPlayer(player).getTowerWarsTeam().getTeamName().equals("§2Grün§7")) {
                team.setSuffix(" §2✔");
            }
            team.addEntry("§9");
            objective.getScore("§9").setScore(9);
        }

        {
            Team team = scoreboard.registerNewTeam("x8");
            team.setPrefix("§e▰ Gelb × §f" + moduleManager.getTowerWarsTeamHelper().getTowerWarsTeams()[TowerWarsTeamHelper.YELLOW].getTeamLife());
            if (moduleManager.getTowerPlayerHelper().getTowerPlayer(player).getTowerWarsTeam().getTeamName().equals("§eGelb§7")) {
                team.setSuffix(" §2✔");
            }
            team.addEntry("§8");
            objective.getScore("§8").setScore(8);
        }

        {
            Team team = scoreboard.registerNewTeam("x7");
            team.setPrefix("§6▰ Orange × §f" + moduleManager.getTowerWarsTeamHelper().getTowerWarsTeams()[TowerWarsTeamHelper.ORANGE].getTeamLife());
            if (moduleManager.getTowerPlayerHelper().getTowerPlayer(player).getTowerWarsTeam().getTeamName().equals("§6Orange§7")) {
                team.setSuffix(" §2✔");
            }
            team.addEntry("§7");
            objective.getScore("§7").setScore(7);
        }

        {
            Team team = scoreboard.registerNewTeam("x6");
            team.setPrefix("§b▰ Aqua × §f" + moduleManager.getTowerWarsTeamHelper().getTowerWarsTeams()[TowerWarsTeamHelper.AQUA].getTeamLife());
            if (moduleManager.getTowerPlayerHelper().getTowerPlayer(player).getTowerWarsTeam().getTeamName().equals("§bAqua§7")) {
                team.setSuffix(" §2✔");
            }
            team.addEntry("§6");
            objective.getScore("§6").setScore(6);
        }

        objective.getScore("  ").setScore(5);

        {
            Team team = scoreboard.registerNewTeam("x4");
            team.setPrefix("§7Gold: ");
            team.setSuffix("§e0");
            team.addEntry("§4");
            objective.getScore("§4").setScore(4);
        }

        {
            Team team = scoreboard.registerNewTeam("x3");
            team.setPrefix("§7Lohn: ");
            team.setSuffix("§e5");
            team.addEntry("§3");
            objective.getScore("§3").setScore(3);
        }

        {
            Team team = scoreboard.registerNewTeam("x2");
            team.setPrefix("§7Einkommen: ");
            team.setSuffix("§e10");
            team.addEntry("§2");
            objective.getScore("§2").setScore(2);
        }

        objective.getScore("§8§m---------------").setScore(1);

        Team rot = scoreboard.registerNewTeam("00000ROT");
        Team blau = scoreboard.registerNewTeam("00100BLAU");
        Team aqua = scoreboard.registerNewTeam("00200AQUA");
        Team gelb = scoreboard.registerNewTeam("00300GELB");
        Team orange = scoreboard.registerNewTeam("00400ORANGE");
        Team gruen = scoreboard.registerNewTeam("00500GRUEN");

        rot.setPrefix("§cRot §8▰ §c");
        blau.setPrefix("§9Blau §8▰ §9");
        aqua.setPrefix("§bAqua §8▰ §b");
        gelb.setPrefix("§eGelb §8▰ §e");
        orange.setPrefix("§6Orange §8▰ §6");
        gruen.setPrefix("§2Grün §8▰ §2");

        player.setScoreboard(scoreboard);
    }
}
