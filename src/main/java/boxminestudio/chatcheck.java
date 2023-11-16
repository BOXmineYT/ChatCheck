package boxminestudio;

import org.bukkit.block.Block;
import org.bukkit.event.Listener;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


public final class chatcheck extends JavaPlugin implements Listener {

    private final List<Block> lastTrapdoors = new ArrayList();

    public static JavaPlugin instance;
    private File ConfigFile;
    java.util.logging.Logger log = Logger.getLogger("Minecraft");

    private String user;

    String configVersion = getConfig().getString("version");

    @Override
    public void onEnable() {
        // Plugin startup logic

        File config = new File(getDataFolder() + File.separator + "config.yml");
        if(!config.exists()){
            getLogger().info("Creating config....");
            getConfig().options().copyDefaults(true);
            saveDefaultConfig();
        }

        getServer().getPluginManager().registerEvents(new ChatListener(this), this);
        getCommand("cc").setExecutor(new MainCommand(this));
        this.getServer().getPluginManager().addPermission(new Permission(getConfig().getString("permissions.reload")));
        this.getServer().getPluginManager().addPermission(new Permission(getConfig().getString("permissions.help")));
        //this.getServer().getPluginManager().addPermission(new Permission("chatcheck.update"));



        String requiredVersion = "1.0"; // Минимальная требуемая версия конфига

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


    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
