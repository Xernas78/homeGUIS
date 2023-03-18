package dev.xernas.homeguis;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class HomeManager {

    private final Map<UUID, List<Home>> homes;
    private final YamlConfiguration configuration;
    private final File homeFile;

    public HomeManager(File homeFile) {
        homes = new HashMap<>();
        this.homeFile = homeFile;
        configuration = YamlConfiguration.loadConfiguration(homeFile);
        if (configuration.getConfigurationSection("homes") == null) {
            configuration.createSection("homes");
        }
        configuration.getConfigurationSection("homes").getKeys(false).forEach(uuid -> {
            List<Home> homeList = new ArrayList<>();
            configuration.getConfigurationSection("homes." + uuid).getKeys(false).forEach(homeName -> {
                String world = configuration.getString("homes." + uuid + "." + homeName + ".world");
                Double x = configuration.getDouble("homes." + uuid + "." + homeName + ".x");
                Double y = configuration.getDouble("homes." + uuid + "." + homeName + ".y");
                Double z = configuration.getDouble("homes." + uuid + "." + homeName + ".z");
                String pitch = configuration.getString("homes." + uuid + "." + homeName + ".pitch");
                String yaw = configuration.getString("homes." + uuid + "." + homeName + ".yaw");
                Location position = new Location(Bukkit.getWorld(world), x, y, z, Float.parseFloat(pitch), Float.parseFloat(yaw));
                Home home = new Home(position, homeName);
                homeList.add(home);
            });
            homes.put(UUID.fromString(uuid), homeList);
        });
    }

    public void addHome(UUID player, Location position, String name) {
        List<Home> homeList;
        if (homes.get(player) != null) {
            homeList = homes.get(player);
        }
        else {
            homeList = new ArrayList<>();
        }
        Player playerObj = (Player) Bukkit.getOfflinePlayer(player);
        AtomicBoolean canAdd = new AtomicBoolean(true);
        homeList.forEach(home -> {
            if (home.getName().equalsIgnoreCase(name)) {
                playerObj.sendMessage(ChatColor.RED + "Ce home existe déjà");
                canAdd.set(false);
            }
        });
        if (!(homeList.size() < 7)) {
            playerObj.sendMessage(ChatColor.RED + "Vous avez atteint le maximum de homes permis par le serveur");
            canAdd.set(false);
        }
        if (canAdd.get()) {
            homeList.add(new Home(position, name));
            configuration.createSection("homes." + player.toString() + "." + name);
            configuration.set("homes." + player.toString() + "." + name + ".x", position.getX());
            configuration.set("homes." + player.toString() + "." + name + ".y", position.getY());
            configuration.set("homes." + player.toString() + "." + name + ".z", position.getZ());
            configuration.set("homes." + player.toString() + "." + name + ".world", position.getWorld().getName());
            configuration.set("homes." + player.toString() + "." + name + ".yaw", String.valueOf(position.getYaw()));
            configuration.set("homes." + player.toString() + "." + name + ".pitch", String.valueOf(position.getPitch()));
            try {
                configuration.save(homeFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
            playerObj.sendMessage(ChatColor.GREEN + "Vous avez bien créer le home " + name);
        }
        homes.put(player, homeList);
    }

    public void removeHome(UUID player, String name) {
        List<Home> homeList;
        if (homes.get(player) != null) {
            homeList = homes.get(player);
        }
        else {
            homeList = new ArrayList<>();
        }
        Player playerObj = (Player) Bukkit.getOfflinePlayer(player);
        AtomicBoolean found = new AtomicBoolean(false);
        AtomicReference<Home> objToRemove = new AtomicReference<>(Home.createDummy());
        homeList.forEach(home -> {
            if (!found.get()) {
                if (home.getName().equalsIgnoreCase(name)) {
                    objToRemove.set(home);
                    found.set(true);
                    configuration.set("homes." + player.toString() + "." + name, null);
                    try {
                        configuration.save(homeFile);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    playerObj.sendMessage(ChatColor.GREEN + "Vous avez bien enlevé le home " + name);
                }
            }
        });
        homeList.remove(objToRemove.get());
        if (!found.get()) {
            playerObj.sendMessage(ChatColor.RED + "Le home " + name + " n'existe pas");
        }
        homes.put(player, homeList);
    }

    public List<Home> getPlayerHomes(UUID player) {
        return homes.get(player);
    }

}
