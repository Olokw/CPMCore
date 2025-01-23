package net.balacobaco.cpmcore.eventos;

import net.balacobaco.cpmcore.CPMCore;
import net.balacobaco.cpmcore.Message;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.data.Ageable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;

public class Prisao implements Listener {
    @EventHandler
    public void blockplace(BlockPlaceEvent e){
        if (e.getBlock().getLocation().getWorld().getName().equalsIgnoreCase("prisao")){
            if (!e.getPlayer().isOp() && !e.getBlock().getType().equals(Material.WHEAT)){
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void blockbreak(BlockBreakEvent e){
        if (e.getBlock().getLocation().getWorld().getName().equalsIgnoreCase("prisao")){
            if (e.getBlock().getType().equals(Material.WHEAT)){
                if (((Ageable) e.getBlock().getBlockData()).getAge() == ((Ageable) e.getBlock().getBlockData()).getMaximumAge()){
                    e.getBlock().getLocation().getWorld().dropItem(e.getBlock().getLocation(), new ItemStack(Material.WHEAT_SEEDS, 3));
                    CPMCore.instance.getPrisaoJsonUtils().removeWheatPoint(e.getPlayer().getUniqueId());
                }else{
                    e.setCancelled(true);
                    e.getPlayer().sendMessage(Message.process("<red>Você só pode colher trigos crescidos."));
                }
            }else{
                if (!e.getPlayer().isOp()){
                    e.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void teleport(PlayerTeleportEvent e){
        if (e.getPlayer().isOp()) return;
        if (e.getFrom().getWorld().getName().equalsIgnoreCase("prisao")){
            if (!e.getCause().equals(PlayerTeleportEvent.TeleportCause.COMMAND)){
                e.getPlayer().sendMessage(Message.process("<red>Você não pode sair da prisão!"));
                e.setCancelled(true);
                return;
            }
        }
        if (e.getTo().getWorld().getName().equalsIgnoreCase("prisao")){
            if (!e.getCause().equals(PlayerTeleportEvent.TeleportCause.COMMAND)){
                e.getPlayer().sendMessage(Message.process("<red>Você não pode entrar da prisão assim!"));
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void damage(EntityDamageEvent e){
        if (e.getEntity().getWorld().getName().equalsIgnoreCase("prisao")){
            e.setCancelled(true);
            e.getEntity().sendMessage(Message.process("<red>Você não pode se matar na prisão!"));
        }
    }

    @EventHandler
    public void dirt(MoistureChangeEvent e){
        if (e.getBlock().getType().equals(Material.FARMLAND)){
            if (e.getBlock().getWorld().getName().equalsIgnoreCase("prisao")){
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void join(PlayerJoinEvent e){
        if (CPMCore.instance.getPrisaoJsonUtils().getWheatRemaining(e.getPlayer().getUniqueId()) > 0){
            if (!e.getPlayer().getWorld().getName().equalsIgnoreCase("prisao")) {
                e.getPlayer().teleport(new Location(Bukkit.getWorld("prisao"), 5.5, 4, -52.5, 0, 0), PlayerTeleportEvent.TeleportCause.COMMAND);
            }
        }
    }

    @EventHandler
    public void worldChange(PlayerChangedWorldEvent e){
        if (e.getPlayer().getWorld().getName().equalsIgnoreCase("prisao")){
            e.getPlayer().playSound(e.getPlayer(), Sound.BLOCK_END_PORTAL_SPAWN, 1, 1);
            Title title = Title.title(Message.process("<red>Você foi preso!!!"), Message.process("Colha " + CPMCore.instance.getPrisaoJsonUtils().getWheatRemaining(e.getPlayer().getUniqueId()) + " trigos!!!"));
        }

    }
}
