package org.artess.arCore.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MobsCmd implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if(!(sender instanceof Player)) return true;
        Player p = (Player) sender;
        List<String> cmd = List.of("spawn", "list", "create", "delete", "edit", "stats", "drop");
        if(args.length == 0 || args[0].equalsIgnoreCase("help") || !cmd.contains(args[0])) {
            p.sendMessage("§cИспользование: /mobs <spawn/create/delete/edit/stats/drop>");
            return true;
        }
        if(args[0].equalsIgnoreCase("spawn")) {
            if(args.length == 1) {
                p.sendMessage("§cИспользование: /mobs spawn <id>");
                return true;
            }
            return true;
        }
        return true;
    }
}
