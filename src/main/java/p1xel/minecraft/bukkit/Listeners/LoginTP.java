package p1xel.minecraft.bukkit.Listeners;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import p1xel.minecraft.bukkit.Storage.Config;
import p1xel.minecraft.bukkit.Storage.SpawnManager;
import p1xel.minecraft.bukkit.User;

public class LoginTP implements Listener {

    @EventHandler
    public void onLogin(PlayerJoinEvent e) {

        Player p = e.getPlayer();

        System.out.println(p.getUniqueId());
        System.out.println("LOCAL:" + SpawnManager.getLocation(p.getUniqueId().toString()));

        if (Config.getBool("settings.login-tp")) {

            Location loc = new User(p).getSpawn();

            System.out.println(loc);

            if (loc == null) {
                return;
            }

            p.teleport(loc);

        }

    }

}
