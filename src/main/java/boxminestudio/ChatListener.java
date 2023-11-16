package boxminestudio;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.List;

public class ChatListener implements Listener {

    private final chatcheck plugin;
    public ChatListener(chatcheck plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        String message = event.getMessage();
        Player player = event.getPlayer();

        List<String> bannedmessage = plugin.getConfig().getStringList("bannedmessage");
        List<String> punishment = plugin.getConfig().getStringList("punishment");


        for (String word : bannedmessage) {
            if (message.contains(word)) {
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