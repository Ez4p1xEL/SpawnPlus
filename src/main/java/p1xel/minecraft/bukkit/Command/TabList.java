package p1xel.minecraft.bukkit.Command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import p1xel.minecraft.bukkit.Storage.Config;
import p1xel.minecraft.bukkit.User;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;

public class TabList implements TabCompleter {

    List<String> args0 = new ArrayList<>();
    @Override
    @ParametersAreNonnullByDefault
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {

        if (args0.isEmpty()) {
            args0.add("help"); args0.add("set"); args0.add("tp"); args0.add("reload");
        }

        List<String> result0 = new ArrayList<>();
        if (args.length == 1) {
            for (String a : args0) {
                if (a.toLowerCase().startsWith(args[0].toLowerCase())) {
                    result0.add(a);
                }
            }
            return result0;
        }

        if (args.length == 2) {
            if (args[0].equalsIgnoreCase("set") || args[0].equalsIgnoreCase("tp")) {

                if (!(sender instanceof Player)) {
                    return null;
                }

                Player player = (Player) sender;
                //String uuid = player.getUniqueId().toString();
                User user = new User(player);

                if (args[1].equalsIgnoreCase("group")) {
                    return new ArrayList<>(Config.getConfigurationSection("settings.global.group-spawn.groups").getKeys(false));
                }

                if (args[1].equalsIgnoreCase("local")) {
                    List<String> result = new ArrayList<>();
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        result.add(p.getName());
                    }
                    return result;
                }

            }

        }

        return new ArrayList<>();
    }

}
