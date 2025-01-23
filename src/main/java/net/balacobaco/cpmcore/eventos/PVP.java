package net.balacobaco.cpmcore.eventos;

import net.balacobaco.cpmcore.CPMCore;
import net.balacobaco.cpmcore.Message;
import net.sacredlabyrinth.phaed.simpleclans.ClanPlayer;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Tameable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.Plugin;

import java.util.Objects;

public class PVP implements Listener {
    @EventHandler
    public void pvp(EntityDamageByEntityEvent e){
        if (e.getEntity() instanceof Player || e.getEntity() instanceof Tameable){
            Player p2 = null;
            if (e.getDamager() instanceof Player){
                p2 = (Player) e.getDamager();
            }
            if (e.getDamager() instanceof Projectile){
                if (((Projectile) e.getDamager()).getShooter() instanceof Player){
                    p2 = (Player) ((Projectile) e.getDamager()).getShooter();
                }
            }
            if (e.getDamager() instanceof Tameable){
                if (((Tameable) e.getDamager()).isTamed()){
                    p2 = (Player) ((Tameable) e.getDamager()).getOwner();
                }
            }
            if (p2 == null) return;
            if (p2 == e.getEntity()) return;
            ClanPlayer cp1 = null;
            if (e.getEntity() instanceof Tameable){
                CPMCore.instance.getSCPlugin().getClanManager().getAnyClanPlayer(((Tameable) e.getEntity()).getOwnerUniqueId());
            }else{
                CPMCore.instance.getSCPlugin().getClanManager().getAnyClanPlayer(e.getEntity().getUniqueId());
            }
            ClanPlayer cp2 = CPMCore.instance.getSCPlugin().getClanManager().getAnyClanPlayer(p2.getUniqueId());
            if (cp1 == null || cp2 == null || !Objects.requireNonNull(cp2).getClan().isWarring(Objects.requireNonNull(cp1).getClan())){
                e.setCancelled(true);
                p2.sendMessage(Message.process("<red>Seu clã e o desse jogador devem estar em guerra para que vocês possam batalhar."));
            }
        }
    }
}
