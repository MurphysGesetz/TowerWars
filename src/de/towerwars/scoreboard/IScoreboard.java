package de.towerwars.scoreboard;

import org.bukkit.entity.Player;

public interface IScoreboard {

    void update(Player player);

    void set(Player player);

}
