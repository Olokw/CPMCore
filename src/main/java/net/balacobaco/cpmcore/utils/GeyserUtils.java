package net.balacobaco.cpmcore.utils;

import net.balacobaco.cpmcore.CPMCore;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.geysermc.api.connection.Connection;
import org.geysermc.geyser.api.GeyserApi;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GeyserUtils {
    public void tryApplySkin(Player player){
        if (!CPMCore.instance.getGeyser().isBedrockPlayer(player.getUniqueId())) return;
        GeyserApi geyser = CPMCore.instance.getGeyser();
        Connection connection = geyser.connectionByUuid(player.getUniqueId());
        String xuid = connection.xuid();
        String name = player.getName();

        new BukkitRunnable() {
            @Override
            public void run() {
                try {
                    // Step 1: Fetch the texture ID from the Geyser API

                    String apiUrl = "https://api.geysermc.org/v2/skin/" + xuid;
                    URL url1 = new URL(apiUrl);
                    HttpURLConnection connection = (HttpURLConnection) url1.openConnection();
                    connection.setRequestMethod("GET");

                    BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String inputLine;
                    StringBuilder content = new StringBuilder();

                    while ((inputLine = in.readLine()) != null) {
                        content.append(inputLine);
                    }

                    in.close();
                    connection.disconnect();
                    String response = content.toString();
                    String textureId = parseTextureIdFromResponse(response);

                    if (textureId != null) {
                        // Step 2: Form the URL with the texture ID
                        String url = "http://textures.minecraft.net/texture/" + textureId;
                        // Dispatch the command to set the skin on the main server thread
                        Bukkit.getScheduler().runTask(CPMCore.instance, () -> {
                            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "skin set " + url + " " + name);
                        });
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }.runTaskAsynchronously(CPMCore.instance);
    }

    private String parseTextureIdFromResponse(String response) {
        // Simple parsing of the texture_id from the JSON response
        String searchKey = "\"texture_id\":\"";
        int startIndex = response.indexOf(searchKey) + searchKey.length();
        int endIndex = response.indexOf("\"", startIndex);
        if (startIndex > searchKey.length() - 1 && endIndex > startIndex) {
            return response.substring(startIndex, endIndex);
        }
        return null;
    }
}
