package net.balacobaco.cpmcore.comandos;

import net.balacobaco.cpmcore.Message;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AddWhitelist implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            if (args.length == 1){
                Bukkit.getOfflinePlayer(args[0]).setWhitelisted(true);
                sender.sendMessage(Message.process("<yellow>Jogador " + args[0] + " adicionado na lista branca."));
            }
        }
        return true;
    }
}
