package de.towerwars.managers;

import de.towerwars.TowerWars;
import de.towerwars.game.gamestate.GameStateHelper;
import de.towerwars.game.team.TowerWarsTeamHelper;
import de.towerwars.game.tower.TowerEngineer;
import de.towerwars.mysql.MySqlConnection;
import de.towerwars.mysql.MySqlTableHelper;
import de.towerwars.player.TowerPlayerHelper;
import de.towerwars.scoreboard.ScoreboardHelper;
import de.towerwars.util.*;

public class ModuleManager {

    private final TowerWars plugin;
    private final Titles titles;
    private final TowerEngineer towerEngineer;
    public MySqlConnection mySqlConnection;
    private final GameStateHelper gameStateHelper;
    private final InventoryLoader inventoryLoader;
    private final ArmorStandHelper armorStandHelper;
    private final ScoreboardHelper scoreboardHelper;
    public MySqlTableHelper mySqlTableHelper;
    private final TowerPlayerHelper towerPlayerHelper;
    private final InventoryCustomizer inventoryCustomizer;
    public TowerWarsTeamHelper towerWarsTeamHelper;
    private final CustomEntityRegister customEntityRegister;
    private final MessageFormatter messageFormatter;

    public ModuleManager(TowerWars plugin) {
        this.plugin = plugin;
        this.titles = new Titles();
        this.inventoryLoader = new InventoryLoader();
        this.towerEngineer = new TowerEngineer();
        this.messageFormatter = new MessageFormatter();
        this.customEntityRegister = new CustomEntityRegister();
        this.gameStateHelper = new GameStateHelper(this);
        this.armorStandHelper = new ArmorStandHelper(this);
        this.scoreboardHelper = new ScoreboardHelper(this);
        this.towerPlayerHelper = new TowerPlayerHelper(this);
        this.inventoryCustomizer = new InventoryCustomizer(this);
    }

    public MessageFormatter getMessageFormatter() {
        return messageFormatter;
    }

    public TowerWars getPlugin() {
        return plugin;
    }

    public ArmorStandHelper getArmorStandHelper() {
        return armorStandHelper;
    }

    public MySqlConnection getMySqlConnection() {
        return mySqlConnection;
    }

    public MySqlTableHelper getMySqlTableHelper() {
        return mySqlTableHelper;
    }

    public TowerEngineer getTowerEngineer() {
        return towerEngineer;
    }

    public CustomEntityRegister getCustomEntityRegister() {
        return customEntityRegister;
    }

    public ScoreboardHelper getScoreboardHelper() {
        return scoreboardHelper;
    }

    public TowerWarsTeamHelper getTowerWarsTeamHelper() {
        return towerWarsTeamHelper;
    }

    public GameStateHelper getGameStateHelper() {
        return gameStateHelper;
    }

    public Titles getTitles() {
        return titles;
    }

    public TowerPlayerHelper getTowerPlayerHelper() {
        return towerPlayerHelper;
    }

    public InventoryCustomizer getInventoryCustomizer() {
        return inventoryCustomizer;
    }

    public InventoryLoader getInventoryLoader() {
        return inventoryLoader;
    }

}
