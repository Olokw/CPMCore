package net.balacobaco.cpmcore.comandos;

import net.balacobaco.cpmcore.CPMCore;
import net.balacobaco.cpmcore.Message;
import net.balacobaco.cpmcore.utils.PrisaoConfig;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.event.player.PlayerTeleportEvent;

public class PrisaoCmd implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender.isOp()) {
            if (args.length == 2){
                Bukkit.broadcast(Message.process("<yellow>O jogador " + args[0] + " foi preso. Pena: Colher " + Integer.parseInt(args[1]) + " trigos."));
                if (Bukkit.getOfflinePlayer(args[0]).isOnline()){
                    Title title = Title.title(Message.process("<red>Você foi preso!!!"), Message.process("Colha " + Integer.parseInt(args[1]) + " trigos!!!"));
                    Bukkit.getOfflinePlayer(args[0]).getPlayer().teleport(new Location(Bukkit.getWorld("prisao"), 5.5, 4, -52.5, 0, 0), PlayerTeleportEvent.TeleportCause.COMMAND);
                    Bukkit.getOfflinePlayer(args[0]).getPlayer().showTitle(title);
                    Bukkit.getOfflinePlayer(args[0]).getPlayer().playSound(Bukkit.getOfflinePlayer(args[0]).getPlayer(), Sound.BLOCK_END_PORTAL_SPAWN, 1, 1);
                }

                PrisaoConfig prisaoConfig = new PrisaoConfig(Integer.parseInt(args[1]));
                CPMCore.instance.getPrisaoJsonUtils().saveWheatConfig(prisaoConfig, Bukkit.getOfflinePlayer(args[0]).getUniqueId());
                // adicionar p prisao

            }
        }
        return true;
    }
}
