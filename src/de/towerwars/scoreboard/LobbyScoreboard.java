package de.towerwars.scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class LobbyScoreboard implements IScoreboard{


    @Override
    public void set(Player player) {
        player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective objective = scoreboard.registerNewObjective("aaa", "dummy");

        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName("§7▰ §a§lTower§2§lWars §7▰");
        objective.getScore("§a§8§m---------------").setScore(13);

        {
            Team team = scoreboard.registerNewTeam("x13");
            team.setPrefix("§8▰§7▰ ");
            team.setSuffix("§7Profil");
            team.addEntry("§c");
            objective.getScore("§c").setScore(12);
        }

        {
            Team team = scoreboard.registerNewTeam("x12");
            team.setPrefix(" §b§8➥ §6" + player.getName().charAt(0) + "§e" + player.getName().charAt(1) + player.getName().charAt(2));
            team.setSuffix("§e" + player.getName().substring(3));
            team.addEntry("§a");
            objective.getScore("§a").setScore(11);
        }

        objective.getScore("   ").setScore(10);

        {
            Team team = scoreboard.registerNewTeam("x7");
            team.setPrefix("§8▰§7▰ Warten...");
            team.setSuffix("§7");
            team.addEntry("§7");
            objective.getScore("§7").setScore(9);
        }

        {
            Team team = scoreboard.registerNewTeam("x6");
            team.setPrefix(" §b§8➥ §e");
            team.setSuffix("§a§e" + Bukkit.getOnlinePlayers().size() + "§7/§66");
            team.addEntry("§6");
            objective.getScore("§6").setScore(8);
        }

        objective.getScore("  ").setScore(7);

        {
            Team team = scoreboard.registerNewTeam("x4");
            team.setPrefix("§8▰§7▰ Ranking");
            team.setSuffix("§5");
            team.addEntry("§4");
            objective.getScore("§4").setScore(6);
        }

        {
            Team team = scoreboard.registerNewTeam("x3");
            team.setPrefix(" §a§b§8➥ §e");
            team.setSuffix("§b§e#1");
            team.addEntry("§3");
            objective.getScore("§3").setScore(5);
        }

        objective.getScore(" ").setScore(4);

        {
            Team team = scoreboard.registerNewTeam("x2");
            team.setPrefix("§8▰§7▰ Siege");
            team.setSuffix("§b§a");
            team.addEntry("§0");
            objective.getScore("§0").setScore(3);
        }

        {
            Team team = scoreboard.registerNewTeam("x1");
            team.setPrefix(" §a§b§8➥ §e");
            team.setSuffix("§e0");
            team.addEntry("§1");
            objective.getScore("§1").setScore(2);
        }

        objective.getScore("§8§m---------------").setScore(1);


        Team lobby = scoreboard.registerNewTeam("00000Lobby");

        lobby.setPrefix("§7");
        player.setScoreboard(scoreboard);
    }

    @Override
    public void update(Player player) {
        if (player.getScoreboard() == null || player.getScoreboard().getObjective(DisplaySlot.SIDEBAR) == null) return;
        player.getScoreboard().getTeam("x6").setSuffix("§a§e" + Bukkit.getOnlinePlayers().size() + "§7/§66");
        Bukkit.getOnlinePlayers().forEach(current -> player.getScoreboard().getTeam("00000Lobby").addEntry(current.getName()));
    }
}
