package de.towerwars.util;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

public class InventoryLoader {

    private final Inventory lobbyInventory;
    private final Inventory lobbyPremiumInventory;
    private final Inventory skillsInventory;
    private final Inventory gameInventory;
    private final Inventory towerInventory;
    private final Inventory teleporterInventory;
    private final ItemStack[] skillItemStacks;

    public InventoryLoader() {
        this.lobbyInventory = loadLobbyInventory();
        this.lobbyPremiumInventory = loadLobbyPremiumInventory();
        this.skillsInventory = loadSkillsInventory();
        this.gameInventory = loadGameInventory();
        this.towerInventory = loadTowerInventory();
        this.skillItemStacks = loadSkillItemStacks();
        this.teleporterInventory = loadTeleporterInventory();
    }

    private Inventory loadTeleporterInventory() {
        Inventory inventory = Bukkit.createInventory(null, 27, "§6▰§e▰ Teleportieren");
        ItemStack glassItem = new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 7).setName("§0").build();
        for(int i = 0; i < 27; i++) {
            inventory.setItem(i, glassItem);
        }
        inventory.setItem(10, new ItemBuilder(Material.SIGN).setName("§7Teams §8»").build());
        inventory.setItem(11, new ItemBuilder(Material.WOOL, 1, (short) 11).setName("§9Blau").build());
        inventory.setItem(12, new ItemBuilder(Material.WOOL, 1, (short) 14).setName("§cRot").build());
        inventory.setItem(13, new ItemBuilder(Material.WOOL, 1, (short) 5).setName("§2Grün").build());
        inventory.setItem(14, new ItemBuilder(Material.WOOL, 1, (short) 4).setName("§eGelb").build());
        inventory.setItem(15, new ItemBuilder(Material.WOOL, 1, (short) 1).setName("§6Orange").build());
        inventory.setItem(16, new ItemBuilder(Material.WOOL, 1, (short) 9).setName("§bAqua").build());
        return inventory;
    }

    private ItemStack[] loadSkillItemStacks() {
        return new ItemStack[]{
                new ItemBuilder(Material.LEATHER).setName("§e▰ §6Wutzauber §e▰").addEnchantment(Enchantment.LUCK).addFlag(ItemFlag.HIDE_ENCHANTS).build(),
                new ItemBuilder(Material.INK_SACK, 1, (short) 1).setName("§e▰ §61 Leben §e▰").addEnchantment(Enchantment.LUCK).addFlag(ItemFlag.HIDE_ENCHANTS).build(),
                new ItemBuilder(Material.PAPER).setName("§e▰ §650% Rabatt §e▰").addEnchantment(Enchantment.LUCK).addFlag(ItemFlag.HIDE_ENCHANTS).build(),
                new ItemBuilder(Material.EYE_OF_ENDER).setName("§e▰ §6Atmosphäre §e▰").addEnchantment(Enchantment.LUCK).addFlag(ItemFlag.HIDE_ENCHANTS).build(),
                new ItemBuilder(Material.GOLD_INGOT).setName("§e▰ §6Geld x 2 §e▰").addEnchantment(Enchantment.LUCK).addFlag(ItemFlag.HIDE_ENCHANTS).build()
        };
    }

    private Inventory loadTowerInventory() {
        Inventory inventory = Bukkit.createInventory(null, 54, "§6▰§e▰ Türme");
        inventory.setItem(0, new ItemBuilder(Material.BOW).setName("§eBogenschütze §8▰ §6I").setLore("", "§7Schaden §8▰ §e15.0 §6Herzen", "§7Nachladen §8▰ §e2 §6Sekunden", "§7Reichweite §8▰ §e8.0 §6Blöcke", "", "§7Preis §8▰ §e10 §6Gold").build());
        inventory.setItem(9, new ItemBuilder(Material.BOW, 2).setName("§eBogenschütze §8▰ §6II").setLore("", "§7Schaden §8▰ §e50.0 §6Herzen", "§7Nachladen §8▰ §e1.75 §6Sekunden", "§7Reichweite §8▰ §e9.0 §6Blöcke", "", "§7Preis §8▰ §e90 §6Gold").build());
        inventory.setItem(18, new ItemBuilder(Material.BOW, 3).setName("§eBogenschütze §8▰ §6III").setLore("", "§7Schaden §8▰ §e250.0 §6Herzen", "§7Nachladen §8▰ §e1.5 §6Sekunden", "§7Reichweite §8▰ §e10.0 §6Blöcke", "", "§7Preis §8▰ §e1.340 §6Gold").build());

        inventory.setItem(1, new ItemBuilder(Material.SLIME_BLOCK).setName("§eSchleim §8▰ §6I").setLore("", "§7Schaden §8▰ §e6.0 §6Herzen", "§7Nachladen §8▰ §e1.5 §6Sekunden", "§7Reichweite §8▰ §e6.0 §6Blöcke", "", "§7Langsamkeit §8▰ §e25.0%", "§7Abklingzeit §8▰ §e1.0 §6Sekunden", "§7Flächenschaden §8▰ §e2 §6Blöcke", "", "§7Preis §8▰ §e50 §6Gold").build());
        inventory.setItem(10, new ItemBuilder(Material.SLIME_BLOCK, 2).setName("§eSchleim §8▰ §6II").setLore("", "§7Schaden §8▰ §e25.0 §6Herzen", "§7Nachladen §8▰ §e1.25 §6Sekunden", "§7Reichweite §8▰ §e7.0 §6Blöcke", "", "§7Langsamkeit §8▰ §e30.0%", "§7Abklingzeit §8▰ §e1.5 §6Sekunden", "§7Flächenschaden §8▰ §e2 §6Blöcke",  "", "§7Preis §8▰ §e650 §6Gold").build());
        inventory.setItem(19, new ItemBuilder(Material.SLIME_BLOCK, 3).setName("§eSchleim §8▰ §6III").setLore("", "§7Schaden §8▰ §e150.0 §6Herzen", "§7Nachladen §8▰ §e1.0 §6Sekunden", "§7Reichweite §8▰ §e7.0 §6Blöcke", "", "§7Langsamkeit §8▰ §e40.0%", "§7Abklingzeit §8▰ §e2 §6Sekunden", "§7Flächenschaden §8▰ §e2 §6Blöcke",  "", "§7Preis §8▰ §e8.650 §6Gold").build());
        return inventory;
    }

    private Inventory loadGameInventory() {
        Inventory inventory = Bukkit.createInventory(null, 27);
        inventory.setItem(1, new ItemBuilder(Material.ARMOR_STAND).setName("§eKlicke, um einen Turm zu platzieren!").build());
        inventory.setItem(2, new ItemBuilder(Material.BOOK).setName("§eBeschwöre Monster!").build());
        inventory.setItem(3, new ItemBuilder(Material.BEACON).setName("§eKlicke, um dein Turm zu verbessern!").build());
        inventory.setItem(8, new ItemBuilder(Material.COMPASS).setName("§eZu Teams teleportieren!").build());
        return inventory;
    }

    private Inventory loadSkillsInventory() {
        Inventory inventory = Bukkit.createInventory(null, 27, "§6▰§e▰ Kompetenzen");
        ItemStack glassItem = new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 7).setName("§0").build();
        for(int i = 0; i < 27; i++) {
            inventory.setItem(i, glassItem);
        }
        inventory.setItem(11, new ItemBuilder(Material.LEATHER).setName("§5▰§d▰ Wutzauber").setLore("§7Mache deine §eVerteidigung", "§7wütend/aggressiv§8!", "", "§7Wartezeit: §e60 Sekunden", "", "§6Klicke zum auszuwählen!").build());
        inventory.setItem(12, new ItemBuilder(Material.INK_SACK, 1, (short) 1).setName("§5▰§d▰ 1 Leben").setLore("§7Dezimiere die Leben", "§7der Monster auf §6§l1§4❤§8!", "", "§7Wartezeit: §e90 Sekunden", "", "§6Klicke zum auszuwählen!").build());
        inventory.setItem(13, new ItemBuilder(Material.PAPER).setName("§5▰§d▰ 50% Rabatt").setLore("§7Gibt dir §e50% Rabatt", "§7auf dein Einkauf§8!", "", "§7Wartezeit: §e120 Sekunden", "", "§6Klicke zum auszuwählen!").build());
        inventory.setItem(14, new ItemBuilder(Material.ENDER_PORTAL_FRAME).setName("§5▰§d▰ Atmosphäre").setLore("§7Erstelle ein Portal, das", "§7bis zu §e5 Monster §7zu", "§7anderen Plots §eteleportiert§8!", "", "§7Wartezeit: §e60 Sekunden", "", "§6Klicke zum auszuwählen!").build());
        inventory.setItem(15, new ItemBuilder(Material.GOLD_INGOT).setName("§5▰§d▰ Geld x 2").setLore("§7Verdoppelt für §e10 Sekunden", "§7dein Einkommen§8!", "", "§7Wartezeit: §e120 Sekunden", "", "§6Klicke zum auszuwählen!").build());
        inventory.setItem(18, new ItemBuilder(Material.REDSTONE).setName("§4▰§c▰ Zurück").build());
        return inventory;
    }

    private Inventory loadLobbyInventory() {
        Inventory inventory = Bukkit.createInventory(null, 27);
        inventory.setItem(0, new ItemBuilder(Material.ENDER_CHEST).setName("§6▰§e▰ Kompetenzen").build());
        inventory.setItem(4, new ItemBuilder(Material.NETHER_STAR).setName("§5▰§d▰ Statistiken").build());
        inventory.setItem(8, new ItemBuilder(Material.SKULL_ITEM, 1, (short) 3).setName("§4▰§c▰ Verlassen").setSkullTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjBiZmMyNTc3ZjZlMjZjNmM2ZjczNjVjMmM0MDc2YmNjZWU2NTMxMjQ5ODkzODJjZTkzYmNhNGZjOWUzOWIifX19").build());
        return inventory;
    }

    private Inventory loadLobbyPremiumInventory() {
        Inventory inventory = Bukkit.createInventory(null, 27);
        inventory.setItem(0, new ItemBuilder(Material.REDSTONE_TORCH_ON).setName("§3▰§b▰ Start").build());
        inventory.setItem(3, new ItemBuilder(Material.ENDER_CHEST).setName("§6▰§e▰ Kompetenzen").build());
        inventory.setItem(5, new ItemBuilder(Material.NETHER_STAR).setName("§5▰§d▰ Statistiken").build());
        inventory.setItem(8, new ItemBuilder(Material.SKULL_ITEM, 1, (short) 3).setName("§4▰§c▰ Verlassen").setSkullTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjBiZmMyNTc3ZjZlMjZjNmM2ZjczNjVjMmM0MDc2YmNjZWU2NTMxMjQ5ODkzODJjZTkzYmNhNGZjOWUzOWIifX19").build());
        return inventory;
    }

    public Inventory getTeleporterInventory() {
        return teleporterInventory;
    }

    public ItemStack[] getSkillItemStacks() {
        return skillItemStacks;
    }

    public Inventory getTowerInventory() {
        return towerInventory;
    }

    public Inventory getLobbyInventory() {
        return lobbyInventory;
    }

    public Inventory getLobbyPremiumInventory() {
        return lobbyPremiumInventory;
    }

    public Inventory getSkillsInventory() {
        return skillsInventory;
    }

    public Inventory getGameInventory() {
        return gameInventory;
    }
}
