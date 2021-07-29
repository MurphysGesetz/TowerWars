package de.towerwars.game.gamestate.gamestates;

import de.towerwars.TowerWars;
import de.towerwars.game.gamestate.GameState;
import de.towerwars.game.gamestate.GameStateHelper;
import de.towerwars.managers.ModuleManager;
import org.bukkit.Bukkit;

public class EndingState extends GameState {

    private final ModuleManager moduleManager;
    private boolean running = false;
    private int taskID;
    private int seconds = 15;

    public EndingState(ModuleManager moduleManager) {
        this.moduleManager = moduleManager;
    }

    @Override
    public void start() {
        running = true;
        taskID = Bukkit.getScheduler().scheduleAsyncRepeatingTask(moduleManager.getPlugin(), () -> {
            switch (seconds) {
                case 15:
                case 10:
                case 5:
                case 3:
                case 2:
                    Bukkit.broadcastMessage(TowerWars.PREFIX + "Das Spiel endet in §e" + seconds + " §7Sekunden§8.");
                    break;
                case 1:
                    Bukkit.broadcastMessage(TowerWars.PREFIX + "Das Spiel endet in §e1er §7Sekunde§8.");
                    break;
                case 0:
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "stop");
                    break;
            }
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

    @Override
    public boolean isRunning() {
        return running;
    }
}
