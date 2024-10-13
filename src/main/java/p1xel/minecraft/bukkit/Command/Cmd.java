package p1xel.minecraft.bukkit.Command;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import p1xel.minecraft.bukkit.SpawnPlus;
import p1xel.minecraft.bukkit.Storage.Config;
import p1xel.minecraft.bukkit.Storage.Locale;
import p1xel.minecraft.bukkit.Storage.SpawnManager;

import javax.annotation.ParametersAreNonnullByDefault;

public class Cmd implements CommandExecutor {

    @Override
    @ParametersAreNonnullByDefault
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!sender.hasPermission("spawnplus.admin")) {
            sender.sendMessage(Locale.getMessage("no-perm"));
            return true;
        }

        if (args.length == 0) {
            sender.sendMessage(Locale.getMessage("commands.help").replaceAll("%input%", label));
            return true;
        }

        if (args.length == 1) {

            if (args[0].equalsIgnoreCase("help")) {

                for (String message : Locale.get().getConfigurationSection("commands").getKeys(false)) {

                    message = Locale.getMessage("commands." + message);
                    message = message.replaceAll("%input%", label);
                    message = message.replaceAll("%version%", SpawnPlus.getInstance().getDescription().getVersion());

                    sender.sendMessage(message);

                }

                return true;

            }

            if (args[0].equalsIgnoreCase("reload")) {

                Config.reloadConfig();
                new Locale().createFile();
                new SpawnManager().createFile();
                sender.sendMessage(Locale.getMessage("reload-success"));
                return true;

            }

            if (args[0].equalsIgnoreCase("set")) {

                if (!(sender instanceof Player)) {
                    sender.sendMessage(Locale.getMessage("must-be-player"));
                    return true;
                }

                Player p = (Player) sender;
                Location loc = p.getLocation();
                Config.setLocation("settings.global.default.location", loc);
                sender.sendMessage(Locale.getMessage("set-success").replaceAll("%target%", "default"));
                return true;

            }

            if (args[0].equalsIgnoreCase("tp")) {

                if (!(sender instanceof Player)) {
                    sender.sendMessage(Locale.getMessage("must-be-player"));
                    return true;
                }

                Player p = (Player) sender;
                Location loc = Config.getLocation("settings.global.default.location");
                System.out.println("CMD: " + loc);
                if (loc == null) {
                    sender.sendMessage(Locale.getMessage("spawn-not-exist"));
                    return true;
                }
                p.teleport(loc);
                sender.sendMessage(Locale.getMessage("tp-success").replaceAll("%target%", "default"));
                return true;

            }

        }

        if (args.length == 2) {

            sender.sendMessage(Locale.getMessage("wrong-arg"));
            return true;

        }

        if (args.length == 3) {

            if (args[0].equalsIgnoreCase("set")) {

                if (args[1].equalsIgnoreCase("group")) {

                    if (!(sender instanceof Player)) {
                        sender.sendMessage(Locale.getMessage("must-be-player"));
                        return true;
                    }

                    String group = args[2];

                    for (String g : Config.getConfigurationSection("settings.global.group-spawn.groups").getKeys(false)) {

                        if (g.equalsIgnoreCase(group)) {

                            Player p = (Player) sender;
                            Location loc = p.getLocation();
                            Config.setLocation("settings.global.group-spawn.groups." + g + ".location", loc);
                            sender.sendMessage(Locale.getMessage("set-success").replaceAll("%target%", g));
                            return true;
                        }

                    }

                    sender.sendMessage(Locale.getMessage("group-not-found").replaceAll("%group%", group));
                    return true;

                }

                if (args[1].equalsIgnoreCase("local")) {

                    if (!(sender instanceof Player)) {
                        sender.sendMessage(Locale.getMessage("must-be-player"));
                        return true;
                    }

                    String user = args[2];
                    Player target = Bukkit.getPlayer(user);
                    if (target == null) {
                        sender.sendMessage(Locale.getMessage("player-invalid"));
                        return true;
                    }

                    Player p = (Player) sender;
                    Location loc = p.getLocation();
                    SpawnManager.setLocation(target.getUniqueId().toString(), loc);
                    sender.sendMessage(Locale.getMessage("set-success").replaceAll("%target%", user));
                    return true;

                }

            }

            if (args[0].equalsIgnoreCase("tp")) {

                if (args[1].equalsIgnoreCase("group")) {

                    if (!(sender instanceof Player)) {
                        sender.sendMessage(Locale.getMessage("must-be-player"));
                        return true;
                    }

                    String group = args[2];
                    for (String g : Config.getConfigurationSection("settings.global.group-spawn.groups").getKeys(false)) {

                        if (g.equalsIgnoreCase(group)) {

                            Location loc = Config.getLocation("settings.global.group-spawn.groups." + g + ".location");
                            if (loc == null) {
                                sender.sendMessage(Locale.getMessage("spawn-not-exist"));
                                return true;
                            }
                            Player p = (Player) sender;
                            p.teleport(loc);
                            sender.sendMessage(Locale.getMessage("tp-success").replaceAll("%target%", g));
                            return true;

                        }


                    }

                    sender.sendMessage(Locale.getMessage("group-not-found").replaceAll("%target%", group));
                    return true;

                }

                if (args[1].equalsIgnoreCase("local")) {

                    if (!(sender instanceof Player)) {
                        sender.sendMessage(Locale.getMessage("must-be-player"));
                        return true;
                    }

                    String user = args[2];
                    Player target = Bukkit.getPlayer(user);
                    if (target == null) {
                        sender.sendMessage(Locale.getMessage("player-invalid"));
                        return true;
                    }

                    Player p = (Player) sender;
                    Location loc = SpawnManager.getLocation(target.getUniqueId().toString());
                    if (loc == null) {
                        sender.sendMessage(Locale.getMessage("spawn-not-exist"));
                        return true;
                    }

                    p.teleport(loc);
                    sender.sendMessage(Locale.getMessage("tp-success").replaceAll("%target%", user));
                    return true;

                }


            }


        }








        return false;
    }
}
