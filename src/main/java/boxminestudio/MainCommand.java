package boxminestudio;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;


public class MainCommand implements CommandExecutor {

    Plugin plugin;
    public MainCommand(chatcheck plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(args.length == 0) {
            sender.sendMessage("/cc (reload/help)");
            return true;
        }
        if(args[0].equalsIgnoreCase("reload")) {
            if (!sender.hasPermission(plugin.getConfig().getString("permissions.reload"))) {
                String s = ChatColor.translateAlternateColorCodes('&',
                        plugin.getConfig().getString("messages.NoPermisionreload"));
                sender.sendMessage(s);
                return true;
            }
            plugin.reloadConfig();
            String s = ChatColor.translateAlternateColorCodes('&',
                    plugin.getConfig().getString("messages.Reloadplugin"));
            sender.sendMessage(s);
            return true;
        }
        if(args[0].equalsIgnoreCase("help")) {
            if (!sender.hasPermission(plugin.getConfig().getString("permissions.help"))) {
                String s = ChatColor.translateAlternateColorCodes('&',
                        plugin.getConfig().getString("messages.NoPermisionhelp"));
                sender.sendMessage(s);
                return true;
            } else {

                sender.sendMessage(ChatColor.GREEN + "ChatCheck 1.0" + ChatColor.WHITE + " by " + ChatColor.RED + "BOXmineYT" + ChatColor.WHITE + " by " + ChatColor.RED + "NateAles");
                sender.sendMessage(ChatColor.GOLD + "/cc reload " + ChatColor.DARK_GREEN + "-" + plugin.getConfig().getString("help.reload"));
                sender.sendMessage(ChatColor.GOLD + "/cc help " + ChatColor.DARK_GREEN + "-" + plugin.getConfig().getString("help.help"));
                return true;
            }
        }

        return false;
    }



}