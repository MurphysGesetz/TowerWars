package de.towerwars.listeners.player;

import de.towerwars.TowerWars;
import de.towerwars.game.gamestate.gamestates.LobbyState;
import de.towerwars.game.team.TowerWarsTeam;
import de.towerwars.game.tower.Tower;
import de.towerwars.managers.ModuleManager;
import de.towerwars.player.TowerPlayer;
import net.minecraft.server.v1_8_R3.WorldServer;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class PlayerInteractListener implements Listener {

    private final ModuleManager moduleManager;
    private final ByteArrayOutputStream b = new ByteArrayOutputStream();
    private final DataOutputStream out = new DataOutputStream(b);

    public PlayerInteractListener(ModuleManager moduleManager) {
        this.moduleManager = moduleManager;
    }

    @EventHandler
    public void handlePlayerInteract(PlayerInteractEvent event) {
        final Player player = event.getPlayer();
        if (player.getGameMode().equals(GameMode.CREATIVE)) return;
        player.updateInventory();
        event.setCancelled(true);
        if (event.getItem() == null || event.getItem().getItemMeta() == null || event.getItem().getItemMeta().getDisplayName() == null)
            return;

        final TowerPlayer towerPlayer = moduleManager.getTowerPlayerHelper().getTowerPlayer(player);
        final TowerWarsTeam towerWarsTeam = towerPlayer.getTowerWarsTeam();

        if (event.getItem().getType() == Material.BOOK) {
            player.openInventory(towerPlayer.getMonsterInventory());

        } else if (event.getItem().getType() == Material.ARMOR_STAND) {
            towerPlayer.setTowerLocation(towerWarsTeam.getArmorStand().getLocation());
            if (towerWarsTeam.getArmorStand().isVisible()) {
                player.setVelocity(new Vector(0, 0, 0));
                if (towerWarsTeam.getPlacedTower().get(towerWarsTeam.getArmorStand().getLocation()) == null) {
                    player.openInventory(moduleManager.getInventoryLoader().getTowerInventory());
                } else {
                    towerPlayer.updateTowerMenu();
                    player.openInventory(towerPlayer.getTowerMenuInventory());
                }
            }

        } else if (event.getItem().getType() == Material.ENDER_CHEST) {
            player.openInventory(towerPlayer.getSkillsInventory());

        } else if (event.getItem().getItemMeta().getDisplayName().startsWith("§e▰")) {
            player.sendMessage(TowerWars.PREFIX + "Diese Funktion wird noch programmiert!");

        } else if (event.getItem().getItemMeta().getDisplayName().equals("§4▰§c▰ Verlassen")) {
            try {
                out.writeUTF("Connect");
                out.writeUTF("Lobby-1");
            } catch (IOException var9) {
                var9.printStackTrace();
            }
            player.sendPluginMessage(moduleManager.getPlugin(), "BungeeCord", b.toByteArray());

        } else if (event.getItem().getType() == Material.REDSTONE_TORCH_ON) {
            if (moduleManager.getGameStateHelper().getCurrentGameState() == null) {
                player.sendMessage(TowerWars.PREFIX + "Warten auf Spieler...");
            } else {
                LobbyState lobbyState = (LobbyState) moduleManager.getGameStateHelper().getCurrentGameState();
                lobbyState.forceStart(player);
            }

        } else if (event.getItem().getType() == Material.NETHER_STAR) {
            player.sendMessage(TowerWars.PREFIX + "§cDeine Statistiken konnten nicht geladen werden.");

        } else if (event.getItem().getType() == Material.COMPASS) {
            player.openInventory(towerPlayer.getTeleporterInventory());

        } else if (event.getItem().getType() == Material.BEACON) {
            final Location location = moduleManager.getArmorStandHelper().getTargetBlock(player).add(0.5D, 1.0D, 0.5D);
            final WorldServer world = ((CraftWorld) player.getWorld()).getHandle();
            if (towerWarsTeam.isInsideArena(location)) {
                if (towerWarsTeam.getPlacedTower().get(location) != null) {
                    Tower tower = towerWarsTeam.getPlacedTower().get(location);
                    if (!isImprovable(player, tower, towerPlayer, towerWarsTeam, location, world, "Archer", 1, 90, 10) && !isImprovable(player, tower, towerPlayer, towerWarsTeam, location, world, "Archer", 2, 1340, 90) && !isImprovable(player, tower, towerPlayer, towerWarsTeam, location, world, "Slime", 1, 650, 50)) {
                        isImprovable(player, tower, towerPlayer, towerWarsTeam, location, world, "Slime", 2, 8650, 650);
                    }
                }
            }
        }
    }

    public boolean isImprovable(Player player, Tower tower, TowerPlayer towerPlayer, TowerWarsTeam towerWarsTeam, Location location, WorldServer world, String name, int level, int price, int previousPrice) {
        if (tower.getName().equals(name)) {
            if (tower.isLevel(level)) {
                if (towerPlayer.getTowerWarsTeam().getCoins() >= price - previousPrice) {
                    player.closeInventory();
                    towerWarsTeam.removeTower(tower, location);
                    towerPlayer.getTowerWarsTeam().removeCoins(price - previousPrice);
                    Tower newTower = null;
                    if (name.equals("Archer")) {
                        newTower = moduleManager.getTowerEngineer().createArcherTower(moduleManager, world, tower.getLocation(), level + 1, player, price);
                    } else if (name.equals("Slime")) {
                        newTower = moduleManager.getTowerEngineer().createSlimeTower(moduleManager, world, tower.getLocation(), level + 1, player, price);
                    }
                    towerWarsTeam.addPlacedTower(location, newTower);
                    String levelText = "";
                    if (level == 1) levelText = "II";
                    if (level == 2) levelText = "III";
                    player.sendMessage(TowerWars.PREFIX + "Der §eTurm §7wurde verbessert §8[§e" + levelText + "§8]");
                    return true;
                } else {
                    player.sendMessage(TowerWars.PREFIX + "Du hast nicht genug §eGold§8!");
                }
            }
        }
        return false;
    }
}