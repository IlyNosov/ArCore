package org.artess.arCore.commands;

import org.artess.arCore.ArCore;
import org.artess.arCore.Floors;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class FloorsCmd implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player p = (Player) sender;
        List<String> cmd = List.of("set", "list", "delete");
        if (args.length == 0 || args[0].equalsIgnoreCase("help") || !cmd.contains(args[0])) {
            p.sendMessage("§cИспользование: /floors <set/delete/list>");
            return true;
        }
        if (args[0].equalsIgnoreCase("set")) {
            if (args.length == 1) {
                p.sendMessage("§cИспользование: /floors set <номер этажа>");
                return true;
            }
            int floor = Integer.parseInt(args[1]);
            if (floor < 1 || floor > 24) {
                p.sendMessage("§cНомер этажа должен быть от 1 до 24!");
                return true;
            }
            Floors.instance.newFloor(p, floor);
            p.sendMessage("§aТелепорт на этаж " + floor + " установлен!");
            return true;
        }
        if (args[0].equalsIgnoreCase("list")) {
            List<String> list = Floors.instance.listFloors();
            p.sendMessage("§eСписок этажей:");
            for (String s : list) {
                p.sendMessage(s);
            }
        }
        if (args[0].equalsIgnoreCase("delete")) {
            if (args.length == 1) {
                p.sendMessage("§cИспользование: /floors delete <номер этажа>");
                return true;
            }
            int floor = Integer.parseInt(args[1]);
            if (floor < 1 || floor > 24) {
                p.sendMessage("§cНомер этажа должен быть от 1 до 24!");
                return true;
            }
            if (!ArCore.instance.floors.contains("Floors." + floor)) {
                p.sendMessage("§cТакого этажа не существует!");
                return true;
            }
            Floors.instance.removeFloor(floor);
            p.sendMessage("§aТелепорт на этаж " + floor + " удален!");
        }
        return true;
    }
}
