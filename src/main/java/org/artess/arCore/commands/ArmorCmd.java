package org.artess.arCore.commands;

import org.artess.arCore.Armor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ArmorCmd implements CommandExecutor {

    List<Material> materials = List.of(Material.LEATHER_HELMET, Material.LEATHER_CHESTPLATE, Material.LEATHER_LEGGINGS, Material.LEATHER_BOOTS,
            Material.CHAINMAIL_HELMET, Material.CHAINMAIL_CHESTPLATE, Material.CHAINMAIL_LEGGINGS, Material.CHAINMAIL_BOOTS,
            Material.IRON_HELMET, Material.IRON_CHESTPLATE, Material.IRON_LEGGINGS, Material.IRON_BOOTS,
            Material.GOLDEN_HELMET, Material.GOLDEN_CHESTPLATE, Material.GOLDEN_LEGGINGS, Material.GOLDEN_BOOTS,
            Material.DIAMOND_HELMET, Material.DIAMOND_CHESTPLATE, Material.DIAMOND_LEGGINGS, Material.DIAMOND_BOOTS,
            Material.NETHERITE_HELMET, Material.NETHERITE_CHESTPLATE, Material.NETHERITE_LEGGINGS, Material.NETHERITE_BOOTS);

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player p = (Player) sender;
        List<String> cmd = List.of("give", "list", "create", "delete");
        if (args.length == 0 || args[0].equalsIgnoreCase("help") || !cmd.contains(args[0])) {
            p.sendMessage("§cИспользование: /armor <give/create/delete/list>");
            return true;
        }
        if (args[0].equalsIgnoreCase("give")) {
            if (args.length == 1) {
                p.sendMessage("§cИспользование: /armor give <id>");
                return true;
            }
            String name = args[1];
            if(Armor.instance.findArmor(name) == null) {
                p.sendMessage("§cТакой брони не существует!");
                return true;
            }
            p.getInventory().addItem(Armor.instance.findArmor(name));
            return true;
        }
        if (args[0].equalsIgnoreCase("list")) {
            List<String> list = Armor.instance.listArmor();
            p.sendMessage("§eСписок брони:");
            for (String s : list) {
                p.sendMessage("§e- " + s);
            }
            return true;
        }
        if (args[0].equalsIgnoreCase("create")) {
            if (args.length < 7) {
                p.sendMessage("§cИспользование: /armor create <id> <плюс к хп> <уровень> <особенность> <редкость> <название>");
                return true;
            }
            if (Armor.instance.findArmor(args[1]) != null) {
                p.sendMessage("§cБроня с таким id уже существует!");
                return true;
            }
            if (!materials.contains(p.getInventory().getItemInMainHand().getType())) {
                p.sendMessage("§cПожалуйста, возьмите в руку предмет брони!");
                return true;
            }
            int type = materials.indexOf(p.getInventory().getItemInMainHand().getType()) / 4;
            int material = materials.indexOf(p.getInventory().getItemInMainHand().getType()) % 4;
            String id = args[1];
            int health = Integer.parseInt(args[2]);
            int level = Integer.parseInt(args[3]);
            int special = Integer.parseInt(args[4]);
            int rarity = Integer.parseInt(args[5]);
            String title = String.join(" ", List.of(args).subList(6, args.length));
            Armor.instance.createArmor(id, title, type, material, health, level, special, rarity);
            p.sendMessage("§aБроня успешно создана!");
            p.getInventory().addItem(Armor.instance.findArmor(id));
            return true;
        }
        if (args[0].equalsIgnoreCase("delete")) {
            if (args.length == 1) {
                p.sendMessage("§cИспользование: /armor delete <id>");
                return true;
            }
            String name = args[1];
            if(Armor.instance.findArmor(name) == null) {
                p.sendMessage("§cТакой брони не существует!");
                return true;
            }
            Armor.instance.removeArmor(name);
            p.sendMessage("§aБроня успешно удалена!");
            return true;
        }
        return true;
    }
}
