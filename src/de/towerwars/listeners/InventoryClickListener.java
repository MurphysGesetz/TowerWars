package de.towerwars.listeners;

import de.towerwars.TowerWars;
import de.towerwars.game.gamestate.gamestates.LobbyState;
import de.towerwars.game.team.TowerWarsTeam;
import de.towerwars.game.team.TowerWarsTeamHelper;
import de.towerwars.game.tower.Tower;
import de.towerwars.managers.ModuleManager;
import de.towerwars.player.TowerPlayer;
import net.minecraft.server.v1_8_R3.WorldServer;
import org.bukkit.GameMode;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryClickListener implements Listener {

    private final ModuleManager moduleManager;

    public InventoryClickListener(ModuleManager moduleManager) {
        this.moduleManager = moduleManager;
    }

    @EventHandler
    public void handleSummonMonsterClick(InventoryClickEvent event) {
        final Player player = (Player) event.getWhoClicked();
        if(event.getClickedInventory() == null  || event.getCurrentItem() == null || player.getGameMode().equals(GameMode.CREATIVE)) return;
        if(event.getClickedInventory().getTitle().equals("§6▰§e▰ Monster")) {
            final TowerPlayer towerPlayer = moduleManager.getTowerPlayerHelper().getTowerPlayer(player);
            if(towerPlayer.getMobSpawnAmount() == 0) return;
            switch (event.getCurrentItem().getItemMeta().getDisplayName()) {
                case "§eSilberfisch":
                    if(towerPlayer.getTowerWarsTeam().getCoins() >= 5) {
                        towerPlayer.removeMobSpawnAmount();
                        towerPlayer.getTowerWarsTeam().removeCoins(5);
                        moduleManager.getTowerWarsTeamHelper().summonMonster(towerPlayer.getTowerWarsTeam(), "silverfish");
                        towerPlayer.getTowerWarsTeam().addIncome(1);
                    } else {
                        player.sendMessage(TowerWars.PREFIX + "Du hast nicht genug §eGold§8!");
                    }
                    break;
                case "§eHuhn":
                    if(towerPlayer.getTowerWarsTeam().getCoins() >= 10) {
                        towerPlayer.removeMobSpawnAmount();
                        towerPlayer.getTowerWarsTeam().removeCoins(10);
                        moduleManager.getTowerWarsTeamHelper().summonMonster(towerPlayer.getTowerWarsTeam(), "chicken");
                        towerPlayer.getTowerWarsTeam().addIncome(2);
                    } else {
                        player.sendMessage(TowerWars.PREFIX + "Du hast nicht genug §eGold§8!");
                    }
                    break;
                case "§eSchaf":
                    if(towerPlayer.getTowerWarsTeam().getCoins() >= 22) {
                        towerPlayer.removeMobSpawnAmount();
                        towerPlayer.getTowerWarsTeam().removeCoins(22);
                        moduleManager.getTowerWarsTeamHelper().summonMonster(towerPlayer.getTowerWarsTeam(), "sheep");
                        towerPlayer.getTowerWarsTeam().addIncome(4);
                    } else {
                        player.sendMessage(TowerWars.PREFIX + "Du hast nicht genug §eGold§8!");
                    }
                    break;
                case "§eHöhlenspinne":
                    if(towerPlayer.getTowerWarsTeam().getCoins() >= 50) {
                        towerPlayer.removeMobSpawnAmount();
                        towerPlayer.getTowerWarsTeam().removeCoins(50);
                        moduleManager.getTowerWarsTeamHelper().summonMonster(towerPlayer.getTowerWarsTeam(), "cavespider");
                        towerPlayer.getTowerWarsTeam().addIncome(8);
                    } else {
                        player.sendMessage(TowerWars.PREFIX + "Du hast nicht genug §eGold§8!");
                    }
                    break;
                case "§eZombie":
                    if(towerPlayer.getTowerWarsTeam().getCoins() >= 75) {
                        towerPlayer.removeMobSpawnAmount();
                        towerPlayer.getTowerWarsTeam().removeCoins(75);
                        moduleManager.getTowerWarsTeamHelper().summonMonster(towerPlayer.getTowerWarsTeam(), "zombie");
                        towerPlayer.getTowerWarsTeam().addIncome(12);
                    } else {
                        player.sendMessage(TowerWars.PREFIX + "Du hast nicht genug §eGold§8!");
                    }
                    break;
                case "§eWolf":
                    if(towerPlayer.getTowerWarsTeam().getCoins() >= 100) {
                        towerPlayer.removeMobSpawnAmount();
                        towerPlayer.getTowerWarsTeam().removeCoins(100);
                        moduleManager.getTowerWarsTeamHelper().summonMonster(towerPlayer.getTowerWarsTeam(), "wolf");
                        towerPlayer.getTowerWarsTeam().addIncome(14);
                    } else {
                        player.sendMessage(TowerWars.PREFIX + "Du hast nicht genug §eGold§8!");
                    }
                    break;
                case "§eSpinne":
                    if(towerPlayer.getTowerWarsTeam().getCoins() >= 200) {
                        towerPlayer.removeMobSpawnAmount();
                        towerPlayer.getTowerWarsTeam().removeCoins(200);
                        moduleManager.getTowerWarsTeamHelper().summonMonster(towerPlayer.getTowerWarsTeam(), "spider");
                        towerPlayer.getTowerWarsTeam().addIncome(22);
                    } else {
                        player.sendMessage(TowerWars.PREFIX + "Du hast nicht genug §eGold§8!");
                    }
                    break;
            }
        }
    }

    @EventHandler
    public void handleTowerBuyClick(InventoryClickEvent event) {
        final Player player = (Player)event.getWhoClicked();
        if(event.getClickedInventory() == null  || event.getCurrentItem() == null || player.getGameMode().equals(GameMode.CREATIVE)) return;
        if(event.getClickedInventory().getTitle().equals("§6▰§e▰ Türme")) {
            final TowerPlayer towerPlayer = moduleManager.getTowerPlayerHelper().getTowerPlayer(player);
            final TowerWarsTeam towerWarsTeam = towerPlayer.getTowerWarsTeam();
            final WorldServer world = ((CraftWorld) player.getWorld()).getHandle();
            if((towerWarsTeam.getPlacedTower().size() + 1) == 61) {
                player.sendMessage(TowerWars.PREFIX + "Du hast die maximale Anzahl an Türmen platziert!");
                return;
            }
            if ("§eBogenschütze §8▰ §6I".equals(event.getCurrentItem().getItemMeta().getDisplayName())) {
                if (towerWarsTeam.getCoins() >= 10) {
                    towerWarsTeam.removeCoins(10);
                    player.sendMessage(TowerWars.PREFIX + "Der §aBogenschütze §7wurde platziert (" + (towerWarsTeam.getPlacedTower().size() + 1) + "/60)");
                    player.closeInventory();
                    Tower tower = moduleManager.getTowerEngineer().createArcherTower(moduleManager, world, towerPlayer.getTowerLocation(), 1, player, 10);
                    towerWarsTeam.addPlacedTower(towerPlayer.getTowerLocation(), tower);
                } else {
                    player.sendMessage(TowerWars.PREFIX + "Du hast nicht genug §eGold");
                }
            } else if ("§eBogenschütze §8▰ §6II".equals(event.getCurrentItem().getItemMeta().getDisplayName())) {
                if (towerWarsTeam.getCoins() >= 90) {
                    towerWarsTeam.removeCoins(90);
                    player.sendMessage(TowerWars.PREFIX + "Der §aBogenschütze §7wurde platziert (" + (towerWarsTeam.getPlacedTower().size() + 1) + "/60)");
                    player.closeInventory();
                    Tower tower = moduleManager.getTowerEngineer().createArcherTower(moduleManager, world, towerPlayer.getTowerLocation(), 2, player, 90);
                    towerWarsTeam.addPlacedTower(towerPlayer.getTowerLocation(), tower);
                } else {
                    player.sendMessage(TowerWars.PREFIX + "Du hast nicht genug §eGold");
                }
            } else if ("§eBogenschütze §8▰ §6III".equals(event.getCurrentItem().getItemMeta().getDisplayName())) {
                if (towerWarsTeam.getCoins() >= 1340) {
                    towerWarsTeam.removeCoins(1340);
                    player.sendMessage(TowerWars.PREFIX + "Der §aBogenschütze §7wurde platziert (" + (towerWarsTeam.getPlacedTower().size() + 1) + "/60)");
                    player.closeInventory();
                    Tower tower = moduleManager.getTowerEngineer().createArcherTower(moduleManager, world, towerPlayer.getTowerLocation(), 3, player, 1340);
                    towerWarsTeam.addPlacedTower(towerPlayer.getTowerLocation(), tower);
                } else {
                    player.sendMessage(TowerWars.PREFIX + "Du hast nicht genug §eGold");
                }
            } else if ("§eSchleim §8▰ §6I".equals(event.getCurrentItem().getItemMeta().getDisplayName())) {
                if (towerWarsTeam.getCoins() >= 50) {
                    towerWarsTeam.removeCoins(50);
                    player.sendMessage(TowerWars.PREFIX + "Der §aSchleim §7wurde platziert (" + (towerWarsTeam.getPlacedTower().size() + 1) + "/60)");
                    player.closeInventory();
                    Tower tower = moduleManager.getTowerEngineer().createSlimeTower(moduleManager, world, towerPlayer.getTowerLocation(), 1, player, 50);
                    towerWarsTeam.addPlacedTower(towerPlayer.getTowerLocation(), tower);
                } else {
                    player.sendMessage(TowerWars.PREFIX + "Du hast nicht genug §eGold");
                }
            } else if ("§eSchleim §8▰ §6II".equals(event.getCurrentItem().getItemMeta().getDisplayName())) {
                if (towerWarsTeam.getCoins() >= 650) {
                    towerWarsTeam.removeCoins(650);
                    player.sendMessage(TowerWars.PREFIX + "Der §aSchleim §7wurde platziert (" + (towerWarsTeam.getPlacedTower().size() + 1) + "/60)");
                    player.closeInventory();
                    Tower tower = moduleManager.getTowerEngineer().createSlimeTower(moduleManager, world, towerPlayer.getTowerLocation(), 2, player, 650);
                    towerWarsTeam.addPlacedTower(towerPlayer.getTowerLocation(), tower);
                } else {
                    player.sendMessage(TowerWars.PREFIX + "Du hast nicht genug §eGold");
                }
            } else if ("§eSchleim §8▰ §6III".equals(event.getCurrentItem().getItemMeta().getDisplayName())) {
                if (towerWarsTeam.getCoins() >= 8650) {
                    towerWarsTeam.removeCoins(8650);
                    player.sendMessage(TowerWars.PREFIX + "Der §aSchleim §7wurde platziert (" + (towerWarsTeam.getPlacedTower().size() + 1) + "/60)");
                    player.closeInventory();
                    Tower tower = moduleManager.getTowerEngineer().createSlimeTower(moduleManager, world, towerPlayer.getTowerLocation(), 3, player, 8650);
                    towerWarsTeam.addPlacedTower(towerPlayer.getTowerLocation(), tower);
                } else {
                    player.sendMessage(TowerWars.PREFIX + "Du hast nicht genug §eGold");
                }
            }
        } else if(event.getClickedInventory().getTitle().equals("§6▰§e▰ Teleportieren")) {
            if(event.getCurrentItem().getItemMeta().getDisplayName().startsWith("§9")) {
                player.teleport(moduleManager.getTowerWarsTeamHelper().getTowerWarsTeams()[TowerWarsTeamHelper.BLUE].getSpawnLocation());
            } else if(event.getCurrentItem().getItemMeta().getDisplayName().startsWith("§c")) {
                player.teleport(moduleManager.getTowerWarsTeamHelper().getTowerWarsTeams()[TowerWarsTeamHelper.RED].getSpawnLocation());
            } else if(event.getCurrentItem().getItemMeta().getDisplayName().startsWith("§2")) {
                player.teleport(moduleManager.getTowerWarsTeamHelper().getTowerWarsTeams()[TowerWarsTeamHelper.GREEN].getSpawnLocation());
            } else if(event.getCurrentItem().getItemMeta().getDisplayName().startsWith("§e")) {
                player.teleport(moduleManager.getTowerWarsTeamHelper().getTowerWarsTeams()[TowerWarsTeamHelper.YELLOW].getSpawnLocation());
            } else if(event.getCurrentItem().getItemMeta().getDisplayName().startsWith("§6")) {
                player.teleport(moduleManager.getTowerWarsTeamHelper().getTowerWarsTeams()[TowerWarsTeamHelper.ORANGE].getSpawnLocation());
            } else if(event.getCurrentItem().getItemMeta().getDisplayName().startsWith("§b")) {
                player.teleport(moduleManager.getTowerWarsTeamHelper().getTowerWarsTeams()[TowerWarsTeamHelper.AQUA].getSpawnLocation());
            }
        }
    }

    @EventHandler
    public void handleInventoryClick(InventoryClickEvent event) {
        final Player player = (Player)event.getWhoClicked();
        if(event.getClickedInventory() == null  || event.getCurrentItem() == null || player.getGameMode().equals(GameMode.CREATIVE)) return;
        event.setCancelled(true);
        if (moduleManager.getGameStateHelper().getCurrentGameState() == null) return;
        if (!(moduleManager.getGameStateHelper().getCurrentGameState() instanceof LobbyState)) return;
        if(event.getClickedInventory() == null || event.getCurrentItem() == null) return;
        if(event.getClickedInventory().getTitle().equals("§6▰§e▰ Kompetenzen")) {
            final TowerPlayer towerPlayer = moduleManager.getTowerPlayerHelper().getTowerPlayer(player);
            switch (event.getCurrentItem().getItemMeta().getDisplayName()) {
                case "§5▰§d▰ Wutzauber":
                    towerPlayer.setCurrentSkill(0);
                    moduleManager.getMySqlTableHelper().setSkill(player.getUniqueId().toString(), 0);
                    towerPlayer.updateSkillsInventory();
                    player.openInventory(towerPlayer.getSkillsInventory());
                    break;
                case "§5▰§d▰ 1 Leben":
                    towerPlayer.setCurrentSkill(1);
                    moduleManager.getMySqlTableHelper().setSkill(player.getUniqueId().toString(), 1);
                    towerPlayer.updateSkillsInventory();
                    player.openInventory(towerPlayer.getSkillsInventory());
                    break;
                case "§5▰§d▰ 50% Rabatt":
                    towerPlayer.setCurrentSkill(2);
                    moduleManager.getMySqlTableHelper().setSkill(player.getUniqueId().toString(), 2);
                    towerPlayer.updateSkillsInventory();
                    player.openInventory(towerPlayer.getSkillsInventory());
                    break;
                case "§5▰§d▰ Atmosphäre":
                    towerPlayer.setCurrentSkill(3);
                    moduleManager.getMySqlTableHelper().setSkill(player.getUniqueId().toString(), 3);
                    towerPlayer.updateSkillsInventory();
                    player.openInventory(towerPlayer.getSkillsInventory());
                    break;
                case "§5▰§d▰ Geld x 2":
                    towerPlayer.setCurrentSkill(4);
                    moduleManager.getMySqlTableHelper().setSkill(player.getUniqueId().toString(), 4);
                    towerPlayer.updateSkillsInventory();
                    player.openInventory(towerPlayer.getSkillsInventory());
                    break;
                case "§4▰§c▰ Zurück":
                    player.closeInventory();
                    break;
            }
        }
    }
}