package de.towerwars.player;

import de.towerwars.TowerWars;
import de.towerwars.managers.ModuleManager;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class TowerPlayerHelper {

    private final Map<UUID, TowerPlayer> playerCache;
    private final ModuleManager moduleManager;


    public TowerPlayerHelper(ModuleManager moduleManager) {
        this.playerCache = new ConcurrentHashMap<>();
        this.moduleManager = moduleManager;
    }

    public TowerPlayer getTowerPlayer(Player player) {
        if (!playerCache.containsKey(player.getUniqueId())) return null;
        TowerPlayer towerPlayer = playerCache.get(player.getUniqueId());
        if (towerPlayer == null) {
            return create(player);
        }
        return towerPlayer;
    }

    public TowerPlayer create(Player player) {
        TowerPlayer towerPlayer = new TowerPlayer(player.getUniqueId(), moduleManager);
        playerCache.put(player.getUniqueId(), towerPlayer);
        return towerPlayer;
    }

    public void remove(Player player) {
        playerCache.remove(player.getUniqueId());
    }
}
