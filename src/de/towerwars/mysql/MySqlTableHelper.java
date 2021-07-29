package de.towerwars.mysql;

import de.towerwars.TowerWars;
import de.towerwars.managers.ModuleManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MySqlTableHelper {

    private final ModuleManager moduleManager;

    public MySqlTableHelper(ModuleManager moduleManager) {
        this.moduleManager = moduleManager;
    }

    public boolean locationExist(String worldName, String locationName) {
        try {
            PreparedStatement preparedStatement = moduleManager.getMySqlConnection().getStatement("SELECT * FROM locations WHERE NAME = ? AND WORLD = ?");
            preparedStatement.setString(1, locationName);
            preparedStatement.setString(2, worldName);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                return resultSet.getString("POSX") != null;
            }
            resultSet.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return false;
    }

    public Location getLocation(String worldName, String locationName) {
        Location location;
        try {
            PreparedStatement preparedStatement = moduleManager.getMySqlConnection().getStatement("SELECT * FROM locations WHERE NAME = ? AND WORLD = ?");
            preparedStatement.setString(1, locationName);
            preparedStatement.setString(2, worldName);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                location = new Location(Bukkit.getWorld(worldName), resultSet.getDouble("POSX"), resultSet.getDouble("POSY"), resultSet.getDouble("POSZ"));
                location.setYaw(resultSet.getFloat("YAW"));
                location.setPitch(resultSet.getFloat("PITCH"));
                return location;
            }
            resultSet.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    public void setLocation(String locationName, Location location) {
        try {
            PreparedStatement preparedStatement = moduleManager.getMySqlConnection().getStatement("INSERT INTO locations(NAME,WORLD,POSX,POSY,POSZ,YAW,PITCH) VALUES (?,?,?,?,?,?,?);");
            preparedStatement.setString(1, locationName);
            preparedStatement.setString(2, location.getWorld().getName());
            preparedStatement.setDouble(3, location.getX());
            preparedStatement.setDouble(4, location.getY());
            preparedStatement.setDouble(5, location.getZ());
            preparedStatement.setFloat(6, location.getYaw());
            preparedStatement.setFloat(7, location.getPitch());
            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public int getSkill(String uuid) {
        try {
            PreparedStatement preparedStatement = moduleManager.getMySqlConnection().getStatement("SELECT * FROM towerplayer WHERE UUID = ?");
            preparedStatement.setString(1, uuid);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                return resultSet.getInt("SKILL");
            }
            resultSet.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return 0;
    }

    public void setSkill(String uuid, int skill) {
        try {
            PreparedStatement preparedStatement = moduleManager.getMySqlConnection().getStatement("UPDATE towerplayer SET SKILL = ? WHERE UUID = ?");
            preparedStatement.setInt(1, skill);
            preparedStatement.setString(2, uuid);
            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public boolean playerExist(String uuid) {
        try {
            PreparedStatement preparedStatement = moduleManager.getMySqlConnection().getStatement("SELECT * FROM towerplayer WHERE UUID = ?");
            preparedStatement.setString(1, uuid);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                return resultSet.getString("UUID") != null;
            }
            resultSet.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return false;
    }

    public void createPlayer(String uuid, String name) {
        try {
            PreparedStatement preparedStatement = moduleManager.getMySqlConnection().getStatement("INSERT INTO towerplayer(UUID, NAME, WINS, SKILL, PLAYED) VALUES (?,?,0,0,0);");
            preparedStatement.setString(1, uuid);
            preparedStatement.setString(2, name);
            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
}
