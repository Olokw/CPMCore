package net.balacobaco.cpmcore.eventos;

import net.balacobaco.cpmcore.CPMCore;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.geysermc.api.connection.Connection;
import org.geysermc.event.subscribe.Subscribe;
import org.geysermc.geyser.api.GeyserApi;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Join implements Listener {
    @EventHandler
    public void join(PlayerJoinEvent e) {
        CPMCore.instance.tryApplySkin(e.getPlayer());
    }



}
