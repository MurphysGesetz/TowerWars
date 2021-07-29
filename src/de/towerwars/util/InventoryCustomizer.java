package de.towerwars.util;

import de.towerwars.game.team.TowerWarsTeam;
import de.towerwars.game.tower.Tower;
import de.towerwars.managers.ModuleManager;
import de.towerwars.player.TowerPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;

public class InventoryCustomizer {

    private final ModuleManager moduleManager;

    public InventoryCustomizer(ModuleManager moduleManager) {
        this.moduleManager = moduleManager;
    }

    public void customizeTowerMenuInventory(Player player) {
        final Inventory inventory = Bukkit.createInventory(null, 27, "");
        final TowerPlayer towerPlayer = moduleManager.getTowerPlayerHelper().getTowerPlayer(player);
        final TowerWarsTeam towerWarsTeam = towerPlayer.getTowerWarsTeam();
        final Tower tower = towerWarsTeam.getPlacedTower().get(towerWarsTeam.getArmorStand().getLocation());

        inventory.setItem(17, new ItemBuilder(Material.TNT).setName("§4▰ §cVerkaufen").setLore("", "§7Rückerstattung §e" +  (tower.getPrice() / 2) + " Gold").build());
        inventory.setItem(13, new ItemBuilder(Material.PAPER).setName("§7▰ §fInfo").setLore("", "§7Level: §e" + tower.getLevel()).build());
        inventory.setItem(9, new ItemBuilder(Material.BEACON).setName("§2▰ §aVerbessern").build());
        towerPlayer.getTowerMenuInventory().setContents(inventory.getContents());
    }

    public void customizeMonsterInventory(Player player) {
        final Inventory inventory = Bukkit.createInventory(null, 27, "");
        final TowerPlayer towerPlayer = moduleManager.getTowerPlayerHelper().getTowerPlayer(player);
        final int itemAmount = towerPlayer.getMobSpawnAmount();

        inventory.setItem(0, new ItemBuilder(Material.STONE, itemAmount).setName("§eSilberfisch").setLore("", "§7Preis §8▰ §e5 §6Gold", "§7Lohn §8▰ §a+§e1", "", "§7Leben §8▰ §e5 Herzen", "§7Geschwindigkeit §8▰ §e1.0").build());
        inventory.setItem(1, new ItemBuilder(Material.EGG, itemAmount).setName("§eHuhn").setLore("", "§7Preis §8▰ §e10 §6Gold", "§7Lohn §8▰ §a+§e2", "", "§7Leben §8▰ §e40 Herzen", "§7Geschwindigkeit §8▰ §e1.2").build());
        inventory.setItem(2, new ItemBuilder(Material.WOOL, itemAmount).setName("§eSchaf").setLore("", "§7Preis §8▰ §e22 §6Gold", "§7Lohn §8▰ §a+§e4", "", "§7Leben §8▰ §e88 Herzen", "§7Geschwindigkeit §8▰ §e1.1").build());
        inventory.setItem(3, new ItemBuilder(Material.SPIDER_EYE, itemAmount).setName("§eHöhlenspinne").setLore("", "§7Preis §8▰ §e50 §6Gold", "§7Lohn §8▰ §a+§e8", "", "§7Leben §8▰ §e200 Herzen", "§7Geschwindigkeit §8▰ §e1.3").build());
        inventory.setItem(4, new ItemBuilder(Material.ROTTEN_FLESH, itemAmount).setName("§eZombie").setLore("", "§7Preis §8▰ §e75 §6Gold", "§7Lohn §8▰ §a+§e12", "", "§7Leben §8▰ §e300 Herzen", "§7Geschwindigkeit §8▰ §e1.2", "", "§d§lBesonderheit", "§7Beschwört Babyzombies beim Tod").build());
        inventory.setItem(5, new ItemBuilder(Material.BONE, itemAmount).setName("§eWolf").setLore("", "§7Preis §8▰ §e100 §6Gold", "§7Lohn §8▰ §a+§e14", "", "§7Leben §8▰ §e400 Herzen", "§7Geschwindigkeit §8▰ §e1.4", "", "§d§lBesonderheit", "§733% Wahrscheinlichkeit,", "§7Flächenschaden auszuweichen").build());
        inventory.setItem(6, new ItemBuilder(Material.WEB, itemAmount).setName("§eSpinne").setLore("", "§7Preis §8▰ §e200 §6Gold", "§7Lohn §8▰ §a+§e22", "", "§7Leben §8▰ §e700 Herzen", "§7Geschwindigkeit §8▰ §e1.4", "", "§d§lBesonderheit", "§7Stoppt Türme", "§7mit Netz").build());
        towerPlayer.getMonsterInventory().setContents(inventory.getContents());
    }

    public void customizeTeleporterInventory(Player player) {
        final Inventory inventory = Bukkit.createInventory(null, 27, "");
        final TowerPlayer towerPlayer = moduleManager.getTowerPlayerHelper().getTowerPlayer(player);

        inventory.setContents(moduleManager.getInventoryLoader().getTeleporterInventory().getContents());
        switch (towerPlayer.getTowerWarsTeam().getTeamName()) {
            case "§9Blau§7":
                inventory.setItem(11, new ItemBuilder(Material.WOOL, 1, (short) 11).setName("§9Blau §7(Dein Team)").addEnchantment(Enchantment.LUCK).addFlag(ItemFlag.HIDE_ENCHANTS).build());
                break;
            case "§cRot§7":
                inventory.setItem(12, new ItemBuilder(Material.WOOL, 1, (short) 14).setName("§cRot §7(Dein Team)").addEnchantment(Enchantment.LUCK).addFlag(ItemFlag.HIDE_ENCHANTS).build());
                break;
            case "§2Grün§7":
                inventory.setItem(13, new ItemBuilder(Material.WOOL, 1, (short) 5).setName("§2Grün §7(Dein Team)").addEnchantment(Enchantment.LUCK).addFlag(ItemFlag.HIDE_ENCHANTS).build());
                break;
            case "§bAqua§7":
                inventory.setItem(16, new ItemBuilder(Material.WOOL, 1, (short) 9).setName("§bAqua §7(Dein Team)").addEnchantment(Enchantment.LUCK).addFlag(ItemFlag.HIDE_ENCHANTS).build());
                break;
            case "§6Orange§7":
                inventory.setItem(15, new ItemBuilder(Material.WOOL, 1, (short) 1).setName("§6Orange §7(Dein Team)").addEnchantment(Enchantment.LUCK).addFlag(ItemFlag.HIDE_ENCHANTS).build());
                break;
            case "§eGelb§7":
                inventory.setItem(14, new ItemBuilder(Material.WOOL, 1, (short) 4).setName("§eGelb §7(Dein Team)").addEnchantment(Enchantment.LUCK).addFlag(ItemFlag.HIDE_ENCHANTS).build());
                break;
        }
        towerPlayer.getTeleporterInventory().setContents(inventory.getContents());
    }

    public void customizeSkillsInventory(Player player) {
        final Inventory inventory = Bukkit.createInventory(null, 27, "");
        final TowerPlayer towerPlayer = moduleManager.getTowerPlayerHelper().getTowerPlayer(player);

        inventory.setContents(moduleManager.getInventoryLoader().getSkillsInventory().getContents());
        switch (towerPlayer.getCurrentSkill()) {
            case 0:
                inventory.setItem(11, new ItemBuilder(Material.LEATHER).setName("§5▰§d▰ Wutzauber").setLore("§7Mache deine §eVerteidigung", "§7wütend/aggressiv§8!", "", "§7Wartezeit: §e60 Sekunden", "", "§aAusgewählt!").addEnchantment(Enchantment.LUCK).addFlag(ItemFlag.HIDE_ENCHANTS).build());
                break;
            case 1:
                inventory.setItem(12, new ItemBuilder(Material.INK_SACK, 1, (short) 1).setName("§5▰§d▰ 1 Leben").setLore("§7Dezimiere die Leben", "§7der Monster auf §6§l1§4❤§8!", "", "§7Wartezeit: §e90 Sekunden", "", "§aAusgewählt!").addEnchantment(Enchantment.LUCK).addFlag(ItemFlag.HIDE_ENCHANTS).build());
                break;
            case 2:
                inventory.setItem(13, new ItemBuilder(Material.PAPER).setName("§5▰§d▰ 50% Rabatt").setLore("§7Gibt dir §e50% Rabatt", "§7auf dein Einkauf§8!", "", "§7Wartezeit: §e120 Sekunden", "", "§aAusgewählt!").addEnchantment(Enchantment.LUCK).addFlag(ItemFlag.HIDE_ENCHANTS).build());
                break;
            case 3:
                inventory.setItem(14, new ItemBuilder(Material.ENDER_PORTAL_FRAME).setName("§5▰§d▰ Atmosphäre").setLore("§7Erstelle ein Portal, das", "§7bis zu §e5 Monster §7zu", "§7anderen Plots §eteleportiert§8!", "", "§7Wartezeit: §e60 Sekunden", "", "§aAusgewählt!").addEnchantment(Enchantment.LUCK).addFlag(ItemFlag.HIDE_ENCHANTS).build());
                break;
            case 4:
                inventory.setItem(15, new ItemBuilder(Material.GOLD_INGOT).setName("§5▰§d▰ Geld x 2").setLore("§7Verdoppelt für §e10 Sekunden", "§7dein Einkommen§8!", "", "§7Wartezeit: §e120 Sekunden", "", "§aAusgewählt!").addEnchantment(Enchantment.LUCK).addFlag(ItemFlag.HIDE_ENCHANTS).build());
                break;
        }
        towerPlayer.getSkillsInventory().setContents(inventory.getContents());
    }
}
