package de.towerwars.util;

import de.towerwars.managers.ModuleManager;
import de.towerwars.player.TowerPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;

public class ArmorStandHelper {

    private final ModuleManager moduleManager;
    public int taskID;

    public ArmorStandHelper(ModuleManager moduleManager) {
        this.moduleManager = moduleManager;
    }

    public void stopPlayerMoveScheduler() {
        Bukkit.getScheduler().cancelTask(taskID);
    }

    public void handlePlayerMoveScheduler() {
        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(moduleManager.getPlugin(), () -> Bukkit.getOnlinePlayers().stream().filter(player -> player.getItemInHand().getType().equals(Material.ARMOR_STAND)).forEach(current -> {
            final TowerPlayer towerPlayer = moduleManager.getTowerPlayerHelper().getTowerPlayer(current);
            final Location location = getTargetBlock(current);
            final ArmorStand armorStand = towerPlayer.getTowerWarsTeam().getArmorStand();
            if(!towerPlayer.getTowerWarsTeam().isInsideArena(location)) {
                armorStand.setVisible(false);
            }else if (location != null) {
                armorStand.setVisible(true);
                armorStand.teleport(location.add(0.5D, 1.0D, 0.5D));
            } else {
                armorStand.setVisible(false);
            }
        }), 0L, 3L);
    }

    public Location getTargetBlock(Player player) {
        for(float i = 0.0F; i <= 30.0F; i += 0.2F) {
            final Block block;
            if ((block = player.getEyeLocation().add(player.getLocation().getDirection().multiply(i)).getBlock()).getType() == Material.GRASS) {
                return block.getLocation();
            }
        }
        return null;
    }
}
