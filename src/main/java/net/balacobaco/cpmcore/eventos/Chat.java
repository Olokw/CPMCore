package net.balacobaco.cpmcore.eventos;

import net.balacobaco.cpmcore.Message;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class Chat implements Listener {

    @EventHandler
    public void onJoin (PlayerJoinEvent e){
        Bukkit.broadcast(Message.process("<yellow>" + e.getPlayer().getDisplayName() + " entrou no servidor."));
    }

    @EventHandler
    public void onLeave (PlayerQuitEvent e){
        Bukkit.broadcast(Message.process("<yellow>" + e.getPlayer().getDisplayName() + " saiu do servidor."));
    }
}

