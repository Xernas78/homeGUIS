package dev.xernas.homeguis.commands;

import dev.xernas.homeguis.HomeGUIS;
import dev.xernas.homeguis.menus.HomeMenu;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SethomeCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            if (args.length == 1) {
                HomeGUIS.getHomeManager().addHome(player.getUniqueId(), player.getLocation(), args[0]);
            } else {
                player.sendMessage("/sethome [name]");
            }
        }
        else {
            sender.sendMessage(ChatColor.RED + "La commande ne fonctionne qu'avec des joueurs !");
        }

        return true;
    }
}
