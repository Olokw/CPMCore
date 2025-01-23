package net.balacobaco.cpmcore.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.balacobaco.cpmcore.CPMCore;
import net.balacobaco.cpmcore.Message;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PrisaoJsonUtils {

    // Poderia fazer no MySQL. Mas por ser um servidor privado, não importa muito.
    // Então vai em json mesmo, dá nada.

    public void saveWheatConfig(PrisaoConfig prisaoConfig, UUID uuid) {

        File folder = new File(CPMCore.instance.getDataFolder(), "/prisao");
        if (!folder.exists()) {
            folder.mkdirs();
        }

        Gson json = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create();

        File file = new File(folder, uuid + ".json");


        try (FileWriter writer = new FileWriter(file)) {
            json.toJson(prisaoConfig, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getWheatRemaining(UUID uuid){
        Gson json = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
        File file = new File(CPMCore.instance.getDataFolder() + "/prisao", uuid + ".json");
        try (FileReader reader = new FileReader(file)) {
            PrisaoConfig prisaoConfig = json.fromJson(reader, PrisaoConfig.class);
            return prisaoConfig.getPontos();
        } catch (IOException ignore) {
        }
        return 0;
    }

    public void removeWheatPoint(UUID uuid){
        if (getWheatRemaining(uuid) != 0){
            if (getWheatRemaining(uuid) - 1 > 0){
                saveWheatConfig(new PrisaoConfig(getWheatRemaining(uuid) - 1), uuid);
                Bukkit.getPlayer(uuid).sendActionBar(Message.process("<gold>" + (getWheatRemaining(uuid)) + " trigos restantes." ));
            }else{
                File file = new File(CPMCore.instance.getDataFolder(), "/prisao/" + uuid + ".json");
                if (file.exists()){
                    file.delete();
                }
                Bukkit.getPlayer(uuid).teleport(new Location(Bukkit.getWorld("world"),-65.5, 77, 19.5, 0, 0), PlayerTeleportEvent.TeleportCause.COMMAND);
                Bukkit.broadcast(Message.process("<aqua>O jogador " + Bukkit.getPlayer(uuid).getName() + " está liberto!"));
                Bukkit.getPlayer(uuid).playSound(Bukkit.getPlayer(uuid), Sound.UI_TOAST_CHALLENGE_COMPLETE, 1, 1);
            }
        }
    }
}
