package org.artess.arCore;

import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class Items implements Listener {

    public static Items instance = new Items();

    public ItemStack item(String name, String title, String material, int rarity, String lore, int item_type) {
        Material mat = Material.getMaterial(material);
        if (mat == null) return null;
        if (item_type > 2) rarity += 5;
        ItemStack item = new ItemStack(mat);
        ItemMeta meta = item.getItemMeta();
        String s = ArCore.getInstance().items.getString("RarityList." + rarity + ".Color");
        meta.setDisplayName(s + "§l" + title);
        List<String> loreList = new ArrayList<>();
        loreList.add("§7" + lore);
        loreList.add(" ");
        loreList.add(ArCore.getInstance().items.getString("RarityList." + rarity + ".Color") + "§l" +
                ArCore.getInstance().items.getString("RarityList." + rarity + ".Name") + " " +
                ArCore.getInstance().items.getString("TypeList." + item_type + ".Name"));
        meta.setLore(loreList);
        return item;
    }

    public List<String> listItems() {
        return List.copyOf(ArCore.getInstance().items.getConfigurationSection("Items").getKeys(false));
    }

    public void createItem(String name, String title, String material, int rarity, String lore, int item_type) {
        ArCore.getInstance().items.set("Items." + name + ".Name", name);
        ArCore.getInstance().items.set("Items." + name + ".Title", title);
        ArCore.getInstance().items.set("Items." + name + ".Material", material);
        ArCore.getInstance().items.set("Items." + name + ".Rarity", rarity);
        ArCore.getInstance().items.set("Items." + name + ".Lore", lore);
        ArCore.getInstance().items.set("Items." + name + ".Type", item_type);
        ArCore.getInstance().saveItems();
    }

    public void removeItem(String name) {
        ArCore.getInstance().items.set("Items." + name, null);
        ArCore.getInstance().saveItems();
    }

    public ItemStack findItem(String name) {
        List<String> items = listItems();
        if (items.contains(name)) {
            ItemStack item = item(name, ArCore.getInstance().items.getString("Items." + name + ".Title"),
                    ArCore.getInstance().items.getString("Items." + name + ".Material"),
                    ArCore.getInstance().items.getInt("Items." + name + ".Rarity"),
                    ArCore.getInstance().items.getString("Items." + name + ".Lore"),
                    ArCore.getInstance().items.getInt("Items." + name + ".Type"));
            return item;
        }
        return null;
    }
}