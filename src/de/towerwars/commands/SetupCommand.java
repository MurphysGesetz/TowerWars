package de.towerwars.commands;

import de.towerwars.TowerWars;
import de.towerwars.managers.ModuleManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetupCommand implements CommandExecutor {

    private final ModuleManager moduleManager;

    public SetupCommand(ModuleManager moduleManager) {
        this.moduleManager = moduleManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) return true;
            Player player = (Player)sender;
            if(player.hasPermission("towerwars.admin")) {
                if(args.length == 0) {
                    player.sendMessage(TowerWars.PREFIX + "/setup lobby");
                    player.sendMessage(TowerWars.PREFIX + "/setup team <Rot/Aqua/Gelb/Orange/Gruen/Blau>");
                    player.sendMessage(TowerWars.PREFIX + "/setup min <Rot/Aqua/Gelb/Orange/Gruen/Blau>");
                    player.sendMessage(TowerWars.PREFIX + "/setup max <Rot/Aqua/Gelb/Orange/Gruen/Blau>");
                    player.sendMessage(TowerWars.PREFIX + "/setup start <Rot/Aqua/Gelb/Orange/Gruen/Blau>");
                    player.sendMessage(TowerWars.PREFIX + "/setup end <Rot/Aqua/Gelb/Orange/Gruen/Blau>");
                    player.sendMessage(TowerWars.PREFIX + "/setup path <1-100>");
                } else if(args.length == 1) {
                    if(args[0].equals("lobby")) {
                        moduleManager.getMySqlTableHelper().setLocation("lobby", player.getLocation());
                        player.sendMessage(TowerWars.PREFIX + "Die §aLobby §7wurde erfolgreich gesetzt!");
                    }
                } else if(args.length == 2) {
                    switch (args[0]) {
                        case "team":
                            if (args[1].equals("rot") || args[1].equals("blau") || args[1].equals("aqua") || args[1].equals("gelb") ||
                                    args[1].equals("gruen") || args[1].equals("orange")) {
                                moduleManager.getMySqlTableHelper().setLocation(args[1] + "spawn", player.getLocation());
                                player.sendMessage(TowerWars.PREFIX + "Du hast den Spawn für §eTeam §6§l" + args[1] + " §7gesetzt§8!");
                            }
                            break;
                        case "min":
                            if (args[1].equals("rot") || args[1].equals("blau") || args[1].equals("aqua") || args[1].equals("gelb") ||
                                    args[1].equals("gruen") || args[1].equals("orange")) {
                                moduleManager.getMySqlTableHelper().setLocation(args[1] + "min", player.getLocation());
                                player.sendMessage(TowerWars.PREFIX + "Du hast den Min für §eTeam §6§l" + args[1] + " §7gesetzt§8!");
                            }
                            break;
                        case "max":
                            if (args[1].equals("rot") || args[1].equals("blau") || args[1].equals("aqua") || args[1].equals("gelb") ||
                                    args[1].equals("gruen") || args[1].equals("orange")) {
                                moduleManager.getMySqlTableHelper().setLocation(args[1] + "max", player.getLocation());
                                player.sendMessage(TowerWars.PREFIX + "Du hast den Max für §eTeam §6§l" + args[1] + " §7gesetzt§8!");
                            }
                            break;
                        case "start":
                            if (args[1].equals("rot") || args[1].equals("blau") || args[1].equals("aqua") || args[1].equals("gelb") ||
                                    args[1].equals("gruen") || args[1].equals("orange")) {
                                moduleManager.getMySqlTableHelper().setLocation(args[1] + "start", player.getLocation());
                                player.sendMessage(TowerWars.PREFIX + "Du hast den Startpunkt für §eTeam §6§l" + args[1] + " §7gesetzt§8!");
                            }
                            break;
                        case "end":
                            if (args[1].equals("rot") || args[1].equals("blau") || args[1].equals("aqua") || args[1].equals("gelb") ||
                                    args[1].equals("gruen") || args[1].equals("orange")) {
                                moduleManager.getMySqlTableHelper().setLocation(args[1] + "end", player.getLocation());
                                player.sendMessage(TowerWars.PREFIX + "Du hast den Endpunkt für §eTeam §6§l" + args[1] + " §7gesetzt§8!");
                            }
                            break;
                        case "path":
                            moduleManager.getMySqlTableHelper().setLocation(args[1] + "path", player.getLocation());
                                player.sendMessage(TowerWars.PREFIX + "Du hast den Pfad §6§l" + args[1] + " §7gesetzt§8!");
                            break;
                    }
                }
            } else {
                player.sendMessage(TowerWars.PREFIX + "§cDazu hast du keine Rechte!");
            }

        return true;
    }
}
