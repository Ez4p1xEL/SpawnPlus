package p1xel.minecraft.bukkit.Storage;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import p1xel.minecraft.bukkit.SpawnPlus;

import java.io.File;

public class Locale {

    static File file;
    static FileConfiguration yaml;

    public void upload(File locale) {
        file = locale;
        yaml = YamlConfiguration.loadConfiguration(locale);
    }

    public void createFile() {

        String[] langs = {"en", "zh_CN", "zh_TW"};
        for (String lang : langs) {

            File file = new File(SpawnPlus.getInstance().getDataFolder() + "/lang", lang + ".yml");

            if (!file.exists()) {

                SpawnPlus.getInstance().saveResource("lang/"+ lang +".yml", false);
            }

        }

        upload(new File(SpawnPlus.getInstance().getDataFolder() + "/lang", Config.getLanguage() + ".yml"));

    }

    public static FileConfiguration get() {
        return yaml;
    }

    public static String getMessage(String path) {
        return ChatColor.translateAlternateColorCodes('&', yaml.getString(path).replaceAll("%plugin%", yaml.getString("plugin")));
    }

    public static String translate(String message) {
        return ChatColor.translateAlternateColorCodes('&', message.replaceAll("%plugin%", yaml.getString("plugin")));
    }

}
