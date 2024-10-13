package p1xel.minecraft.bukkit.Command;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import p1xel.minecraft.bukkit.Storage.Locale;
import p1xel.minecraft.bukkit.User;

import javax.annotation.ParametersAreNonnullByDefault;

public class SpawnCmd implements CommandExecutor {

    @Override
    @ParametersAreNonnullByDefault
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (args.length == 0) {

            if (!sender.hasPermission("spawnplus.spawn")) {
                sender.sendMessage(Locale.getMessage("no-perm"));
                return true;
            }

            if (!(sender instanceof Player)) {
                sender.sendMessage(Locale.getMessage("must-be-player"));
                return true;
            }

            Player p = (Player) sender;
            User user = new User(p);
            Location loc = user.getSpawn();
            if (loc == null) {
                sender.sendMessage(Locale.getMessage("destination-incorrect"));
                return true;
            }

            p.teleport(loc);
            sender.sendMessage(Locale.getMessage("tp-success").replaceAll("%target%", user.getType()));
            return true;

        }




        return true;
    }

}
