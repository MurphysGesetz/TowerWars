package de.towerwars.listeners.player;

import de.towerwars.TowerWars;
import de.towerwars.game.gamestate.gamestates.LobbyState;
import de.towerwars.managers.ModuleManager;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerDisconnectListener implements Listener {

    private final ModuleManager moduleManager;

    public PlayerDisconnectListener(ModuleManager moduleManager) {
        this.moduleManager = moduleManager;
    }

    @EventHandler
    public void handlePlayerQuit(PlayerQuitEvent event) {
        if (moduleManager.getGameStateHelper().getCurrentGameState() != null && !(moduleManager.getGameStateHelper().getCurrentGameState() instanceof LobbyState)) {
            event.setQuitMessage(null);
            Bukkit.getScheduler().runTaskLater(moduleManager.getPlugin(), () -> {
                if(Bukkit.getOnlinePlayers().size() == 0) Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "stop");
            }, 2);
        } else {
            event.setQuitMessage(TowerWars.PREFIX + event.getPlayer().getName() + " hat das Spiel verlassen! §8(§b" + (Bukkit.getOnlinePlayers().size() - 1) + "§8/§b6§8)");
            moduleManager.getTowerPlayerHelper().remove(event.getPlayer());
            moduleManager.getScoreboardHelper().updateAllScoreboards();
        }
    }
}
