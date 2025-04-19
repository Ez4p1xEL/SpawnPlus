package p1xel.minecraft.bukkit.Storage;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import p1xel.minecraft.bukkit.SpawnPlus;

import java.io.File;
import java.io.IOException;

public class SpawnManager {

    static File file;
    static FileConfiguration yaml;

    public void upload(File file, FileConfiguration yaml) {
        SpawnManager.file = file;
        SpawnManager.yaml = yaml;
    }

    public void createFile() {


        File file = new File(SpawnPlus.getInstance().getDataFolder(), "spawns.yml");

        if (!file.exists()) {

            SpawnPlus.getInstance().saveResource("spawns.yml", false);

        }

        upload(file, YamlConfiguration.loadConfiguration(file));

    }

    public static Location getLocation(String uuid) {
        ConfigurationSection section = yaml.getConfigurationSection(uuid);
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
    }

    public static void setLocation(String uuid, Location loc) {
        yaml.set(uuid+".world", loc.getWorld().getName());
        yaml.set(uuid+".x", loc.getX());
        yaml.set(uuid+".y", loc.getY());
        yaml.set(uuid+".z", loc.getZ());
        yaml.set(uuid+".yaw", loc.getYaw());
        yaml.set(uuid+".pitch", loc.getPitch());
        try {
            yaml.save(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean isExist(String uuid) {
        return yaml.get(uuid) != null;
    }

}
