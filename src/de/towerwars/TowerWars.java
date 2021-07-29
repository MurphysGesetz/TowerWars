package de.towerwars;

import de.towerwars.commands.SetupCommand;
import de.towerwars.commands.TestCommand;
import de.towerwars.game.team.TowerWarsTeamHelper;
import de.towerwars.listeners.*;
import de.towerwars.listeners.block.BlockBreakListener;
import de.towerwars.listeners.block.BlockPlaceListener;
import de.towerwars.listeners.player.*;
import de.towerwars.listeners.tower.TowerEditListener;
import de.towerwars.managers.ModuleManager;
import de.towerwars.mysql.MySqlConnection;
import de.towerwars.mysql.MySqlTableHelper;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

//
// YOU MUST SET max-entity-collisions TO "-1" IN THE
// SPIGOT.YML
//
public class TowerWars extends JavaPlugin {

    public static final String PREFIX = "§7▰ §2Tower§aWars §8| §7";
    private ModuleManager moduleManager;

    @Override
    public void onEnable() {
        getConfig().options().copyDefaults(true);
        saveConfig();
        moduleManager = new ModuleManager(this);
        if(!tryToConnectDatabase()) return;
        init();
        moduleManager.getCustomEntityRegister().loadEntities();
        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        getServer().getScheduler().runTaskLater(this, () -> moduleManager.towerWarsTeamHelper = new TowerWarsTeamHelper(moduleManager), 40L);
    }

    @Override
    public void onDisable() {
        if(moduleManager.getMySqlConnection() == null) return;
        if(moduleManager.getMySqlConnection().isConnected()) {
            moduleManager.getMySqlConnection().disconnect();
        }
    }

    private void init() {
        PluginManager pluginManager = getServer().getPluginManager();
        pluginManager.registerEvents(new PlayerConnectionListener(moduleManager), this);
        pluginManager.registerEvents(new PlayerDisconnectListener(moduleManager), this);
        pluginManager.registerEvents(new PlayerArmorStandManipulateListener(), this);
        pluginManager.registerEvents(new PlayerInteractListener(moduleManager), this);
        pluginManager.registerEvents(new InventoryClickListener(moduleManager), this);
        pluginManager.registerEvents(new PlayerItemHeldListener(moduleManager), this);
        pluginManager.registerEvents(new TowerEditListener(moduleManager), this);
        pluginManager.registerEvents(new FoodLevelChangeListener(), this);
        pluginManager.registerEvents(new PlayerDropItemListener(), this);
        pluginManager.registerEvents(new WeatherChangeListener(), this);
        pluginManager.registerEvents(new EntityDamageListener(), this);
        pluginManager.registerEvents(new BlockBreakListener(), this);
        pluginManager.registerEvents(new BlockPlaceListener(), this);
        getCommand("setup").setExecutor(new SetupCommand(moduleManager));
        getCommand("test").setExecutor(new TestCommand(moduleManager));
    }

    private boolean tryToConnectDatabase() {
        if (getConfig().getString("mysql.password").equals("12345abc")) {
            getServer().broadcastMessage(PREFIX + "§c§lTrage in der Config.yml die MySQL Daten ein!");
            return false;
        } else {
            moduleManager.mySqlConnection = new MySqlConnection(getConfig().getString("mysql.host"), getConfig().getString("mysql.port"), getConfig().getString("mysql.database"),
                    getConfig().getString("mysql.user"), getConfig().getString("mysql.password"));
            moduleManager.getMySqlConnection().connect();
            moduleManager.mySqlTableHelper = new MySqlTableHelper(moduleManager);
            return true;
        }
    }
}
