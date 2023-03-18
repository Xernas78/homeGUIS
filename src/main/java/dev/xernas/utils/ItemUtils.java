package dev.xernas.utils;

import dev.xernas.homeguis.HomeGUIS;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class ItemUtils {
    public static ItemStack createCustom(Material material, String name, String id, String value) {
        ItemStack itemStack = new ItemStack(material);
        ItemMeta im = itemStack.getItemMeta();
        im.setDisplayName(name);
        if (id != null && value != null) {
            PersistentDataContainer dataContainer = im.getPersistentDataContainer();
            dataContainer.set(new NamespacedKey(HomeGUIS.getInstance(), id), PersistentDataType.STRING, value);
        }
        itemStack.setItemMeta(im);
        return itemStack;
    }

    public static ItemStack createCustom(Material material, String name) {
        return createCustom(material, name, null, null);
    }

}
