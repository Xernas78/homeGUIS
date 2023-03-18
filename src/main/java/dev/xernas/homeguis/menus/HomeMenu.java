package dev.xernas.homeguis.menus;

import dev.xernas.homeguis.Home;
import dev.xernas.homeguis.HomeGUIS;
import dev.xernas.menumanager.InventorySizes;
import dev.xernas.menumanager.Menu;
import dev.xernas.utils.ItemUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeMenu extends Menu {
    public HomeMenu(Player owner) {
        super(owner);
    }

    @Override
    public int getSize() {
        return InventorySizes.NORMAL;
    }

    @Override
    public String getTitle() {
        return ChatColor.BOLD + "" + ChatColor.DARK_AQUA + "Homes";
    }

    @Override
    public void handleMenuClicks(InventoryClickEvent e) {
        if (e.getCurrentItem().getItemMeta().getPersistentDataContainer().has(new NamespacedKey(HomeGUIS.getInstance(), "home"), PersistentDataType.STRING)) {
            String homeName = e.getCurrentItem().getItemMeta().getPersistentDataContainer().get(new NamespacedKey(HomeGUIS.getInstance(), "home"), PersistentDataType.STRING);
            List<Home> playerHomes = HomeGUIS.getHomeManager().getPlayerHomes(owner.getUniqueId());
            playerHomes.forEach(home -> {
                if (homeName.equalsIgnoreCase(home.getName())) {
                    owner.teleport(home.getPosition());
                }
            });
        }
    }

    @Override
    public Map<Integer, ItemStack> getContent() {
        Map<Integer, ItemStack> map = setFillerGlass(ItemUtils.createCustom(Material.GRAY_STAINED_GLASS_PANE, " "));
        for (int slot = 10; slot < 17; slot++) {
            map.put(slot, null);
        }
        List<Home> playerHomes = HomeGUIS.getHomeManager().getPlayerHomes(owner.getUniqueId());
        if (playerHomes != null) {
            int slot = 10;
            for (int home = 0; home < playerHomes.size(); home++) {
                map.put(slot, ItemUtils.createCustom(Material.RED_BED, ChatColor.GOLD + playerHomes.get(home).getName(), "home", playerHomes.get(home).getName()));
                slot++;
            }
        }
        return map;
    }
}
