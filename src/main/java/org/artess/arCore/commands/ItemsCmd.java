package org.artess.arCore.commands;

import org.artess.arCore.Items;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ItemsCmd implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player p = (Player) sender;
        List<String> cmd = List.of("give", "list", "create", "delete");
        if (args.length == 0 || args[0].equalsIgnoreCase("help") || !cmd.contains(args[0])) {
            p.sendMessage("§cИспользование: /items <give/create/delete/list>");
            return true;
        }
        if (args[0].equalsIgnoreCase("give")) {
            if (args.length == 1) {
                p.sendMessage("§cИспользование: /items give <id>");
                return true;
            }
            String name = args[1];
            if(Items.instance.findItem(name) == null) {
                p.sendMessage("§cТакого предмета не существует!");
                return true;
            }
            p.getInventory().addItem(Items.instance.findItem(name));
            return true;
        }
        if (args[0].equalsIgnoreCase("list")) {
            List<String> list = Items.instance.listItems();
            p.sendMessage("§eСписок предметов:");
            for (String s : list) {
                p.sendMessage("§e- " + s);
            }
            return true;
        }
        if (args[0].equalsIgnoreCase("create")) {
            if (args.length < 5) {
                p.sendMessage("§cИспользование: /items create <id> <редкость> <тип> <название; лор>");
                return true;
            }
            if (p.getInventory().getItemInMainHand().getType() == Material.AIR) {
                p.sendMessage("§cВозьмите предмет в руку!");
                return true;
            }
            if (Items.instance.findItem(args[1]) != null) {
                p.sendMessage("§cПредмет с таким id уже существует!");
                return true;
            }
            String name = args[1];
            int rarity = Integer.parseInt(args[2]);
            int type = Integer.parseInt(args[3]);
            String split = String.join(" ", List.of(args).subList(4, args.length));
            String title;
            String lore;
            if (!split.contains(";")) {
                lore = null;
                title = split;
            } else {
                String[] split2 = split.split(";");
                title = split2[0];
                lore = split2[1];
            }
            String mat = p.getInventory().getItemInMainHand().getType().name();
            Items.instance.createItem(name, title, mat, rarity, lore, type);
            p.sendMessage("§aПредмет успешно создан!");
            p.getInventory().addItem(Items.instance.findItem(name));
            return true;
        }
        if (args[0].equalsIgnoreCase("delete")) {
            if (args.length == 1) {
                p.sendMessage("§cИспользование: /item delete <id>");
                return true;
            }
            String name = args[1];
            if(Items.instance.findItem(name) == null) {
                p.sendMessage("§cТакого предмета не существует!");
                return true;
            }
            Items.instance.removeItem(name);
            p.sendMessage("§aПредмет успешно удален!");
            return true;
        }
        return true;
    }
}