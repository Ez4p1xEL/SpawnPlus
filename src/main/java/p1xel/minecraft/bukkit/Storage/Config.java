package p1xel.minecraft.bukkit.Storage;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;
import p1xel.minecraft.bukkit.SpawnPlus;

public class Config {

    public static Configuration config = null;

    public static void reloadConfig() {
        SpawnPlus.getInstance().reloadConfig();
        config = SpawnPlus.getInstance().getConfig();
    }

    public static String getString(String path) {
        return config.getString(path);
    }

    public static int getInt(String path) {
        return config.getInt(path);
    }

    public static void setLocation(String path, Location loc) {

        config.set(path+".world", loc.getWorld().getName());
        config.set(path+".x", loc.getX());
        config.set(path+".y", loc.getY());
        config.set(path+".z", loc.getZ());
        config.set(path+".yaw", loc.getYaw());
        config.set(path+".pitch", loc.getPitch());
        SpawnPlus.getInstance().saveConfig();
        reloadConfig();

    }

    public static Location getLocation(String path) {

        ConfigurationSection section = config.getConfigurationSection(path);
        if (section == null) {
            return null;
        }
        World world = Bukkit.getWorld(section.getString("world"));
        double x = section.getDouble("x");
        double y = section.getDouble("y");
        double z = section.getDouble("z");
        float pitch = (float) section.getDouble("pitch");
        float yaw = (float) section.getDouble("yaw");

        return new Location(world,x,y,z,yaw,pitch);
//        return (Location) config.get(path, null);
    }

    public static boolean getBool(String path) {
        return config.getBoolean(path);
    }

    public static String getLanguage() {
        return config.getString("locale");
    }

    public static ConfigurationSection getConfigurationSection(String path) {
        return config.getConfigurationSection(path);
    }


}
