package de.towerwars.game.gamestate.gamestates;

import de.towerwars.TowerWars;
import de.towerwars.game.gamestate.GameState;
import de.towerwars.game.team.TowerWarsTeam;
import de.towerwars.managers.ModuleManager;
import de.towerwars.player.TowerPlayer;
import org.bukkit.Bukkit;

public class IngameState extends GameState {

    private final ModuleManager moduleManager;
    private boolean running = false;
    private int incomeTaskID;
    private int incomeDelay;

    public IngameState(ModuleManager moduleManager) {
        this.moduleManager = moduleManager;
    }

    @Override
    public void start() {
        moduleManager.getGameStateHelper().setTimestamp(System.currentTimeMillis() + 3600000);
        running = true;
        incomeDelay = 9;
        moduleManager.getScoreboardHelper().setAllScoreboards();
        moduleManager.getScoreboardHelper().updateAllScoreboards();
        Bukkit.getOnlinePlayers().forEach(current -> {
            TowerPlayer towerPlayer = moduleManager.getTowerPlayerHelper().getTowerPlayer(current);
            towerPlayer.updateTeleporterInventory();
            current.getInventory().clear();
            current.getInventory().setContents(moduleManager.getInventoryLoader().getGameInventory().getContents());
            current.getInventory().setItem(0, moduleManager.getInventoryLoader().getSkillItemStacks()[moduleManager.getTowerPlayerHelper().getTowerPlayer(current).getCurrentSkill()]);
        });
        moduleManager.getArmorStandHelper().handlePlayerMoveScheduler();
        startIncomeScheduler();
    }

    @Override
    public void stop() {
        if (running) {
            running = false;
            Bukkit.getScheduler().cancelTask(incomeTaskID);
            moduleManager.getArmorStandHelper().stopPlayerMoveScheduler();
        }
    }

    @Override
    public boolean isRunning() {
        return false;
    }

    private void startIncomeScheduler() {
        incomeTaskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(moduleManager.getPlugin(), () -> {
            Bukkit.getOnlinePlayers().forEach(current -> {
                moduleManager.getTowerPlayerHelper().getTowerPlayer(current).addMobSpawnAmount();
            });
            moduleManager.getScoreboardHelper().updateAllPlayTimeDisplay();
            moduleManager.getScoreboardHelper().updateAllIncomeDisplay();
            if(incomeDelay == 10) {
                for (TowerWarsTeam towerWarsTeam : moduleManager.getTowerWarsTeamHelper().getTowerWarsTeams()) {
                    if (towerWarsTeam.getMember() != null) {
                        towerWarsTeam.addCoins(towerWarsTeam.getIncome());
                        towerWarsTeam.getMember().sendMessage(TowerWars.PREFIX + "§7Du hast +§6" + moduleManager.getMessageFormatter().format(towerWarsTeam.getIncome()) + " §7Gold §7erhalten");
                    }
                }
            }
            incomeDelay--;
            if (incomeDelay == 0) {
                incomeDelay = 10;
            }
        }, 0L, 20L);
    }

    public int getIncomeDelay() {
        return incomeDelay;
    }
}
