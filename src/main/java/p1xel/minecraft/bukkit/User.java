package p1xel.minecraft.bukkit;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import p1xel.minecraft.bukkit.Storage.Config;
import p1xel.minecraft.bukkit.Storage.SpawnManager;

public class User {

    private Player player;
    private String uuid;
    private String type;

    public User(Player player) {

        this.player = player;
        this.uuid = player.getUniqueId().toString();

    }

    public String getName() {
        return player.getName();
    }

    public String getType() {
        // must use after getSpawn();
        return this.type;
    }

    public String getWorld() {
        return player.getWorld().getName();
    }

    // It must be used if the spawn point is existed.
    public Location getSpawn(String type) {
        if (type.equalsIgnoreCase("local")) {
            return SpawnManager.getLocation(uuid);
        } else if (type.equalsIgnoreCase("group")) {
            for (String group : Config.getConfigurationSection("settings.global.group-spawn.groups").getKeys(false)) {
                if (player.hasPermission(Config.getString("settings.global.group-spawn.groups." + group+".permission"))) {
                    return Config.getLocation("settings.global.group-spawn.groups." + group + ".location");
                }
            }
        } else if (type.equalsIgnoreCase("default")) {
            return Config.getLocation("settings.global.default.location");
        }
        return null;
    }

    public Location getSpawn() {
        // if local spawn exists
        if (Config.getBool("settings.local.enable")) {

            if (SpawnManager.isExist(uuid)) {
                Location loc = SpawnManager.getLocation(uuid);
                if (Config.getBool("settings.local.single-world-only")) {
                    // player will teleport to local spawn
                    // if the death world is equal to world of local spawn
                    if (getWorld().equalsIgnoreCase(loc.getWorld().getName())) {
                        return loc;
                    }
                } else {
                    return loc;
                }
                this.type = getName();
            }

        }

        // if group spawn exists
        if (Config.getBool("settings.global.group-spawn.enable")) {
            for (String group : Config.getConfigurationSection("settings.global.group-spawn.groups").getKeys(false)) {
                if (player.hasPermission(Config.getString("settings.global.group-spawn.groups." + group+".permission"))) {
                    this.type = group;
                    return Config.getLocation("settings.global.group-spawn.groups." + group + ".location");

                }

            }

        }

        // if player has no permission for above spawn point
        // use default spawn point instead
        if (Config.getBool("settings.global.default.enable")) {
            this.type = "default";
            return Config.getLocation("settings.global.default.location");
        }

        // if default spawn point is not enabled
        // back to player's vanilla spawn point
        //return player.getBedSpawnLocation();
        return null;
    }

}
