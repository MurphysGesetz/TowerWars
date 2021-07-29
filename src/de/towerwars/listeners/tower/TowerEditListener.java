package de.towerwars.listeners.tower;

import de.towerwars.TowerWars;
import de.towerwars.game.team.TowerWarsTeam;
import de.towerwars.game.tower.Tower;
import de.towerwars.managers.ModuleManager;
import de.towerwars.player.TowerPlayer;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class TowerEditListener implements Listener {

    private final ModuleManager moduleManager;

    public TowerEditListener(ModuleManager moduleManager) {
        this.moduleManager = moduleManager;
    }

    @EventHandler
    public void handleTowerEdit(InventoryClickEvent event) {
        final Player player = (Player) event.getWhoClicked();
        if (event.getClickedInventory() == null || event.getCurrentItem() == null || player.getGameMode().equals(GameMode.CREATIVE)) return;
        if (event.getClickedInventory().getTitle().equals("§6▰§e▰ Turm-Menü")) {
            if (event.getCurrentItem().getItemMeta().getDisplayName().equals("§4▰ §cVerkaufen")) {
                player.closeInventory();
                TowerPlayer towerPlayer = moduleManager.getTowerPlayerHelper().getTowerPlayer(player);
                TowerWarsTeam towerWarsTeam = towerPlayer.getTowerWarsTeam();
                Tower tower = towerWarsTeam.getPlacedTower().get(towerWarsTeam.getArmorStand().getLocation());
                towerWarsTeam.addCoins(tower.getPrice() / 2);
                player.sendMessage(TowerWars.PREFIX + "Der Turm wurde verkauft für §6" + moduleManager.getMessageFormatter().format(tower.getPrice() / 2) + " Gold");
                towerWarsTeam.removeTower(tower, towerWarsTeam.getArmorStand().getLocation());
            }
        }
    }
}
