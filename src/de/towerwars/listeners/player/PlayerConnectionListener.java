package de.towerwars.listeners.player;

import de.towerwars.TowerWars;
import de.towerwars.game.gamestate.gamestates.LobbyState;
import de.towerwars.managers.ModuleManager;
import de.towerwars.player.TowerPlayer;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerConnectionListener implements Listener {

    private final ModuleManager moduleManager;

    public PlayerConnectionListener(ModuleManager moduleManager) {
        this.moduleManager = moduleManager;
    }

    @EventHandler
    public void handlePlayerJoin(PlayerJoinEvent event) {
        final Player player = event.getPlayer();
        if (moduleManager.getGameStateHelper().getCurrentGameState() != null && !(moduleManager.getGameStateHelper().getCurrentGameState() instanceof LobbyState)) {
            event.setJoinMessage(null);
            player.kickPlayer("");
            moduleManager.getScoreboardHelper().updateAllScoreboards();
        } else {
            event.setJoinMessage(TowerWars.PREFIX + player.getName() + " hat das Spiel betreten! §8(§b" + Bukkit.getOnlinePlayers().size() + "§8/§b6§8)");
            player.setGameMode(GameMode.SURVIVAL);
            player.setHealth(20.0D);
            player.setFoodLevel(20);
            player.teleport(moduleManager.getTowerWarsTeamHelper().getLobbySpawn());
            player.setAllowFlight(true);
            player.setFlySpeed(0.3F);
            player.getInventory().clear();
            if (player.hasPermission("towerwars.premium")) {
                player.getInventory().setContents(moduleManager.getInventoryLoader().getLobbyPremiumInventory().getContents());
            } else {
                player.getInventory().setContents(moduleManager.getInventoryLoader().getLobbyInventory().getContents());
            }
            moduleManager.getScoreboardHelper().setScoreboard(player);
            moduleManager.getScoreboardHelper().updateAllScoreboards();
            if (!moduleManager.getMySqlTableHelper().playerExist(player.getUniqueId().toString())) {
                moduleManager.getMySqlTableHelper().createPlayer(player.getUniqueId().toString(), player.getName());
            }
            final TowerPlayer towerPlayer = moduleManager.getTowerPlayerHelper().create(player);
            towerPlayer.updateSkillsInventory();
            if (moduleManager.getGameStateHelper().getCurrentGameState() != null) return;
            if (Bukkit.getOnlinePlayers().size() >= 1) {
                moduleManager.getGameStateHelper().setGameState(0);
            }
        }
    }
}
