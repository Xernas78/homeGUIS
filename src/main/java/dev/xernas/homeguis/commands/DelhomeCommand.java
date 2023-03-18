package dev.xernas.homeguis.commands;

import dev.xernas.homeguis.Home;
import dev.xernas.homeguis.HomeGUIS;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class DelhomeCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player player) {
            if (args.length == 1) {
                HomeGUIS.getHomeManager().removeHome(player.getUniqueId(), args[0]);
            } else {
                player.sendMessage("/delhome [name]");
            }
        }
        else {
            sender.sendMessage(ChatColor.RED + "La commande ne fonctionne qu'avec des joueurs !");
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> options = new ArrayList<>();
        if (sender instanceof Player player) {
            List<Home> homes = HomeGUIS.getHomeManager().getPlayerHomes(player.getUniqueId());
            homes.forEach(home -> {
                options.add(home.getName());
            });
        }
        return options;
    }
}
