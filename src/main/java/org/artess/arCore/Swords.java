package org.artess.arCore;

import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.event.Listener;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class Swords implements Listener {

    public static Swords instance = new Swords();

    List<Material> MaterialList = new ArrayList<>(Arrays.asList(Material.WOODEN_SWORD, Material.STONE_SWORD, Material.IRON_SWORD, Material.GOLDEN_SWORD, Material.DIAMOND_SWORD, Material.NETHERITE_SWORD));

    private ItemStack sword(String name, String title, int material, int damage, int level, double speed, int critDamage, double critChance, int special, int rarity) {
        ItemStack sword = new ItemStack(MaterialList.get(material));
        ItemMeta meta = sword.getItemMeta();
        String s = ArCore.getInstance().swords.getString("RarityList." + rarity + ".Color");
        meta.setDisplayName(s + "§l" + title);
        List<String> lore = new ArrayList<>();
        lore.add("§7Урон: " + s + damage);
        lore.add("§7Уровень: " + s + level);
        lore.add("§7Скорость: " + s + speed);
        lore.add("§7Критический урон: " + s + critDamage + ", " + critChance*100 + "%");
        if (special != 0) {
            lore.add(" ");
            lore.add("§7Особенность: " + ArCore.getInstance().swords.getString("SpecialList." + special + ".Color") +
                    ArCore.getInstance().swords.getString("SpecialList." + special + ".Name"));
        }
        lore.add(" ");
        lore.add(ArCore.getInstance().swords.getString("RarityList." + rarity + ".Color") + "§l" +
                ArCore.getInstance().swords.getString("RarityList." + rarity + ".Name"));
        meta.setLore(lore);
        meta.setUnbreakable(true);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        int v = ArCore.getInstance().swords.getStringList("RarityList").indexOf(name);
        meta.setVersion(v);
        meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, new AttributeModifier(UUID.randomUUID(), "generic.attackDamage", damage, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND));
        meta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, new AttributeModifier(UUID.randomUUID(), "generic.attackSpeed", speed, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND));
        sword.setItemMeta(meta);
        return sword;
    }

    public List<String> listSwords() {
        List<String> list = new ArrayList<>();
        list.addAll(ArCore.getInstance().swords.getConfigurationSection("Swords").getKeys(false));
        return list;
    }

    public void createSword(String name, String title, int material, int damage, int level, double speed, int critDamage, double critChance, int special, int rarity) {
        ArCore.getInstance().swords.set("Swords." + name + ".Name", name);
        ArCore.getInstance().swords.set("Swords." + name + ".Title", title);
        ArCore.getInstance().swords.set("Swords." + name + ".Material", material);
        ArCore.getInstance().swords.set("Swords." + name + ".Damage", damage);
        ArCore.getInstance().swords.set("Swords." + name + ".Level", level);
        ArCore.getInstance().swords.set("Swords." + name + ".Speed", speed);
        ArCore.getInstance().swords.set("Swords." + name + ".CritDamage", critDamage);
        ArCore.getInstance().swords.set("Swords." + name + ".CritChance", critChance);
        ArCore.getInstance().swords.set("Swords." + name + ".Special", special);
        ArCore.getInstance().swords.set("Swords." + name + ".Rarity", rarity);
        ArCore.getInstance().saveSwords();
    }

    public void deleteSword(String name) {
        ArCore.getInstance().swords.set("Swords." + name, null);
        ArCore.getInstance().saveSwords();
    }

    public ItemStack findSword(String name) {
        List<String> Swords = ArCore.getInstance().swords.getConfigurationSection("Swords").getKeys(false).stream().toList();
        if(Swords.contains(name)) {
            ItemStack sword = sword(ArCore.getInstance().swords.getString("Swords." + name + ".Name"),
                    ArCore.getInstance().swords.getString("Swords." + name + ".Title"),
                    ArCore.getInstance().swords.getInt("Swords." + name + ".Material"),
                    ArCore.getInstance().swords.getInt("Swords." + name + ".Damage"),
                    ArCore.getInstance().swords.getInt("Swords." + name + ".Level"),
                    ArCore.getInstance().swords.getDouble("Swords." + name + ".Speed"),
                    ArCore.getInstance().swords.getInt("Swords." + name + ".CritDamage"),
                    ArCore.getInstance().swords.getDouble("Swords." + name + ".CritChance"),
                    ArCore.getInstance().swords.getInt("Swords." + name + ".Special"),
                    ArCore.getInstance().swords.getInt("Swords." + name + ".Rarity"));
            return sword;
        }
        return null;
    }
}
