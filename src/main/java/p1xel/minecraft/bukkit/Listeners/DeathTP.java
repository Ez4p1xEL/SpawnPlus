package p1xel.minecraft.bukkit.Listeners;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import p1xel.minecraft.bukkit.SpawnPlus;
import p1xel.minecraft.bukkit.Storage.Config;
import p1xel.minecraft.bukkit.Storage.Locale;
import p1xel.minecraft.bukkit.Storage.SpawnManager;
import p1xel.minecraft.bukkit.User;

public class DeathTP implements Listener {

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {

        Player p = e.getEntity();
        User user = new User(p);
        Location new_loc = user.getSpawn();
        String uuid = p.getUniqueId().toString();

        if (SpawnManager.getLocation(uuid)!=null) {
            if (SpawnPlus.getInstance().isVaultEnabled()) {
                double cost = Config.getDouble("settings.local.money-cost.amount");
                if (SpawnPlus.getEconomy().getBalance(p) < cost) {
                    Location result;
                    try {
                        result = user.getSpawn(Config.getString("settings.local.money-cost.back-to"));
                    } catch (NullPointerException exception) {
                        result = user.getSpawn("default");
                    }
                    p.setBedSpawnLocation(result);
                    p.sendMessage(Locale.getMessage("not-enough-money"));
                    return;
                } else {
                    SpawnPlus.getEconomy().withdrawPlayer(p, cost);
                    p.sendMessage(Locale.getMessage("money-cost").replaceAll("%money%", String.valueOf(cost)));
                }
            }
        }

        if (new_loc != null) {
            p.setBedSpawnLocation(new_loc,true);
        }

    }
}
