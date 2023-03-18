package dev.xernas.homeguis;

import dev.xernas.homeguis.commands.DelhomeCommand;
import dev.xernas.homeguis.commands.HomeCommand;
import dev.xernas.homeguis.commands.SethomeCommand;
import dev.xernas.menumanager.listeners.ClickEvent;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public final class HomeGUIS extends JavaPlugin {

    private static HomeManager homeManager;
    private static HomeGUIS instance;

    @Override
    public void onEnable() {
        instance = this;
        File homeFile = new File(this.getDataFolder(), "/homes.yml");
        if (!this.getDataFolder().exists()) {
            this.getDataFolder().mkdir();
        }
        if (!homeFile.exists()) {
            try {
                homeFile.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        homeManager = new HomeManager(homeFile);
        registerCommands();
        registerListeners();
    }

    @Override
    public void onDisable() {

    }

    private void registerCommands() {
        this.getCommand("home").setExecutor(new HomeCommand());
        this.getCommand("sethome").setExecutor(new SethomeCommand());
        this.getCommand("delhome").setExecutor(new DelhomeCommand());
    }

    private void registerListeners() {
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new ClickEvent(), this);
    }

    public static HomeGUIS getInstance() {
        return instance;
    }

    public static HomeManager getHomeManager() {
        return homeManager;
    }
}
