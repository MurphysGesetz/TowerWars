package de.towerwars.commands;

import de.towerwars.managers.ModuleManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TestCommand implements CommandExecutor {

    private final ModuleManager moduleManager;

    public TestCommand(ModuleManager moduleManager) {
        this.moduleManager = moduleManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player)sender;
            if(player.hasPermission("test")) {
                if(args.length == 0) {
                    moduleManager.getTowerPlayerHelper().getTowerPlayer(player).getTowerWarsTeam().addCoins(9999);
                    moduleManager.getTowerPlayerHelper().getTowerPlayer(player).getTowerWarsTeam().addIncome(9999);
                } else if(args.length == 1) {
                    if(args[0].equalsIgnoreCase("0")) {
                        moduleManager.getTowerWarsTeamHelper().getTowerWarsTeams()[0].summonMonster(moduleManager.getTowerWarsTeamHelper().getTowerWarsTeams()[0], "cavespider");
                    } else if(args[0].equalsIgnoreCase("1")) {
                        for(int i = 0; i < 5; i++)
                        moduleManager.getTowerWarsTeamHelper().getTowerWarsTeams()[0].summonMonster(moduleManager.getTowerWarsTeamHelper().getTowerWarsTeams()[0], "cavespider");
                    } else if(args[0].equalsIgnoreCase("2")) {
                        for(int i = 0; i < 5; i++)
                        moduleManager.getTowerWarsTeamHelper().getTowerWarsTeams()[0].summonMonster(moduleManager.getTowerWarsTeamHelper().getTowerWarsTeams()[0], "chicken");
                    } else if(args[0].equalsIgnoreCase("3")) {
                        moduleManager.getTowerWarsTeamHelper().getTowerWarsTeams()[0].summonMonster(moduleManager.getTowerWarsTeamHelper().getTowerWarsTeams()[0], "zombie");
                    } else if(args[0].equalsIgnoreCase("4")) {
                        moduleManager.getTowerWarsTeamHelper().getTowerWarsTeams()[0].summonMonster(moduleManager.getTowerWarsTeamHelper().getTowerWarsTeams()[0], "sheep");
                    } else if(args[0].equalsIgnoreCase("5")) {
                        for(int i = 0; i < 5; i++)
                        moduleManager.getTowerWarsTeamHelper().getTowerWarsTeams()[0].summonMonster(moduleManager.getTowerWarsTeamHelper().getTowerWarsTeams()[0], "wolf");
                    } else if(args[0].equalsIgnoreCase("6")) {
                        moduleManager.getTowerWarsTeamHelper().getTowerWarsTeams()[0].summonMonster(moduleManager.getTowerWarsTeamHelper().getTowerWarsTeams()[0], "spider");
                    }
                }
            }
        }
        return false;
    }
}