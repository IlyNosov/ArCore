package org.artess.arCore.commands;

import org.artess.arCore.Items;
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
            p.sendMessage("§cИспользование: /armor <give/create/delete/list>");
            return true;
        }
        if (args[0].equalsIgnoreCase("give")) {
            if (args.length == 1) {
                p.sendMessage("§cИспользование: /item give <id>");
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
            if (args.length < 7) {
                p.sendMessage("§cИспользование: /item create <id> <материал> <лор (_ вместо пробелов)> <редкость> <тип> <название (_ вместо пробелов)>");
                return true;
            }
            if (Items.instance.findItem(args[1]) != null) {
                p.sendMessage("§cПредмет с таким id уже существует!");
                return true;
            }
            String material = p.getInventory().getItemInMainHand().getType().name();
            String id = args[1];
            String lore = args[3].replace('_', ' ');
            int rarity = Integer.parseInt(args[4]);
            int type = Integer.parseInt(args[5]);
            String title = args[6].replace('_', ' ');
            Items.instance.createItem(id, title, material, rarity, lore, type);
            p.sendMessage("§aПредмет успешно создан!");
            p.getInventory().addItem(Items.instance.findItem(id));
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