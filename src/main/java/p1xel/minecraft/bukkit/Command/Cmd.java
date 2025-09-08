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
import p1xel.minecraft.bukkit.User;

import javax.annotation.ParametersAreNonnullByDefault;

public class Cmd implements CommandExecutor {

    @Override
    @ParametersAreNonnullByDefault
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {


        if (args.length <= 1) {
            Player p;
            if (args.length == 0) {
                if (!sender.hasPermission("spawnplus.spawn")) {
                    sender.sendMessage(Locale.getMessage("no-perm"));
                    return true;
                }

                if (!(sender instanceof Player)) {
                    sender.sendMessage(Locale.getMessage("must-be-player"));
                    return true;
                }
                p = (Player) sender;

                User user = new User(p);
                Location loc = user.getSpawn();
                if (loc == null) {
                    sender.sendMessage(Locale.getMessage("destination-incorrect"));
                    return true;
                }

                p.teleportAsync(loc);
                sender.sendMessage(Locale.getMessage("spawn"));
                return true;
            }

            if (args.length == 1) {

                p = Bukkit.getPlayer(args[0]);
                if (p != null) {
                    if (!sender.hasPermission("spawnplus.spawn.other")) {
                        sender.sendMessage(Locale.getMessage("no-perm"));
                        return true;
                    }
                    User user = new User(p);
                    Location loc = user.getSpawn();
                    if (loc == null) {
                        sender.sendMessage(Locale.getMessage("destination-incorrect"));
                        return true;
                    }

                    p.teleportAsync(loc);
                    p.sendMessage(Locale.getMessage("spawn"));
                    return true;
                }


            }

        }

        if (args.length == 1) {

            if (args[0].equalsIgnoreCase("help")) {

                if (!sender.hasPermission("spawnplus.help")) {
                    sender.sendMessage(Locale.getMessage("no-perm"));
                    return true;
                }

                for (String message : Locale.get().getConfigurationSection("commands").getKeys(false)) {

                    if (sender.hasPermission("spawnplus." + message.replaceAll("-", "."))) {
                        message = Locale.getMessage("commands." + message);
                        message = message.replaceAll("%input%", label);
                        message = message.replaceAll("%version%", SpawnPlus.getInstance().getDescription().getVersion());
                        sender.sendMessage(message);
                    }

                }

                return true;

            }

            if (args[0].equalsIgnoreCase("reload")) {

                if (!sender.hasPermission("spawnplus.reload")) {
                    sender.sendMessage(Locale.getMessage("no-perm"));
                    return true;
                }

                Config.reloadConfig();
                new Locale().createFile();
                new SpawnManager().createFile();
                sender.sendMessage(Locale.getMessage("reload-success"));
                return true;

            }

            if (args[0].equalsIgnoreCase("set")) {

                if (!sender.hasPermission("spawnplus.set")) {
                    sender.sendMessage(Locale.getMessage("no-perm"));
                    return true;
                }

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

                if (!sender.hasPermission("spawnplus.tp")) {
                    sender.sendMessage(Locale.getMessage("no-perm"));
                    return true;
                }

                if (!(sender instanceof Player)) {
                    sender.sendMessage(Locale.getMessage("must-be-player"));
                    return true;
                }

                Player p = (Player) sender;
                Location loc = Config.getLocation("settings.global.default.location");
                if (loc == null) {
                    sender.sendMessage(Locale.getMessage("spawn-not-exist").replaceAll("%group%", "default"));
                    return true;
                }
                p.teleportAsync(loc);
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

                    if (!sender.hasPermission("spawnplus.set.group")) {
                        sender.sendMessage(Locale.getMessage("no-perm"));
                        return true;
                    }

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

                    if (!sender.hasPermission("spawnplus.set.local")) {
                        sender.sendMessage(Locale.getMessage("no-perm"));
                        return true;
                    }

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

                    if (!sender.hasPermission("spawnplus.tp.group")) {
                        sender.sendMessage(Locale.getMessage("no-perm"));
                        return true;
                    }

                    if (!(sender instanceof Player)) {
                        sender.sendMessage(Locale.getMessage("must-be-player"));
                        return true;
                    }

                    String group = args[2];
                    for (String g : Config.getConfigurationSection("settings.global.group-spawn.groups").getKeys(false)) {

                        if (g.equalsIgnoreCase(group)) {

                            Location loc = Config.getLocation("settings.global.group-spawn.groups." + g + ".location");
                            if (loc == null) {
                                sender.sendMessage(Locale.getMessage("spawn-not-exist").replaceAll("%group%", group));
                                return true;
                            }
                            Player p = (Player) sender;
                            p.teleportAsync(loc);
                            sender.sendMessage(Locale.getMessage("tp-success").replaceAll("%target%", g));
                            return true;

                        }


                    }

                    sender.sendMessage(Locale.getMessage("group-not-found").replaceAll("%target%", group));
                    return true;

                }

                if (args[1].equalsIgnoreCase("local")) {

                    if (!sender.hasPermission("spawnplus.tp.local")) {
                        sender.sendMessage(Locale.getMessage("no-perm"));
                        return true;
                    }

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

                    p.teleportAsync(loc);
                    sender.sendMessage(Locale.getMessage("tp-success").replaceAll("%target%", user));
                    return true;

                }


            }


        }








        return false;
    }
}
