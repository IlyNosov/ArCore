package org.artess.arCore;

import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class Armor implements Listener {

    public static Armor instance = new Armor();

    List<List<Material>> MaterialList = List.of(
            List.of(Material.LEATHER_HELMET, Material.LEATHER_CHESTPLATE, Material.LEATHER_LEGGINGS, Material.LEATHER_BOOTS),
            List.of(Material.CHAINMAIL_HELMET, Material.CHAINMAIL_CHESTPLATE, Material.CHAINMAIL_LEGGINGS, Material.CHAINMAIL_BOOTS),
            List.of(Material.IRON_HELMET, Material.IRON_CHESTPLATE, Material.IRON_LEGGINGS, Material.IRON_BOOTS),
            List.of(Material.GOLDEN_HELMET, Material.GOLDEN_CHESTPLATE, Material.GOLDEN_LEGGINGS, Material.GOLDEN_BOOTS),
            List.of(Material.DIAMOND_HELMET, Material.DIAMOND_CHESTPLATE, Material.DIAMOND_LEGGINGS, Material.DIAMOND_BOOTS),
            List.of(Material.NETHERITE_HELMET, Material.NETHERITE_CHESTPLATE, Material.NETHERITE_LEGGINGS, Material.NETHERITE_BOOTS)
    );

    private ItemStack armor(String name, String title, int type, int material, int health, int level, int special, int rarity) {
        ItemStack armor = new ItemStack(MaterialList.get(type).get(material));
        ItemMeta meta = armor.getItemMeta();
        String s = ArCore.getInstance().items.getString("RarityList." + rarity + ".Color");
        meta.setDisplayName(s + "§l" + title);
        List<String> lore = new ArrayList<>();
        lore.add("§cЗдоровье: +" + health);
        lore.add("§7Уровень: " + s + level);
        if (special != 0) {
            lore.add(" ");
            lore.add("§7Особенность: " + ArCore.getInstance().items.getString("SpecialList." + special + ".Color") +
                    ArCore.getInstance().items.getString("SpecialList." + special + ".Name"));
        }
        lore.add(" ");
        lore.add(ArCore.getInstance().items.getString("RarityList." + rarity + ".Color") + "§l" +
                ArCore.getInstance().items.getString("RarityList." + rarity + ".Name"));
        meta.setLore(lore);
        meta.setUnbreakable(true);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        armor.setItemMeta(meta);
        return armor;
    }

    public List<String> listArmor() {
        return List.copyOf(ArCore.getInstance().items.getConfigurationSection("Armor").getKeys(false));
    }

    public void createArmor(String name, String title, int type, int material, int health, int level, int special, int rarity) {
        ArCore.getInstance().items.set("Armor." + name + ".Name", name);
        ArCore.getInstance().items.set("Armor." + name + ".Title", title);
        ArCore.getInstance().items.set("Armor." + name + ".Type", type);
        ArCore.getInstance().items.set("Armor." + name + ".Material", material);
        ArCore.getInstance().items.set("Armor." + name + ".Health", health);
        ArCore.getInstance().items.set("Armor." + name + ".Level", level);
        ArCore.getInstance().items.set("Armor." + name + ".Special", special);
        ArCore.getInstance().items.set("Armor." + name + ".Rarity", rarity);
        ArCore.getInstance().saveArmor();
    }

    public void removeArmor(String name) {
        ArCore.getInstance().items.set("Armor." + name, null);
        ArCore.getInstance().saveArmor();
    }

    public ItemStack findArmor(String name) {
        List<String> Armor = ArCore.getInstance().items.getConfigurationSection("Armor").getKeys(false).stream().toList();
        if (Armor.contains(name)) {
            ItemStack armor = armor(
                    ArCore.getInstance().items.getString("Armor." + name + ".Name"),
                    ArCore.getInstance().items.getString("Armor." + name + ".Title"),
                    ArCore.getInstance().items.getInt("Armor." + name + ".Type"),
                    ArCore.getInstance().items.getInt("Armor." + name + ".Material"),
                    ArCore.getInstance().items.getInt("Armor." + name + ".Health"),
                    ArCore.getInstance().items.getInt("Armor." + name + ".Level"),
                    ArCore.getInstance().items.getInt("Armor." + name + ".Special"),
                    ArCore.getInstance().items.getInt("Armor." + name + ".Rarity")
            );
            return armor;
        } else {
            return null;
        }
    }
}
