package de.towerwars.game.gamestate;

import de.towerwars.game.gamestate.gamestates.EndingState;
import de.towerwars.game.gamestate.gamestates.IngameState;
import de.towerwars.game.gamestate.gamestates.LobbyState;
import de.towerwars.managers.ModuleManager;

public class GameStateHelper {

    private final GameState[] gameStates;
    private GameState currentGameState;
    private long timestamp;

    public GameStateHelper(ModuleManager moduleManager) {
        gameStates = new GameState[3];
        gameStates[GameState.LOBBY_STATE] = new LobbyState(moduleManager);
        gameStates[GameState.INGAME_STATE] = new IngameState(moduleManager);
        gameStates[GameState.ENDING_STATE] = new EndingState(moduleManager);
    }

    public void setGameState(int gameStateID) {
        if(currentGameState != null) {
            currentGameState.stop();
        }
        currentGameState = gameStates[gameStateID];
        currentGameState.start();
    }

    public void stopCurrentGameState() {
        if(currentGameState != null) {
            currentGameState.stop();
            currentGameState = null;
        }
    }

    public GameState getCurrentGameState() {
        return currentGameState;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
