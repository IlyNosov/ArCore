package org.artess.arCore.Old;

import org.artess.arCore.ArCore;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class SwordsOld implements Listener {

    List<String> RarityList = new ArrayList<>(Arrays.asList("§a§lОБЫЧНЫЙ", "§e§lРЕДКИЙ", "§d§lЭПИЧЕСКИЙ", "§6§lЛЕГЕНДАРНЫЙ", "§5§lМИФИЧЕСКИЙ"));
    List<String> RarityColorList = new ArrayList<>(Arrays.asList("§a§l", "§e§l", "§d§l", "§6§l", "§5§l"));
    List<String> SpecialList = new ArrayList<>(Arrays.asList("§7отсутствует"));
    List<Material> MaterialList = new ArrayList<>(Arrays.asList(Material.WOODEN_SWORD, Material.STONE_SWORD, Material.IRON_SWORD, Material.GOLDEN_SWORD, Material.DIAMOND_SWORD, Material.NETHERITE_SWORD));

    private ItemStack Sword(String name, int material, int damage, int level, double speed, int critDamage, int critChance, int special, int rarity) {
        ItemStack sword = new ItemStack(MaterialList.get(material), 1);
        ItemMeta meta = sword.getItemMeta();
        meta.setDisplayName(RarityColorList.get(rarity) + name);
        List<String> lore = new ArrayList<>();
        lore.add("§7Урон: §f" + damage);
        lore.add("§7Необходимый уровень: §f" + level);
        lore.add("§7Скорость атаки: §f" + speed);
        lore.add("§7Критический урон: §f" + critDamage + ", " + critChance + "%");
        lore.add(" ");
        if (special != 0) {
            lore.add("§7Особенность: §f" + SpecialList.get(special));
            lore.add(" ");
        }
        lore.add(RarityList.get(rarity));
        meta.setLore(lore);
        meta.setUnbreakable(true);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, new AttributeModifier(UUID.randomUUID(), "generic.attackDamage", damage, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND));
        meta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, new AttributeModifier(UUID.randomUUID(), "generic.attackSpeed", speed, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND));
        sword.setItemMeta(meta);
        return sword;
    }

    @EventHandler
    public void CreateSword(AsyncPlayerChatEvent e) {
        if(e.getMessage().startsWith(".createsword")) {
            e.setCancelled(true);
            if(!e.getPlayer().isOp()) {
                e.getPlayer().sendMessage("§cУ вас нет прав на использование этой команды!");
                return;
            }
            String[] args = e.getMessage().split(" ");
            if(args.length != 10) {
                e.getPlayer().sendMessage("§eИспользование: .createsword <название> <материал> <урон> <уровень> <скорость> <критический урон> <шанс крита> <особенность> <редкость>");
                return;
            }
            String name = Arrays.toString(args[1].split("_")).replace("[", "").replace("]", "").replace(",", "");
            int material = Integer.parseInt(args[2]);
            int damage = Integer.parseInt(args[3]);
            int level = Integer.parseInt(args[4]);
            double speed = Double.parseDouble(args[5]);
            int critDamage = Integer.parseInt(args[6]);
            int critChance = Integer.parseInt(args[7]);
            int special = Integer.parseInt(args[8]);
            int rarity = Integer.parseInt(args[9]);
            e.getPlayer().getInventory().addItem(Sword(name, material, damage, level, speed, critDamage, critChance, special, rarity));
            List<Integer> NN = ArCore.getInstance().getConfig().getIntegerList("Swords.NN");
            List<String> NameCfg = ArCore.getInstance().getConfig().getStringList("Swords.Name");
            List<Integer> MaterialCfg = ArCore.getInstance().getConfig().getIntegerList("Swords.Material");
            List<Integer> DamageCfg = ArCore.getInstance().getConfig().getIntegerList("Swords.Damage");
            List<Integer> LevelCfg = ArCore.getInstance().getConfig().getIntegerList("Swords.Level");
            List<Double> SpeedCfg = ArCore.getInstance().getConfig().getDoubleList("Swords.Speed");
            List<Integer> CritDamageCfg = ArCore.getInstance().getConfig().getIntegerList("Swords.CritDamage");
            List<Integer> CritChanceCfg = ArCore.getInstance().getConfig().getIntegerList("Swords.CritChance");
            List<Integer> SpecialCfg = ArCore.getInstance().getConfig().getIntegerList("Swords.Special");
            List<Integer> RarityCfg = ArCore.getInstance().getConfig().getIntegerList("Swords.Rarity");
            NN.add(NN.size()+1);
            NameCfg.add(name);
            MaterialCfg.add(material);
            DamageCfg.add(damage);
            LevelCfg.add(level);
            SpeedCfg.add(speed);
            CritDamageCfg.add(critDamage);
            CritChanceCfg.add(critChance);
            SpecialCfg.add(special);
            RarityCfg.add(rarity);
            ArCore.getInstance().getConfig().set("Swords.NN", NN);
            ArCore.getInstance().getConfig().set("Swords.Name", NameCfg);
            ArCore.getInstance().getConfig().set("Swords.Material", MaterialCfg);
            ArCore.getInstance().getConfig().set("Swords.Damage", DamageCfg);
            ArCore.getInstance().getConfig().set("Swords.Level", LevelCfg);
            ArCore.getInstance().getConfig().set("Swords.Speed", SpeedCfg);
            ArCore.getInstance().getConfig().set("Swords.CritDamage", CritDamageCfg);
            ArCore.getInstance().getConfig().set("Swords.CritChance", CritChanceCfg);
            ArCore.getInstance().getConfig().set("Swords.Special", SpecialCfg);
            ArCore.getInstance().getConfig().set("Swords.Rarity", RarityCfg);
            ArCore.getInstance().saveConfig();
            e.getPlayer().sendMessage("§aМеч успешно создан!");
        }
        if(e.getMessage().startsWith(".sword")) {
            e.setCancelled(true);
            if(!e.getPlayer().isOp()) {
                e.getPlayer().sendMessage("§cУ вас нет прав на использование этой команды!");
                return;
            }
            String[] args = e.getMessage().split(" ");
            if(args.length != 2) {
                e.getPlayer().sendMessage("§eИспользование: .sword <номер меча>");
                return;
            }
            int n = Integer.parseInt(args[1]) - 1;
            List<String> NameCfg = ArCore.getInstance().getConfig().getStringList("Swords.Name");
            List<Integer> MaterialCfg = ArCore.getInstance().getConfig().getIntegerList("Swords.Material");
            List<Integer> DamageCfg = ArCore.getInstance().getConfig().getIntegerList("Swords.Damage");
            List<Integer> LevelCfg = ArCore.getInstance().getConfig().getIntegerList("Swords.Level");
            List<Double> SpeedCfg = ArCore.getInstance().getConfig().getDoubleList("Swords.Speed");
            List<Integer> CritDamageCfg = ArCore.getInstance().getConfig().getIntegerList("Swords.CritDamage");
            List<Integer> CritChanceCfg = ArCore.getInstance().getConfig().getIntegerList("Swords.CritChance");
            List<Integer> SpecialCfg = ArCore.getInstance().getConfig().getIntegerList("Swords.Special");
            List<Integer> RarityCfg = ArCore.getInstance().getConfig().getIntegerList("Swords.Rarity");
            if(NameCfg.size() < n) {
                e.getPlayer().sendMessage("§cМеча с таким номером не существует!");
                return;
            }
            e.getPlayer().getInventory().addItem(Sword(NameCfg.get(n), MaterialCfg.get(n), DamageCfg.get(n), LevelCfg.get(n), SpeedCfg.get(n), CritDamageCfg.get(n), CritChanceCfg.get(n), SpecialCfg.get(n), RarityCfg.get(n)));
            e.getPlayer().sendMessage("§aМеч успешно выдан!");
        }
        if(e.getMessage().startsWith(".removesword")) {
            e.setCancelled(true);
            if (!e.getPlayer().isOp()) {
                e.getPlayer().sendMessage("§cУ вас нет прав на использование этой команды!");
                return;
            }
            String[] args = e.getMessage().split(" ");
            if (args.length != 2) {
                e.getPlayer().sendMessage("§eИспользование: .removesword <номер меча>");
                return;
            }
            int n = Integer.parseInt(args[1]);
            List<Integer> NN = ArCore.getInstance().getConfig().getIntegerList("Swords.NN");
            List<String> NameCfg = ArCore.getInstance().getConfig().getStringList("Swords.Name");
            List<Integer> MaterialCfg = ArCore.getInstance().getConfig().getIntegerList("Swords.Material");
            List<Integer> DamageCfg = ArCore.getInstance().getConfig().getIntegerList("Swords.Damage");
            List<Integer> LevelCfg = ArCore.getInstance().getConfig().getIntegerList("Swords.Level");
            List<Double> SpeedCfg = ArCore.getInstance().getConfig().getDoubleList("Swords.Speed");
            List<Integer> CritDamageCfg = ArCore.getInstance().getConfig().getIntegerList("Swords.CritDamage");
            List<Integer> CritChanceCfg = ArCore.getInstance().getConfig().getIntegerList("Swords.CritChance");
            List<Integer> SpecialCfg = ArCore.getInstance().getConfig().getIntegerList("Swords.Special");
            List<Integer> RarityCfg = ArCore.getInstance().getConfig().getIntegerList("Swords.Rarity");
            if (NN.contains(n)) {
                int i = NN.indexOf(n);
                NN.remove(i);
                NameCfg.remove(i);
                MaterialCfg.remove(i);
                DamageCfg.remove(i);
                LevelCfg.remove(i);
                SpeedCfg.remove(i);
                CritDamageCfg.remove(i);
                CritChanceCfg.remove(i);
                SpecialCfg.remove(i);
                RarityCfg.remove(i);
                ArCore.getInstance().getConfig().set("Swords.NN", NN);
                ArCore.getInstance().getConfig().set("Swords.Name", NameCfg);
                ArCore.getInstance().getConfig().set("Swords.Material", MaterialCfg);
                ArCore.getInstance().getConfig().set("Swords.Damage", DamageCfg);
                ArCore.getInstance().getConfig().set("Swords.Level", LevelCfg);
                ArCore.getInstance().getConfig().set("Swords.Speed", SpeedCfg);
                ArCore.getInstance().getConfig().set("Swords.CritDamage", CritDamageCfg);
                ArCore.getInstance().getConfig().set("Swords.CritChance", CritChanceCfg);
                ArCore.getInstance().getConfig().set("Swords.Special", SpecialCfg);
                ArCore.getInstance().getConfig().set("Swords.Rarity", RarityCfg);
                ArCore.getInstance().saveConfig();
                e.getPlayer().sendMessage("§aМеч успешно удален!");
                for (int j = n; j < NN.size(); j++) {
                    NN.set(j, NN.get(j) - 1);
                }
            } else {
                e.getPlayer().sendMessage("§cМеч не найден!");
            }
        }
        if(e.getMessage().startsWith(".listsword")) {
            e.setCancelled(true);
            if (!e.getPlayer().isOp()) {
                e.getPlayer().sendMessage("§cУ вас нет прав на использование этой команды!");
                return;
            }
            if (ArCore.getInstance().getConfig().getIntegerList("Swords.NN").isEmpty()){
                e.getPlayer().sendMessage("§eСписок мечей пуст!");
                return;
            }
            e.getPlayer().sendMessage("§eСписок мечей:");
            List<Integer> NN = ArCore.getInstance().getConfig().getIntegerList("Swords.NN");
            for (int i = 0; i < NN.size(); i++) {
                e.getPlayer().sendMessage("§eМеч " + NN.get(i) + ": " + RarityColorList.get(ArCore.getInstance().getConfig().getIntegerList("Swords.Rarity").get(i)) + ArCore.getInstance().getConfig().getStringList("Swords.Name").get(i));
            }
        }
    }

    @EventHandler
    public void Use(PlayerInteractEvent e) {
        if(e.getItem() == null) return;
        if(!MaterialList.contains(e.getItem().getType())) return;
        if(e.getItem().getItemMeta() == null) return;
        if(e.getItem().getItemMeta().getLore() == null) return;
        List<String> NamesCfg = ArCore.getInstance().getConfig().getStringList("Swords.Name");
        int n = NamesCfg.indexOf(e.getItem().getItemMeta().getDisplayName());
    }

    @EventHandler
    public void Damage(EntityDamageByEntityEvent e) {
        if(e.getDamager() instanceof Player) {
            if(((Player) e.getDamager()).getInventory().getItemInMainHand().getItemMeta() == null) return;
            if(((Player) e.getDamager()).getInventory().getItemInMainHand().getItemMeta().getLore() == null) return;
            List<String> NamesCfg = ArCore.getInstance().getConfig().getStringList("Swords.Name");
            int n = NamesCfg.indexOf(((Player) e.getDamager()).getInventory().getItemInMainHand().getItemMeta().getDisplayName().substring(4));
            int damage = ArCore.getInstance().getConfig().getIntegerList("Swords.Damage").get(n);
            int critDamage = ArCore.getInstance().getConfig().getIntegerList("Swords.CritDamage").get(n);
            int critChance = ArCore.getInstance().getConfig().getIntegerList("Swords.CritChance").get(n);
            Location loc = e.getEntity().getLocation();
            loc.setX(loc.getX() + Math.random());
            loc.setY(loc.getY() + Math.random());
            loc.setZ(loc.getZ() + Math.random());
            if(Math.random() * 100 < critChance) {
                damage += critDamage;
                Location loc2 = e.getEntity().getLocation();
                loc2.setX(loc.getX());
                loc2.setY(loc.getY() - 0.25);
                loc2.setZ(loc.getZ());
                ArmorStand crit_damage = loc.getWorld().spawn(loc2, ArmorStand.class);
                crit_damage.setInvisible(true);
                crit_damage.setCustomName("§eКРИТ " + critDamage);
                crit_damage.setCustomNameVisible(true);
                crit_damage.setGravity(false);
                crit_damage.setInvulnerable(true);
                Bukkit.getScheduler().runTaskLater(ArCore.getInstance(), crit_damage::remove, 20);
                Player p = (Player) e.getDamager();
                p.spawnParticle(org.bukkit.Particle.CRIT_MAGIC, loc, 100, 0.5, 0.5, 0.5, 0);
            }
            e.setDamage(damage);
            ArmorStand as_damage = e.getEntity().getWorld().spawn(loc, ArmorStand.class);
            as_damage.setInvisible(true);
            as_damage.setCustomName("§c" + damage);
            as_damage.setCustomNameVisible(true);
            as_damage.setGravity(false);
            as_damage.setInvulnerable(true);
            Bukkit.getScheduler().runTaskLater(ArCore.getInstance(), as_damage::remove, 20);
        }
    }
}
