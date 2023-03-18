package dev.xernas.menumanager;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public abstract class PaginatedMenu extends Menu {

    protected Map<UUID, Integer> page = new HashMap<>();
    protected int maxItemsPerPage = 28;
    protected Player owner;

    public PaginatedMenu(Player owner) {
        super(owner);
        this.owner = owner;
        for (Player pl : Bukkit.getOnlinePlayers()) {
            page.put(pl.getUniqueId(), 0);
        }
    }

    @Override
    public int getSize() {
        return 54;
    }

    public Map<Integer, ItemStack> setBorder(ItemStack border) {
        Map<Integer, ItemStack> map = new HashMap<>();
        for (int i = 0; i < 9; i++) {
            map.put(i, border);
        }
        map.put(9, border);
        map.put(17, border);
        map.put(18, border);
        map.put(26, border);
        map.put(27, border);
        map.put(35, border);
        map.put(36, border);
        map.put(44, border);
        for (int i = 45; i < 54; i++) {
            map.put(i, border);
        }

        return map;
    }

    public void nextPage() {
        page.put(owner.getUniqueId(), page.get(owner.getUniqueId()) + 1);
        this.open();
    }
    public void previousPage() {
        page.put(owner.getUniqueId(), page.get(owner.getUniqueId()) - 1);
        this.open();
    }
}
