package de.towerwars.game.gamestate.gamestates;

import de.towerwars.TowerWars;
import de.towerwars.game.gamestate.GameState;
import de.towerwars.managers.ModuleManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class LobbyState extends GameState {

    private final ModuleManager moduleManager;
    private boolean running = false;
    private int taskID;
    private int seconds = 45;

    public LobbyState(ModuleManager moduleManager) {
        this.moduleManager = moduleManager;
    }

    @Override
    public void start() {
        running = true;
        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(moduleManager.getPlugin(), () -> {
            switch (seconds) {
                case 45:
                case 30:
                case 15:
                case 10:
                case 5:
                case 3:
                case 2:
                    Bukkit.broadcastMessage(TowerWars.PREFIX + "Das Spiel startet in §e" + seconds + " §7Sekunden§8.");
                    break;
                case 1:
                    Bukkit.broadcastMessage(TowerWars.PREFIX + "Das Spiel startet in §e1er §7Sekunde§8.");
                    break;
                case 0:
                    Bukkit.getOnlinePlayers().forEach(moduleManager.getTowerWarsTeamHelper()::teamAddPlayer);
                    moduleManager.getTowerWarsTeamHelper().teleportTeamSpawn();
                    moduleManager.getGameStateHelper().setGameState(GameState.INGAME_STATE);
                    break;
            }
            if (!(moduleManager.getGameStateHelper().getCurrentGameState() instanceof LobbyState)) return;
            if(Bukkit.getOnlinePlayers().size() == 0) {
                moduleManager.getGameStateHelper().stopCurrentGameState();
            }
            for (Player current : Bukkit.getOnlinePlayers())
                moduleManager.getTitles().sendActionBar(current, TowerWars.PREFIX + "Das Spiel startet in §e" + seconds + " §7Sekunden§8.");
            seconds--;
        }, 0L, 20L);
    }

    @Override
    public void stop() {
        if (running) {
            running = false;
            Bukkit.getScheduler().cancelTask(taskID);
            this.seconds = 45;
        }
    }

    public void forceStart(Player player) {
        if (seconds < 6) {
            player.sendMessage(TowerWars.PREFIX + "Das §eSpiel §7startet bereits§8!");
        } else {
            seconds = 5;
            player.sendMessage(TowerWars.PREFIX + "Das §eSpiel §7wird jetzt §agestartet§8!");
        }
    }

    @Override
    public boolean isRunning() {
        return running;
    }
}
