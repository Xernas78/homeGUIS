package dev.xernas.homeguis;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldType;
import org.bukkit.generator.WorldInfo;

public class Home {

    private final Location position;
    private String name;

    public Home(Location position, String name) {
        this.position = position;
        this.name = name;
    }

    public Location getPosition() {
        return position;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static Home createDummy() {
        return new Home(new Location(Bukkit.getWorlds().get(0), 0, 0, 0), "dummy");
    }

}
