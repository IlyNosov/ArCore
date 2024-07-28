package org.artess.arCore;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Floors implements Listener {

    List<String> TpLocations = ArCore.getInstance().floors.getStringList("TpLocations");

    public static Floors instance = new Floors();

    @EventHandler
    public void TeleportPlayer(PlayerInteractEvent e) {
        if(e.getClickedBlock() == null) return;
        if (e.getAction().isRightClick() && e.getClickedBlock().getBlockData().getMaterial().equals(Material.BEACON)) {
            Player p = e.getPlayer();
            e.setCancelled(true);
            Inventory menu = Bukkit.createInventory(p, 54, "Выберите этаж:");

            ItemStack unavailable = new ItemStack(Material.GRAY_DYE, 1);
            ItemMeta unavailableMeta = unavailable.getItemMeta();
            unavailableMeta.setDisplayName(ChatColor.RED + "Недоступно");
            unavailable.setItemMeta(unavailableMeta);

            ItemStack portal1 = createPortal(Material.GRASS_BLOCK, 1, TpLocations.get(0), "1", "§e★§7☆☆☆☆");
            ItemStack portal2 = createPortal(Material.DARK_OAK_LEAVES, 2, TpLocations.get(1), "5", "§e★§7☆☆☆☆");
            ItemStack portal3 = createPortal(Material.MUD, 3, TpLocations.get(2), "10", "§e★§7☆☆☆☆");
            ItemStack portal4 = createPortal(Material.ACACIA_LOG, 4, TpLocations.get(3), "15", "§e★★§7☆☆☆");
            ItemStack portal5 = createPortal(Material.TERRACOTTA, 5, TpLocations.get(4), "20", "§e★★§7☆☆☆");
            ItemStack portal6 = createPortal(Material.CACTUS, 6, TpLocations.get(5), "25", "§e★★§7☆☆☆");
            ItemStack portal7 = createPortal(Material.TROPICAL_FISH, 7, TpLocations.get(6), "30", "§e★★§7☆☆☆");
            ItemStack portal8 = createPortal(Material.PRISMARINE, 8, TpLocations.get(7), "35", "§e★★§7☆☆☆");
            ItemStack portal9 = createPortal(Material.RED_MUSHROOM_BLOCK, 9, TpLocations.get(8), "40", "§e★★§7☆☆☆");
            ItemStack portal10 = createPortal(Material.CHERRY_LEAVES, 10, TpLocations.get(9), "45", "§e★★★§7☆☆");
            ItemStack portal11 = createPortal(Material.SPRUCE_LEAVES, 11, TpLocations.get(10), "50", "§e★★★§7☆☆");
            ItemStack portal12 = createPortal(Material.DEAD_BRAIN_CORAL_BLOCK, 12, TpLocations.get(11), "55", "§e★★★§7☆☆");
            ItemStack portal13 = createPortal(Material.SNOW_BLOCK, 13, TpLocations.get(12), "60", "§e★★★§7☆☆");
            ItemStack portal14 = createPortal(Material.PACKED_ICE, 14, TpLocations.get(13), "65", "§e★★★§7☆☆");
            ItemStack portal15 = createPortal(Material.AMETHYST_SHARD, 15, TpLocations.get(14), "70", "§e★★★★§7☆");
            ItemStack portal16 = createPortal(Material.YELLOW_STAINED_GLASS, 16, TpLocations.get(15), "75", "§e★★★★§7☆");
            ItemStack portal17 = createPortal(Material.DANDELION, 17, TpLocations.get(16), "80", "§e★★★★§7☆");
            ItemStack portal18 = createPortal(Material.CRACKED_DEEPSLATE_BRICKS, 18, TpLocations.get(17), "85", "§e★★★★§7☆");
            ItemStack portal19 = createPortal(Material.MOSSY_COBBLESTONE, 19, TpLocations.get(18), "90", "§e★★★★§7☆");
            ItemStack portal20 = createPortal(Material.SEA_LANTERN, 20, TpLocations.get(19), "95", "§e★★★★★");
            ItemStack portal21 = createPortal(Material.HEART_OF_THE_SEA, 21, TpLocations.get(20), "100", "§e★★★★★");
            ItemStack portal22 = createPortal(Material.MOSSY_STONE_BRICKS, 22, TpLocations.get(21), "105", "§e★★★★★");
            ItemStack portal23 = createPortal(Material.EMERALD_BLOCK, 23, TpLocations.get(22), "110", "§e★★★★★");
            ItemStack portal24 = createPortal(Material.GOLD_BLOCK, 24, TpLocations.get(23), "115", "§e★★★★★");

            menu.setItem(10, portal1);
            menu.setItem(11, portal2);
            menu.setItem(12, portal3);
            menu.setItem(13, portal4);
            menu.setItem(14, portal5);
            menu.setItem(15, portal6);
            menu.setItem(16, portal7);
            menu.setItem(19, portal8);
            menu.setItem(20, portal9);
            menu.setItem(21, portal10);
            menu.setItem(22, portal11);
            menu.setItem(23, portal12);
            menu.setItem(24, portal13);
            menu.setItem(25, portal14);
            menu.setItem(28, portal15);
            menu.setItem(29, portal16);
            menu.setItem(30, portal17);
            menu.setItem(31, portal18);
            menu.setItem(32, portal19);
            menu.setItem(33, portal20);
            menu.setItem(34, portal21);
            menu.setItem(39, portal22);
            menu.setItem(40, portal23);
            menu.setItem(41, portal24);

            Location BeaconLoc = e.getClickedBlock().getLocation();
            double x = BeaconLoc.getX();
            double y = BeaconLoc.getY();
            double z = BeaconLoc.getZ();
            int floor = 0;
            for (int i = 0; i < ArCore.getInstance().floors.getConfigurationSection("Floors").getKeys(false).size(); i++) {
                if (ArCore.getInstance().floors.getDouble("Floors." + (i+1) + ".X") < (x+3) && ArCore.getInstance().floors.getDouble("Floors." + (i+1) + ".X") > (x-3) &&
                        ArCore.getInstance().floors.getDouble("Floors." + (i+1) + ".Y") < (y+3) && ArCore.getInstance().floors.getDouble("Floors." + (i+1) + ".Y") > (y-3) &&
                        ArCore.getInstance().floors.getDouble("Floors." + (i+1) + ".Z") < (z+3) && ArCore.getInstance().floors.getDouble("Floors." + (i+1) + ".Z") > (z-3)) {
                    floor = i+1;
                }
            }

            int PlayersFloor = ArCore.getInstance().stats.getInt("Players." + p.getName() + ".Floor");
            int n = 0;
            for (int j = 0; j < 54; j++) {
                if (menu.getItem(j) != null) {
                    n += 1;
                    if (n > PlayersFloor) {
                        menu.setItem(j, unavailable);
                    }
                    if (n == floor && n <= PlayersFloor) {
                        ItemStack portal = menu.getItem(j);
                        ItemMeta portalMeta = portal.getItemMeta();
                        portalMeta.setLore(Arrays.asList(portalMeta.getLore().get(0), portalMeta.getLore().get(1), ChatColor.RED + "Вы уже на этом этаже"));
                        portalMeta.addEnchant(org.bukkit.enchantments.Enchantment.DURABILITY, 10, true);
                        portalMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                        portal.setItemMeta(portalMeta);
                        menu.setItem(j, portal);
                    }
                }
            }
            p.openInventory(menu);
        }
    }
    private ItemStack createPortal(Material material, int amount, String displayName, String level, String difficulty) {
        ItemStack portal = new ItemStack(material, amount);
        ItemMeta portalMeta = portal.getItemMeta();
        portalMeta.setDisplayName(displayName);
        portalMeta.setLore(Arrays.asList(ChatColor.GRAY + "Рекомендуемый уровень: " + level, "§7Сложность: " + difficulty, ChatColor.GREEN + "Нажмите чтобы отправиться"));
        portal.setItemMeta(portalMeta);
        return portal;
    }

    @EventHandler
    public void MenuClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        Inventory inv = e.getInventory();
        if(e.getView().getTitle().equals("Выберите этаж:")) {
            e.setCancelled(true);
            if(e.getCurrentItem() != null && TpLocations.contains(e.getCurrentItem().getItemMeta().getDisplayName())) {
                int i = e.getCurrentItem().getAmount();
                if(i > ArCore.getInstance().floors.getConfigurationSection("Floors").getKeys(false).size() || e.getCurrentItem().getItemMeta().getEnchantLevel(org.bukkit.enchantments.Enchantment.DURABILITY) == 10) {
                    p.sendMessage("§cВы не можете телепортироваться на этот этаж!");
                    return;
                }
                double x = ArCore.getInstance().floors.getDouble("Floors." + i + ".X");
                double y = ArCore.getInstance().floors.getDouble("Floors." + i + ".Y");
                double z = ArCore.getInstance().floors.getDouble("Floors." + i + ".Z");
                p.teleport(p.getWorld().getBlockAt((int)x, (int)y, (int)z).getLocation());
                p.closeInventory();
                p.playSound(p.getLocation(), org.bukkit.Sound.ENTITY_ENDERMAN_TELEPORT, 1, 1);
                p.spawnParticle(org.bukkit.Particle.PORTAL, p.getLocation(), 100, 0.5, 0.5, 0.5, 0);
                p.sendTitle(TpLocations.get(i-1), "§7" + i + " этаж", 10, 70, 20);
            }
        }
    }

    public void newFloor(Player p, int floor) {
        ArCore.getInstance().floors.set("Floors." + floor + ".X", p.getLocation().getX());
        ArCore.getInstance().floors.set("Floors." + floor + ".Y", p.getLocation().getY());
        ArCore.getInstance().floors.set("Floors." + floor + ".Z", p.getLocation().getZ());
        ArCore.getInstance().saveFloors();
    }

    public void removeFloor(int floor) {
        ArCore.getInstance().floors.set("Floors." + floor, null);
        ArCore.getInstance().saveFloors();
    }

    public List<String> listFloors() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < ArCore.getInstance().floors.getConfigurationSection("Floors").getKeys(false).size(); i++) {
            list.add("§e" + (i+1) + " этаж " + "§7- " + ArCore.getInstance().floors.getInt("Floors." + (i+1) + ".X") + " "
                    + ArCore.getInstance().floors.getInt("Floors." + (i+1) + ".Y") + " "
                    + ArCore.getInstance().floors.getInt("Floors." + (i+1) + ".Z"));
        }
        return list;
    }
}
