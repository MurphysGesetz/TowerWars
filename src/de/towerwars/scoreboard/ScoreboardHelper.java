package de.towerwars.scoreboard;

import de.towerwars.game.gamestate.gamestates.IngameState;
import de.towerwars.game.gamestate.gamestates.LobbyState;
import de.towerwars.managers.ModuleManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ScoreboardHelper {

    private final IngameScoreboard ingameScoreboard;
    private final LobbyScoreboard lobbyScoreboard;
    private final ModuleManager moduleManager;

    public ScoreboardHelper(ModuleManager moduleManager) {
        this.moduleManager = moduleManager;
        this.ingameScoreboard = new IngameScoreboard(moduleManager);
        this.lobbyScoreboard = new LobbyScoreboard();
    }

    public void updateAllPlayTimeDisplay() {
        Bukkit.getOnlinePlayers().forEach(ingameScoreboard::updatePlayTime);
    }

    public void updateAllIncomeDisplay() {
        IngameState ingameState = (IngameState) moduleManager.getGameStateHelper().getCurrentGameState();
        Bukkit.getOnlinePlayers().forEach(current -> ingameScoreboard.updateIncome(current, ingameState.getIncomeDelay()));
    }

    public void updateAllScoreboards() {
        if (moduleManager.getGameStateHelper().getCurrentGameState() == null || moduleManager.getGameStateHelper().getCurrentGameState() instanceof LobbyState) {
            Bukkit.getOnlinePlayers().forEach(lobbyScoreboard::update);
        } else {
            Bukkit.getOnlinePlayers().forEach(ingameScoreboard::update);
        }
    }

    public void updateScoreboard(Player player) {
        if (moduleManager.getGameStateHelper().getCurrentGameState() == null || moduleManager.getGameStateHelper().getCurrentGameState() instanceof LobbyState) {
            lobbyScoreboard.update(player);
        } else {
            ingameScoreboard.update(player);
        }
    }

    public void setScoreboard(Player player) {
        if (moduleManager.getGameStateHelper().getCurrentGameState() == null || moduleManager.getGameStateHelper().getCurrentGameState() instanceof LobbyState) {
            lobbyScoreboard.set(player);
        } else {
            ingameScoreboard.set(player);
        }
    }

    public void setAllScoreboards() {
        if (moduleManager.getGameStateHelper().getCurrentGameState() == null || moduleManager.getGameStateHelper().getCurrentGameState() instanceof LobbyState) {
            Bukkit.getOnlinePlayers().forEach(lobbyScoreboard::set);
        } else {
            Bukkit.getOnlinePlayers().forEach(ingameScoreboard::set);
        }
    }
}
