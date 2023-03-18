package dev.xernas.homeguis.commands;

import dev.xernas.homeguis.Home;
import dev.xernas.homeguis.HomeGUIS;
import dev.xernas.homeguis.menus.HomeMenu;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class HomeCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player player) {
            if (args.length == 0) {
                HomeMenu menu = new HomeMenu(player);
                menu.open();
            }
            else if (args.length == 1) {
                List<Home> homes = HomeGUIS.getHomeManager().getPlayerHomes(player.getUniqueId());
                AtomicBoolean found = new AtomicBoolean(false);
                if (homes != null) {
                    homes.forEach(home -> {
                        if (home.getName().equalsIgnoreCase(args[0])) {
                            found.set(true);
                            player.teleport(home.getPosition());
                        }
                    });
                    if (!found.get()) {
                        player.sendMessage(ChatColor.RED + "Le home " + args[0] + " n'existe pas");
                    }
                }
                else {
                    player.sendMessage(ChatColor.RED + "Vous n'avez aucun home");
                }
            }
            else {
                player.sendMessage("/home [name]");
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
            if (homes != null) {
                homes.forEach(home -> {
                    options.add(home.getName());
                });
            }
        }

        return options;
    }
}
