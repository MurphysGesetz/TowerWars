package de.towerwars.listeners.player;

import de.towerwars.TowerWars;
import de.towerwars.game.gamestate.gamestates.LobbyState;
import de.towerwars.game.team.TowerWarsTeam;
import de.towerwars.managers.ModuleManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;

public class PlayerItemHeldListener implements Listener {

    private final ModuleManager moduleManager;

    public PlayerItemHeldListener(ModuleManager moduleManager) {
        this.moduleManager = moduleManager;
    }

    @EventHandler
    public void handlePlayerItemHeld(PlayerItemHeldEvent event) {
        Player player = event.getPlayer();
        if (moduleManager.getGameStateHelper().getCurrentGameState() == null) return;
        if (moduleManager.getGameStateHelper().getCurrentGameState() instanceof LobbyState) return;
        TowerWarsTeam towerWarsTeam = moduleManager.getTowerPlayerHelper().getTowerPlayer(player).getTowerWarsTeam();
        towerWarsTeam.getArmorStand().setVisible(false);
    }
}
