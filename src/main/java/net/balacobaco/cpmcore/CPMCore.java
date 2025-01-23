package net.balacobaco.cpmcore;

import net.balacobaco.cpmcore.comandos.AddWhitelist;
import net.balacobaco.cpmcore.comandos.PrisaoCmd;
import net.balacobaco.cpmcore.comandos.ReloadSkins;
import net.balacobaco.cpmcore.utils.GeyserUtils;
import net.balacobaco.cpmcore.utils.HelderFalas;
import net.balacobaco.cpmcore.utils.PrisaoJsonUtils;
import net.sacredlabyrinth.phaed.simpleclans.SimpleClans;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.geysermc.geyser.api.GeyserApi;

import java.util.Random;

public final class CPMCore extends JavaPlugin {

    public static CPMCore instance;
    private SimpleClans sc;
    private HelderFalas helderFalas;
    private GeyserApi geyser;
    private PrisaoJsonUtils prisaoJsonUtils;
    private GeyserUtils geyserUtils;

    @Override
    public void onEnable() {
        instance = this;
        Plugin plug = CPMCore.instance.getServer().getPluginManager().getPlugin("SimpleClans");
        if (plug != null) {
            sc = (SimpleClans) plug;
        }

        geyser = GeyserApi.api();

        geyserUtils = new GeyserUtils();
        helderFalas = new HelderFalas();
        prisaoJsonUtils = new PrisaoJsonUtils();

        startAnnouncementsTimer();

        this.getCommand("listabranca").setExecutor(new AddWhitelist());
        this.getCommand("reloadskins").setExecutor(new ReloadSkins());
        this.getCommand("prisao").setExecutor(new PrisaoCmd());

        Listeners.register();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
    public SimpleClans getSCPlugin() {
        return sc;
    }

    public GeyserApi getGeyser() {
        return geyser;
    }

    public GeyserUtils getGeyserUtils() {
        return geyserUtils;
    }

    public PrisaoJsonUtils getPrisaoJsonUtils() {
        return prisaoJsonUtils;
    }

    private void startAnnouncementsTimer(){
        new BukkitRunnable() {
            @Override
            public void run() {
                Bukkit.broadcast(Message.process("<click:open_url:https://discord.gg/2g6ZMWzyYW>Entre no nosso discord! <blue>https://discord.gg/2g6ZMWzyYW"));
            }
        }.runTaskTimer(instance, 0, 20 * 60 * 15);

        new BukkitRunnable() {
            @Override
            public void run() {
                Random random = new Random();
                int index = random.nextInt(helderFalas.getFrases().size());
                String fala = helderFalas.getFrases().get(index);
                if (Bukkit.getWorld("prisao").getPlayerCount() > 0){
                    Bukkit.getWorld("prisao").getPlayers().forEach(player -> {
                        player.sendMessage(Message.process("<Hélder BOT> <red>" + fala));
                    });
                }
            }
        }.runTaskTimer(instance, 0, 20 * 15);
    }
}
