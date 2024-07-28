package org.artess.arCore.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class BordersCmd implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cКоманда доступна только игрокам!");
            return true;
        }
        Player p = (Player) sender;
        List<String> cmd = List.of("set", "delete", "list");
        if (args.length == 0 || args[0].equalsIgnoreCase("help") || !cmd.contains(args[0])) {
            p.sendMessage("§cИспользование: /borders <set/delete/list>");
            return true;
        }
        if (args[0].equalsIgnoreCase("list")) {
            if (org.artess.arCore.Borders.instance.getBorders().isEmpty()) {
                p.sendMessage("§cСписок границ пуст!");
                return true;
            }
            p.sendMessage("§eСписок границ:");
            for (String border : org.artess.arCore.Borders.instance.getBorders()) {
                p.sendMessage(border);
            }
            return true;
        }
        if (args[0].equalsIgnoreCase("set")) {
            if (args.length < 3) {
                p.sendMessage("§cИспользование: /borders set <название> <радиус>");
                return true;
            }
            String id = args[1];
            double radius = Double.parseDouble(args[2]);
            org.artess.arCore.Borders.instance.setBorder(p, radius, id);
            p.sendMessage("§aГраница " + id + " добавлена!");
            return true;
        }
        if (args[0].equalsIgnoreCase("delete")) {
            if (args.length < 2) {
                p.sendMessage("§cИспользование: /borders delete <название>");
                return true;
            }
            String id = args[1];
            org.artess.arCore.Borders.instance.removeBorder(id);
            p.sendMessage("§aГраница " + id + " удалена!");
            return true;
        }
        return true;
    }
}
