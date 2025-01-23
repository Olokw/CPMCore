package net.balacobaco.cpmcore.comandos;

import net.balacobaco.cpmcore.CPMCore;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ReloadSkins implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            if (sender.isOp()){
                Bukkit.getOnlinePlayers().forEach(player -> {
                    CPMCore.instance.getGeyserUtils().tryApplySkin(player);
                });
            }
        }
        return true;
    }
}
