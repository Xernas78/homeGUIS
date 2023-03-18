package dev.xernas.menumanager;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public abstract class Menu implements InventoryHolder {
    protected Player owner;

    public Menu(Player owner) {
        this.owner = owner;
    }

    public abstract int getSize();

    public abstract String getTitle();

    public abstract void handleMenuClicks(InventoryClickEvent e);

    public abstract Map<Integer, ItemStack> getContent();

    public Map<Integer, ItemStack> setFillerGlass(ItemStack filler) {
        Map<Integer, ItemStack> map = new HashMap<>();
        for (int i = 0; i < getSize(); i++) {
            map.put(i, filler);
        }
        return map;
    }

    public void open() {
        Inventory inventory = getInventory();

        Map<Integer, ItemStack> items = getContent();
        items.forEach(inventory::setItem);

        owner.openInventory(inventory);
    }

    public Inventory getInventory() {
        return Bukkit.createInventory(this, getSize(), getTitle());
    }

}
