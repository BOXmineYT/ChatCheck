package boxminestudio;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.List;
import java.util.logging.Logger;


public final class chatcheck extends JavaPlugin implements Listener {

    public static JavaPlugin instance;
    private File ConfigFile;
    java.util.logging.Logger log = Logger.getLogger("Minecraft");

    private String user;

    String configVersion = getConfig().getString("version");

    @Override
    public void onEnable() {


        new UpdateChecker(this, 113569).getVersion(version -> {
            if (this.getDescription().getVersion().equals(version)) {
                getLogger().info("There is not a new update available.");
            } else {
                getLogger().info("A new version of the plugin has been released. version: " + version );
                getLogger().info("Download - https://www.spigotmc.org/resources/chatcheck.113569/");
            }
        });

        File config = new File(getDataFolder() + File.separator + "config.yml");
        if(!config.exists()){
            getLogger().info("Creating config....");
            getConfig().options().copyDefaults(true);
            saveDefaultConfig();
        }

        getServer().getPluginManager().registerEvents(new ChatListener(this), this);
        this.getServer().getPluginManager().registerEvents(this, this);
        getCommand("cc").setExecutor(new MainCommand(this));
        this.getServer().getPluginManager().addPermission(new Permission(getConfig().getString("permissions.reload")));
        this.getServer().getPluginManager().addPermission(new Permission(getConfig().getString("permissions.help")));
        this.getServer().getPluginManager().addPermission(new Permission("chatcheck.update"));



        String requiredVersion = "1.1";
        if (configVersion == null || !configVersion.equals(requiredVersion)) {
            // Обработка, если версия конфига не соответствует требуемой
            getLogger().info("Outdated config.yml! Creating a new!");
            this.ConfigFile = new File(this.getDataFolder(), "config.yml");
            this.ConfigFile.renameTo(new File(this.getDataFolder(), "config.yml.old"));
            this.saveResource("config.yml", false);
        } else {
            // Обработка, если версия конфига соответствует требуемой
            getLogger().info("The config.yml file is not outdated yet.");
        }

    }







    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (this.getConfig().getBoolean("update-check") && player.hasPermission("chatcheck.update")) {
            (new UpdateChecker(this, 113569)).getVersion((version) -> {
                if (!this.getDescription().getVersion().equals(version)) {

                    List<String> messageList = getConfig().getStringList("new-version");
                    for (String message : messageList) {
                        message = message.replace("%version%", version).replace("&", "§");
                        player.sendMessage(message);
                    }
                }

            });
        }

    }
}
