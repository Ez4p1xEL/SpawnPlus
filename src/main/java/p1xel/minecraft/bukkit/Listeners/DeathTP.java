package p1xel.minecraft.bukkit.Listeners;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import p1xel.minecraft.bukkit.User;

public class DeathTP implements Listener {

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {

        Player p = e.getEntity();
        Location new_loc = new User(p).getSpawn();

        if (new_loc != null) {
            p.setBedSpawnLocation(new_loc,true);
        }

    }
}
