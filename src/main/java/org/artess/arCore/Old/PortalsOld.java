package org.artess.arCore.Old;

import org.artess.arCore.ArCore;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PortalsOld implements Listener {

    //Символы §

    List<String> TpLocations = new ArrayList<>(Arrays.asList("§a§lЗеленый луг", "§2§lТемный лес", "§5§lМангровые топи",
            "§e§lСаванна", "§6§lМеса", "§e§lПустыня",
            "§b§lТропический остров", "§9§lОкеан", "§c§lГрибные острова", "§d§lСады", "§2§lХвойный лес", "§3§lПризрачный лес",
            "§f§lГорная деревня", "§b§lЛедяные пики", "§d§lКристальные пещеры", "§4§lР§c§lа§6§lд§e§lу§a§lж§2§lн§b§lы§3§lй §9§lл§5§lе§d§lс",
            "§a§lЦветочная поляна", "§8§k§l...§f §8§lДревние руины §8§k§l...", "§7§lПодземелье", "§f§lНебесный сад",
            "§b§lХрустальное озеро", "§2§lЛабиринт", "§d§lМегаполис", "§6§lПантеон"));

    List<String> TpLocationsD = new ArrayList<>(Arrays.asList("на Зеленый луг!", "в Темный лес!",
            "в Мангровые топи!", "в Саванну!", "в Месу!", "в Пустыню!",
            "на Тропический остров!", "в Океан!", "на Грибные острова!",
            "в Сады!", "в Хвойный лес!", "в Призрачный лес!", "в Горную деревню!",
            "на Ледяные пики!", "в Кристальные пещеры!", "в Радужный лес!",
            "на Цветочную поляну!", "в Древние руины!", "в Подземелье!", "в Небесный сад!",
            "к Хрустальному озеру!", "в Лабиринт!", "в Мегаполис!", "в Пантеон!"));


    @EventHandler
    public void SetupPortals(AsyncPlayerChatEvent e) {
        if (e.getMessage().startsWith(".setportal")) {
            e.setCancelled(true);
            if (!e.getPlayer().isOp()) {
                e.getPlayer().sendMessage(ChatColor.RED + "У вас нет прав на использование этой команды!");
                return;
            }
            String[] args = e.getMessage().split(" ");
            if (args.length != 1) {
                e.getPlayer().sendMessage(ChatColor.YELLOW + "Использование: .setportal");
                return;
            }
            List<Double> x = ArCore.getInstance().getConfig().getDoubleList("Portals.X");
            x.add(e.getPlayer().getLocation().getX());
            ArCore.getInstance().getConfig().set("Portals.X", x);
            List<Double> y = ArCore.getInstance().getConfig().getDoubleList("Portals.Y");
            y.add(e.getPlayer().getLocation().getY());
            ArCore.getInstance().getConfig().set("Portals.Y", y);
            List<Double> z = ArCore.getInstance().getConfig().getDoubleList("Portals.Z");
            z.add(e.getPlayer().getLocation().getZ());
            ArCore.getInstance().getConfig().set("Portals.Z", z);
            List<Integer> NN = ArCore.getInstance().getConfig().getIntegerList("Portals.NN");
            NN.add(NN.size() + 1);
            ArCore.getInstance().getConfig().set("Portals.NN", NN);
            ArCore.getInstance().saveConfig();
            e.getPlayer().sendMessage(ChatColor.GREEN + "Портал установлен!");
        }
        if (e.getMessage().startsWith(".removeportal")) {
            e.setCancelled(true);
            if (!e.getPlayer().isOp()) {
                e.getPlayer().sendMessage(ChatColor.RED + "У вас нет прав на использование этой команды!");
                return;
            }
            String[] args = e.getMessage().split(" ");
            if (args.length != 2) {
                e.getPlayer().sendMessage(ChatColor.YELLOW + "Использование: .removeportal <номер портала>");
                return;
            }
            int n = Integer.parseInt(args[1]);
            List<Double> x = ArCore.getInstance().getConfig().getDoubleList("Portals.X");
            List<Double> y = ArCore.getInstance().getConfig().getDoubleList("Portals.Y");
            List<Double> z = ArCore.getInstance().getConfig().getDoubleList("Portals.Z");
            List<Integer> NN = ArCore.getInstance().getConfig().getIntegerList("Portals.NN");
            if (NN.contains(n)) {
                int i = NN.indexOf(n);
                x.remove(i);
                y.remove(i);
                z.remove(i);
                NN.remove(i);
                ArCore.getInstance().getConfig().set("Portals.X", x);
                ArCore.getInstance().getConfig().set("Portals.Y", y);
                ArCore.getInstance().getConfig().set("Portals.Z", z);
                ArCore.getInstance().getConfig().set("Portals.NN", NN);
                ArCore.getInstance().saveConfig();
                e.getPlayer().sendMessage(ChatColor.GREEN + "Портал удален!");
            }
        }
        if(e.getMessage().startsWith(".listportals")) {
            e.setCancelled(true);
            if (!e.getPlayer().isOp()) {
                e.getPlayer().sendMessage(ChatColor.RED + "У вас нет прав на использование этой команды!");
                return;
            }
            List<Integer> NN = ArCore.getInstance().getConfig().getIntegerList("Portals.NN");
            List<Double> x = ArCore.getInstance().getConfig().getDoubleList("Portals.X");
            List<Double> y = ArCore.getInstance().getConfig().getDoubleList("Portals.Y");
            List<Double> z = ArCore.getInstance().getConfig().getDoubleList("Portals.Z");
            for (int i = 0; i < NN.size(); i++) {
                e.getPlayer().sendMessage(ChatColor.GREEN + "Портал " + NN.get(i) + ": X: " + x.get(i) + " Y: " + y.get(i) + " Z: " + z.get(i));
            }
        }
        if(e.getMessage().startsWith(".setlevel")) {
            e.setCancelled(true);
            if (!e.getPlayer().isOp()) {
                e.getPlayer().sendMessage(ChatColor.RED + "У вас нет прав на использование этой команды!");
                return;
            }
            String[] args = e.getMessage().split(" ");
            if (args.length != 3) {
                e.getPlayer().sendMessage(ChatColor.YELLOW + "Использование: .setlevel <игрок> <уровень>");
                return;
            }
            List<String> players = ArCore.getInstance().getConfig().getStringList("PlayersLocation.Player");
            List<Integer> NN = ArCore.getInstance().getConfig().getIntegerList("PlayersLocation.NN");
            if (players.contains(args[1])) {
                int i = players.indexOf(args[1]);
                if (Integer.parseInt(args[2]) > 24 || Integer.parseInt(args[2]) < 1) {
                    e.getPlayer().sendMessage(ChatColor.RED + "Уровень должен быть от 1 до 24!");
                    return;
                }
                NN.set(i, Integer.parseInt(args[2]));
                ArCore.getInstance().getConfig().set("PlayersLocation.NN", NN);
                ArCore.getInstance().saveConfig();
                e.getPlayer().sendMessage(ChatColor.GREEN + "Уровень установлен!");
            } else {
                e.getPlayer().sendMessage(ChatColor.RED + "Игрок не найден!");
            }
        }
        if(e.getMessage().startsWith(".clearlevels")) {
            e.setCancelled(true);
            if (!e.getPlayer().isOp()) {
                e.getPlayer().sendMessage(ChatColor.RED + "У вас нет прав на использование этой команды!");
                return;
            }
            List<String> players = ArCore.getInstance().getConfig().getStringList("PlayersLocation.Player");
            List<Integer> NN = ArCore.getInstance().getConfig().getIntegerList("PlayersLocation.NN");
            for (int i = 0; i < players.size(); i++) {
                NN.set(i, 1);
            }
            ArCore.getInstance().getConfig().set("PlayersLocation.NN", NN);
            ArCore.getInstance().saveConfig();
            e.getPlayer().sendMessage(ChatColor.GREEN + "Уровни сброшены!");
        }
    }

        @EventHandler
        public void TeleportPlayer(PlayerInteractEvent e) {
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

            int p_i = ArCore.getInstance().getConfig().getStringList("PlayersLocation.Player").indexOf(p.getName());
            int PlayerNN = ArCore.getInstance().getConfig().getIntegerList("PlayersLocation.NN").get(p_i);
            int n = 0;
            for (int j = 0; j < 54; j++) {
                if (menu.getItem(j) != null) {
                    n += 1;
                    if (n > PlayerNN) {
                        menu.setItem(j, unavailable);
                    }
                }
            }
            p.openInventory(menu);
        }
    }

    @EventHandler
    public void MenuClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        Inventory inv = e.getInventory();
        if(e.getView().getTitle().equals("Выберите этаж:")) {
            e.setCancelled(true);
            if(e.getCurrentItem() != null && TpLocations.contains(e.getCurrentItem().getItemMeta().getDisplayName())) {
                int i = e.getCurrentItem().getAmount();
                if (ArCore.getInstance().getConfig().getIntegerList("PlayersLocation.NN").get(ArCore.getInstance().getConfig().getStringList("PlayersLocation.Player").indexOf(p.getName())) < i
                || ArCore.getInstance().getConfig().getIntegerList("Portals.NN").size() < i) {
                    p.sendMessage(ChatColor.RED + "Вы не можете отправиться на этот уровень!");
                    return;
                }
                double x = ArCore.getInstance().getConfig().getDoubleList("Portals.X").get(i-1);
                double y = ArCore.getInstance().getConfig().getDoubleList("Portals.Y").get(i-1);
                double z = ArCore.getInstance().getConfig().getDoubleList("Portals.Z").get(i-1);
                p.teleport(p.getWorld().getBlockAt((int)x, (int)y, (int)z).getLocation());
                p.playSound(p.getLocation(), org.bukkit.Sound.ENTITY_ENDERMAN_TELEPORT, 1, 1);
                p.spawnParticle(org.bukkit.Particle.PORTAL, p.getLocation(), 100, 0.5, 0.5, 0.5, 0);
                p.sendTitle(TpLocations.get(i-1), "§7" + i + " этаж", 10, 70, 20);
            }
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
}
