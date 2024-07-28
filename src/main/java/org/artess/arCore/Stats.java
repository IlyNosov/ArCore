package org.artess.arCore;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Arrays;
import java.util.Objects;

public class Stats implements Listener {

    public static Stats instance = new Stats();

    @EventHandler
    public void FirstJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        ItemStack compass = new ItemStack(Material.COMPASS);
        ItemMeta meta = compass.getItemMeta();
        meta.setDisplayName("§6§lМеню");
        compass.setItemMeta(meta);
        p.getInventory().setItem(8, compass);
        if (ArCore.getInstance().stats.getConfigurationSection("Players." + p.getName()) == null) {
            ArCore.getInstance().stats.set("Players." + p.getName() + ".Level", 1);
            ArCore.getInstance().stats.set("Players." + p.getName() + ".Points", 0);
            ArCore.getInstance().stats.set("Players." + p.getName() + ".Attack", 1);
            ArCore.getInstance().stats.set("Players." + p.getName() + ".Stamina", 1);
            ArCore.getInstance().stats.set("Players." + p.getName() + ".Dexterity", 1);
            ArCore.getInstance().stats.set("Players." + p.getName() + ".Stealth", 1);
            ArCore.getInstance().stats.set("Players." + p.getName() + ".Wisdom", 1);
            ArCore.getInstance().stats.set("Players." + p.getName() + ".Luck", 1);
            ArCore.getInstance().stats.set("Players." + p.getName() + ".Health", 20);
            ArCore.getInstance().saveStats();
        }
        //Хп устанавливается тут
        double health = ArCore.getInstance().stats.getInt("Players." + p.getName() + ".Health");
        health += health * (ArCore.getInstance().stats.getInt("Players." + p.getName() + ".Stamina") - 1) / 100;
        p.setHealthScale(health);
    }

    @EventHandler
    public void OpenInventory(PlayerInteractEvent e) {
        if (e.getItem() == null) return;
        if (!e.getItem().getItemMeta().getDisplayName().equals("§6§lМеню")) return;
        Player p = e.getPlayer();
        e.setCancelled(true);
        Inventory inv = Menu(p);
        p.openInventory(inv);
    }

    public void addSkill(Player p, String skill, int value) {
        if (ArCore.getInstance().stats.getInt("Players." + p.getName() + "." + skill) + value < 1) {
            p.sendMessage("§cУ вас недостаточно очков навыков!");
            return;
        }
        ArCore.getInstance().stats.set("Players." + p.getName() + "." + skill, ArCore.getInstance().stats.getInt("Players." + p.getName() + "." + skill) + value);
        ArCore.getInstance().stats.set("Players." + p.getName() + ".Points", ArCore.getInstance().stats.getInt("Players." + p.getName() + ".Points") - value);
        ArCore.getInstance().saveStats();
        double health = ArCore.getInstance().stats.getInt("Players." + p.getName() + ".Health");
        health += health * (ArCore.getInstance().stats.getInt("Players." + p.getName() + ".Stamina") - 1) / 100;
        p.setHealthScale(health);
    }

    public void removeSkill(Player p, String skill, int value) {
        if (ArCore.getInstance().stats.getInt("Players." + p.getName() + "." + skill) < 2) {
            p.sendMessage("§cУ вас недостаточно очков навыков!");
            return;
        }
        ArCore.getInstance().stats.set("Players." + p.getName() + "." + skill, ArCore.getInstance().stats.getInt("Players." + p.getName() + "." + skill) - value);
        ArCore.getInstance().stats.set("Players." + p.getName() + ".Points", ArCore.getInstance().stats.getInt("Players." + p.getName() + ".Points") + value);
        ArCore.getInstance().saveStats();
    }

    //Раздел для команды /stats

    public Integer getStat(Player p, String stat) {
        return ArCore.getInstance().stats.getInt("Players." + p.getName() + "." + stat);
    }

    public void setStat(Player p, String stat, int value) {
        ArCore.getInstance().stats.set("Players." + p.getName() + "." + stat, value);
        ArCore.getInstance().saveStats();
        double health = ArCore.getInstance().stats.getInt("Players." + p.getName() + ".Health");
        health += health * (ArCore.getInstance().stats.getInt("Players." + p.getName() + ".Stamina") - 1) / 100;
        p.setHealthScale(health);
    }

    @EventHandler
    public void BlockCompass(InventoryClickEvent e) {
        if (e.getCurrentItem() == null) return;
        ItemStack compass = new ItemStack(Material.COMPASS);
        ItemMeta meta = compass.getItemMeta();
        meta.setDisplayName("§6§lМеню");
        compass.setItemMeta(meta);
        if (e.getCurrentItem().equals(compass)) e.setCancelled(true);
    }

    @EventHandler
    public void BlockCompassDrag(InventoryDragEvent e) {
        ItemStack compass = new ItemStack(Material.COMPASS);
        ItemMeta meta = compass.getItemMeta();
        meta.setDisplayName("§6§lМеню");
        compass.setItemMeta(meta);
        if (e.getOldCursor().equals(compass)) e.setCancelled(true);
    }

    @EventHandler
    public void BlockCompassDrop(PlayerDropItemEvent e) {
        ItemStack compass = new ItemStack(Material.COMPASS);
        ItemMeta meta = compass.getItemMeta();
        meta.setDisplayName("§6§lМеню");
        compass.setItemMeta(meta);
        if (e.getItemDrop().getItemStack().equals(compass)) e.setCancelled(true);
    }

    @EventHandler
    public void OnlyOneCompass(InventoryInteractEvent e) {
        ItemStack compass = new ItemStack(Material.COMPASS);
        ItemMeta meta = compass.getItemMeta();
        meta.setDisplayName("§6§lМеню");
        compass.setItemMeta(meta);
        if (e.getInventory().contains(compass)) e.setCancelled(true);
    }

    //Атака - до +20% к основному урону - +0.5% за поинт
    //Выносливость - до +40% к макс здоровью - +1% за поинт
    //Ловкость - до +20% к шансу крита - +0.5% за поинт
    //Скрытность - до +60% к скорости - +1.5% за поинт
    //Мудрость - до +20% к шансу поглотить урон - +0.5% за поинт
    //Удача - до +20% к шансу дропа легендарного/мифического предмета - +0.5% за поинт

    @EventHandler
    public void MenuClick(InventoryClickEvent e) {
        if (e.getView().getTitle().equals("Меню")) {
            e.setCancelled(true);
            if (e.getCurrentItem() == null) return;
            if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§e§lНавыки")) {
                Inventory inv = Stats((Player) e.getWhoClicked());
                e.getWhoClicked().openInventory(inv);
            }
            if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§d§lЭндер сундук")) {
                Inventory ec = e.getWhoClicked().getEnderChest();
                e.getWhoClicked().openInventory(ec);
                Player p = (Player) e.getWhoClicked();
                p.playSound(p.getLocation(), Sound.BLOCK_ENDER_CHEST_OPEN, 1, 1);
            }
        }
    }



    private ItemStack stat(Material material, String name, String lore, int v, int max) {
        ItemStack item = new ItemStack(material, v);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        String s = name.substring(0, 2);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS);
        meta.setLore(Arrays.asList("§7" + lore, " ", s + "+" + (v-1)*max/40.0 + "% §8 >>> " + s + "+" + v*max/40.0 + "%", " ", "§7Текущее значение: §f" + v, "§aНажмите чтобы улучшить"));
        if (v == 41) {
            meta.addEnchant(org.bukkit.enchantments.Enchantment.DURABILITY, 10, true);
            meta.setLore(Arrays.asList("§7" + lore, " ", s + (v-1)*max/40.0 + "%", " ", "§7Текущее значение: §f" + v, "§cДостигнут максимум"));
        }
        item.setItemMeta(meta);
        return item;
    }

    @EventHandler
    public void StatMenuClick(InventoryClickEvent e) {
        if (e.getView().getTitle().equals("Навыки")) {
            e.setCancelled(true);
            Player p = (Player) e.getWhoClicked();
            if (e.getCurrentItem() == null) return;
            if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§6§lМеню")) {
                Inventory inv = Menu(p);
                p.openInventory(inv);
            }
            if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§c§lАтака")) {
                if(ArCore.getInstance().stats.getInt("Players." + p.getName() + ".Points") == 0) {
                    p.sendMessage("§cУ вас недостаточно очков навыков!");
                    p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1, 1);
                    return;
                }
                if(ArCore.getInstance().stats.getInt("Players." + p.getName() + ".Attack") == 41) {
                    p.sendMessage("§cДостигнут максимум!");
                    p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1, 1);
                    return;
                }
                addSkill(p, "Attack", 1);
                Inventory inv = Stats(p);
                p.openInventory(inv);
                p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
            }
            if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§b§lВыносливость")) {
                if(ArCore.getInstance().stats.getInt("Players." + p.getName() + ".Points") == 0) {
                    p.sendMessage("§cУ вас недостаточно очков навыков!");
                    p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1, 1);
                    return;
                }
                if(ArCore.getInstance().stats.getInt("Players." + p.getName() + ".Stamina") == 41) {
                    p.sendMessage("§cДостигнут максимум!");
                    p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1, 1);
                    return;
                }
                addSkill(p, "Stamina", 1);
                Inventory inv = Stats(p);
                p.openInventory(inv);
                p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
            }
            if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§9§lЛовкость")) {
                if(ArCore.getInstance().stats.getInt("Players." + p.getName() + ".Points") == 0) {
                    p.sendMessage("§cУ вас недостаточно очков навыков!");
                    p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1, 1);
                    return;
                }
                if(ArCore.getInstance().stats.getInt("Players." + p.getName() + ".Dexterity") == 41) {
                    p.sendMessage("§cДостигнут максимум!");
                    p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1, 1);
                    return;
                }
                addSkill(p, "Dexterity", 1);
                Inventory inv = Stats(p);
                p.openInventory(inv);
                p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
            }
            if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§d§lСкрытность")) {
                if(ArCore.getInstance().stats.getInt("Players." + p.getName() + ".Points") == 0) {
                    p.sendMessage("§cУ вас недостаточно очков навыков!");
                    p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1, 1);
                    return;
                }
                if(ArCore.getInstance().stats.getInt("Players." + p.getName() + ".Stealth") == 41) {
                    p.sendMessage("§cДостигнут максимум!");
                    p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1, 1);
                    return;
                }
                addSkill(p, "Stealth", 1);
                Inventory inv = Stats(p);
                p.openInventory(inv);
                p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
            }
            if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§e§lМудрость")) {
                if(ArCore.getInstance().stats.getInt("Players." + p.getName() + ".Points") == 0) {
                    p.sendMessage("§cУ вас недостаточно очков навыков!");
                    p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1, 1);
                    return;
                }
                if(ArCore.getInstance().stats.getInt("Players." + p.getName() + ".Wisdom") == 41) {
                    p.sendMessage("§cДостигнут максимум!");
                    p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1, 1);
                    return;
                }
                addSkill(p, "Wisdom", 1);
                Inventory inv = Stats(p);
                p.openInventory(inv);
                p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
            }
            if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§a§lУдача")) {
                if(ArCore.getInstance().stats.getInt("Players." + p.getName() + ".Points") == 0) {
                    p.sendMessage("§cУ вас недостаточно очков навыков!");
                    p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1, 1);
                    return;
                }
                if(ArCore.getInstance().stats.getInt("Players." + p.getName() + ".Luck") == 41) {
                    p.sendMessage("§cДостигнут максимум!");
                    p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1, 1);
                    return;
                }
                addSkill(p, "Luck", 1);
                Inventory inv = Stats(p);
                p.openInventory(inv);
                p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
            }
        }
    }

    public Inventory Menu(Player p) {
        Inventory inv = ArCore.getInstance().getServer().createInventory(null, 27, "Меню");

        ItemStack bundle = new ItemStack(Material.ENDER_CHEST);
        ItemMeta meta0 = bundle.getItemMeta();
        meta0.setDisplayName("§d§lЭндер сундук");
        meta0.addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS);
        meta0.setLore(Arrays.asList("§aНажмите чтобы открыть"));
        bundle.setItemMeta(meta0);

        ItemStack head = new ItemStack(Material.PLAYER_HEAD);
        ItemMeta meta1 = head.getItemMeta();
        meta1.setDisplayName("§6§lПрофиль " + p.getName());
        meta1.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta1.setLore(Arrays.asList("§7Уровень персонажа: §f" + ArCore.getInstance().stats.getInt("Players." + p.getName() + ".Level"),
                " ",
                "§cМакс. Здоровье: " + ArCore.getInstance().stats.getInt("Players." + p.getName() + ".Health"),
                "§7Атака: §f" + ArCore.getInstance().stats.getInt("Players." + p.getName() + ".Attack"),
                "§7Выносливость: §f" + ArCore.getInstance().stats.getInt("Players." + p.getName() + ".Stamina"),
                "§7Ловкость: §f" + ArCore.getInstance().stats.getInt("Players." + p.getName() + ".Dexterity"),
                "§7Скрытность: §f" + ArCore.getInstance().stats.getInt("Players." + p.getName() + ".Stealth"),
                "§7Мудрость: §f" + ArCore.getInstance().stats.getInt("Players." + p.getName() + ".Wisdom"),
                "§7Удача: §f" + ArCore.getInstance().stats.getInt("Players." + p.getName() + ".Luck")));
        SkullMeta skullMeta = (SkullMeta) meta1;
        skullMeta.setOwningPlayer(p);
        head.setItemMeta(skullMeta);

        ItemStack star = new ItemStack(Material.NETHER_STAR);
        ItemMeta meta2 = star.getItemMeta();
        meta2.setDisplayName("§e§lНавыки");
        meta2.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta2.setLore(Arrays.asList("§aНажмите чтобы редактировать навыки", "§7Доступно очков: §f" + ArCore.getInstance().stats.getInt("Players." + p.getName() + ".Points")));
        star.setItemMeta(meta2);

        inv.setItem(11, star);
        inv.setItem(13, head);
        inv.setItem(15, bundle);
        return inv;
    }

    public Inventory Stats(Player p) {
        Inventory inv = ArCore.getInstance().getServer().createInventory(null, 27, "Навыки");
        ItemStack attack = stat(Material.DIAMOND_SWORD, "§c§lАтака", "Увеличивает урон", getStat(p, "Attack"), 20);
        ItemStack stamina = stat(Material.GOLDEN_APPLE, "§b§lВыносливость", "Увеличивает здоровье", getStat(p, "Stamina"), 40);
        ItemStack dexterity = stat(Material.BOW, "§9§lЛовкость", "Увеличивает шанс крита", getStat(p, "Dexterity"), 20);
        ItemStack stealth = stat(Material.LEATHER_BOOTS, "§d§lСкрытность", "Увеличивает скорость", getStat(p, "Stealth"), 60);
        ItemStack wisdom = stat(Material.ENCHANTED_BOOK, "§e§lМудрость", "Увеличивает шанс поглотить урон", getStat(p, "Wisdom"), 20);
        ItemStack luck = stat(Material.EMERALD, "§a§lУдача", "Увеличивает шанс дропа легендарного предмета", getStat(p, "Luck"), 20);
        ItemStack menu = new ItemStack(Material.COMPASS);
        ItemMeta meta = menu.getItemMeta();
        meta.setDisplayName("§6§lМеню");
        meta.setLore(Arrays.asList("§7Нераспределенные очки: §f" + ArCore.getInstance().stats.getInt("Players." + p.getName() + ".Points")));
        menu.setItemMeta(meta);

        inv.setItem(10, attack);
        inv.setItem(11, stamina);
        inv.setItem(12, dexterity);
        inv.setItem(13, stealth);
        inv.setItem(14, wisdom);
        inv.setItem(15, luck);
        inv.setItem(16, menu);
        return inv;
    }

    @EventHandler
    public void addSpeed(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        p.setWalkSpeed((float) (0.2 + 0.01 * ArCore.getInstance().stats.getInt("Players." + p.getName() + ".Stealth")));
    }

}

