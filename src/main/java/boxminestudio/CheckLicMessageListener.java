package boxminestudio;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.List;

public class CheckLicMessageListener implements Listener {

    private final chatcheck plugin;
    public CheckLicMessageListener(chatcheck plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPrivateMessage(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        String message = event.getMessage().toLowerCase();
        List<String> bannedmessage = plugin.getConfig().getStringList("bannedmessage");
        List<String> punishment = plugin.getConfig().getStringList("punishment");


        if (message.startsWith("/msg ") || message.startsWith("/tell ")) {
            String[] args = event.getMessage().split(" ");
            if (args.length >= 3) {
                Player recipient = Bukkit.getPlayer(args[1]);
                if (recipient != null) {
                    String msg = event.getMessage().substring(args[0].length() + args[1].length() + 2);
                    for (String word : bannedmessage) {
                        if (msg.toLowerCase().contains(word.toLowerCase())) {
                            event.setCancelled(true);
                            Bukkit.getScheduler().runTask(plugin, () -> {
                                for (String pucommand : punishment) {
                                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), pucommand.replace("%player_name%", player.getName()));
                                }
                            });
                            break;
                        }
                    }
                }
            }
        }
    }


}
