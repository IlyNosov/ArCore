package org.artess.arCore.commands;

import org.artess.arCore.Swords;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

public class SwordsCmd implements CommandExecutor {

    List<Material> materials = Arrays.asList(Material.WOODEN_SWORD, Material.STONE_SWORD, Material.IRON_SWORD, Material.GOLDEN_SWORD, Material.DIAMOND_SWORD, Material.NETHERITE_SWORD);

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player p = (Player) sender;
        List<String> cmd = Arrays.asList("give", "list", "create", "delete");
        if (args.length == 0 || args[0].equalsIgnoreCase("help") || !cmd.contains(args[0])) {
            p.sendMessage("§cИспользование: /swords <give/create/delete/list>");
            return true;
        }
        if (args[0].equalsIgnoreCase("give")) {
            if (args.length == 1) {
                p.sendMessage("§cИспользование: /swords give <id>");
                return true;
            }
            String name = args[1];
            if(Swords.instance.findSword(name) == null) {
                p.sendMessage("§cТакого меча не существует!");
                return true;
            }
            p.getInventory().addItem(Swords.instance.findSword(name));
            return true;
        }
        if (args[0].equalsIgnoreCase("list")) {
            List<String> list = Swords.instance.listSwords();
            p.sendMessage("§eСписок мечей:");
            for (String s : list) {
                p.sendMessage("§e- " + s);
            }
            return true;
        }
        if (args[0].equalsIgnoreCase("create")) {
            if (args.length < 10) {
                p.sendMessage("§cИспользование: /swords create <id> <урон> <уровень> <скорость> <критический урон> <шанс крита> <особенность> <редкость> <название>");
                return true;
            }
            if (Swords.instance.findSword(args[1]) != null) {
                p.sendMessage("§cМеч с таким id уже существует!");
                return true;
            }
            if (!materials.contains(p.getInventory().getItemInMainHand().getType())) {
                p.sendMessage("§cПожалуйста, возьмите в руку меч!");
                return true;
            }
            int material = materials.indexOf(p.getInventory().getItemInMainHand().getType());
            String id = args[1];
            int damage = Integer.parseInt(args[2]);
            int level = Integer.parseInt(args[3]);
            double speed = Double.parseDouble(args[4]);
            int critDamage = Integer.parseInt(args[5]);
            double critChance = Double.parseDouble(args[6]);
            int special = Integer.parseInt(args[7]);
            int rarity = Integer.parseInt(args[8]);
            String title = Arrays.stream(args).skip(9).reduce((s1, s2) -> s1 + " " + s2).orElse("");
            Swords.instance.createSword(id, title, material, damage, level, speed, critDamage, critChance, special, rarity);
            p.sendMessage("§aМеч создан!");
            p.getInventory().addItem(Swords.instance.findSword(id));
            return true;
        }
        if (args[0].equalsIgnoreCase("delete")) {
            if (args.length == 1) {
                p.sendMessage("§cИспользование: /swords delete <id>");
                return true;
            }
            String name = args[1];
            if(Swords.instance.findSword(name) == null) {
                p.sendMessage("§cТакого меча не существует!");
                return true;
            }
            Swords.instance.deleteSword(name);
            p.sendMessage("§aМеч удален!");
            return true;
        }
        return true;
    }
}
