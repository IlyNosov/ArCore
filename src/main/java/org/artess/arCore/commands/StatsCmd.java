package org.artess.arCore.commands;

import org.artess.arCore.Stats;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class StatsCmd implements CommandExecutor {

    //Команды:
    // /stats <Ник> - показывает статистику игрока
    // /stats add <Ник> <Стат> <Кол-во> - добавляет статистику игроку
    // /stats remove <Ник> <Стат> <Кол-во> - убирает статистику игроку
    // /stats set <Ник> <Стат> <Кол-во> - устанавливает статистику игроку
    // /stats reset <Ник> - сбрасывает статистику игрока
    // /stats list - показывает список статистик


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player p = (Player) sender;
        List<String> cmd = List.of("add", "remove", "set", "reset", "list");
        if (args.length == 0 || args[0].equalsIgnoreCase("help") || (!cmd.contains(args[0]) && args.length > 1)) {
            p.sendMessage("§cИспользование: /stats <Ник/add/remove/set/reset/list>");
            return true;
        }
        if (args[0].equalsIgnoreCase("list")) {
            p.sendMessage("§eСписок статистик:");
            p.sendMessage("§e- Этаж (Floor)");
            p.sendMessage("§e- Уровень (Level)");
            p.sendMessage("§e- Опыт (XP)");
            p.sendMessage("§e- Здоровье (Health)");
            p.sendMessage("§e- Атака (Attack)");
            p.sendMessage("§e- Выносливость (Stamina)");
            p.sendMessage("§e- Ловкость (Dexterity)");
            p.sendMessage("§e- Скрытность (Stealth)");
            p.sendMessage("§e- Мудрость (Wisdom)");
            p.sendMessage("§e- Удача (Luck)");
            p.sendMessage("§e- Очки навыков (Points)");
            return true;
        }
        if (args[0].equalsIgnoreCase("add")) {
            if (args.length < 4) {
                p.sendMessage("§cИспользование: /stats add <Ник> <Стат> <Кол-во>");
                return true;
            }
            String name = args[1];
            String stat = args[2];
            int amount = Integer.parseInt(args[3]);
            if (amount < 0) {
                p.sendMessage("§cКоличество не может быть отрицательным!");
                return true;
            }
            if (stat.equals("Floor") || stat.equals("Level") || stat.equals("XP") || stat.equals("Health") || stat.equals("Attack") || stat.equals("Stamina") || stat.equals("Dexterity") || stat.equals("Stealth") || stat.equals("Wisdom") || stat.equals("Luck") || stat.equals("Points")) {
                Player player = Bukkit.getPlayer(name);
                if (player == null) {
                    p.sendMessage("§cИгрок не найден!");
                    return true;
                }
                int current = Stats.instance.getStat(player, stat);
                Stats.instance.setStat(player, stat, current + amount);
                p.sendMessage("§aСтатистика изменена!");
                return true;
            }
            p.sendMessage("§cТакой статистики не существует!");
            return true;
        }
        if (args[0].equalsIgnoreCase("remove")) {
            if (args.length < 4) {
                p.sendMessage("§cИспользование: /stats remove <Ник> <Стат> <Кол-во>");
                return true;
            }
            String name = args[1];
            String stat = args[2];
            int amount = Integer.parseInt(args[3]);
            if (amount < 0) {
                p.sendMessage("§cКоличество не может быть отрицательным!");
                return true;
            }
            if (stat.equals("Floor") || stat.equals("Level") || stat.equals("XP") || stat.equals("Health") || stat.equals("Attack") || stat.equals("Stamina") || stat.equals("Dexterity") || stat.equals("Stealth") || stat.equals("Wisdom") || stat.equals("Luck") || stat.equals("Points")) {
                Player player = Bukkit.getPlayer(name);
                if (player == null) {
                    p.sendMessage("§cИгрок не найден!");
                    return true;
                }
                int current = Stats.instance.getStat(player, stat);
                if (current - amount < 0) {
                    p.sendMessage("§cЗначение не может превышать количество очков данной статистики у игрока!");
                    return true;
                }
                Stats.instance.setStat(player, stat, current - amount);
                p.sendMessage("§aСтатистика изменена!");
                return true;
            }
            p.sendMessage("§cТакой статистики не существует!");
            return true;
        }
        if (args[0].equalsIgnoreCase("set")) {
            if (args.length < 4) {
                p.sendMessage("§cИспользование: /stats set <Ник> <Стат> <Кол-во>");
                return true;
            }
            String name = args[1];
            String stat = args[2];
            int amount = Integer.parseInt(args[3]);
            if (amount < 0) {
                p.sendMessage("§cКоличество не может быть отрицательным!");
                return true;
            }
            if (stat.equals("Floor") || stat.equals("Level") || stat.equals("XP") || stat.equals("Health") || stat.equals("Attack") || stat.equals("Stamina") || stat.equals("Dexterity") || stat.equals("Stealth") || stat.equals("Wisdom") || stat.equals("Luck") || stat.equals("Points")) {
                Player player = Bukkit.getPlayer(name);
                if (player == null) {
                    p.sendMessage("§cИгрок не найден!");
                    return true;
                }
                Stats.instance.setStat(player, stat, amount);
                p.sendMessage("§aСтатистика изменена!");
                return true;
            }
            p.sendMessage("§cТакой статистики не существует!");
            return true;
        }
        if (args[0].equalsIgnoreCase("reset")) {
            if (args.length < 2) {
                p.sendMessage("§cИспользование: /stats reset <Ник>");
                return true;
            }
            String name = args[1];
            Player player = Bukkit.getPlayer(name);
            if (player == null) {
                p.sendMessage("§cИгрок не найден!");
                return true;
            }
            Stats.instance.setStat(player, "Floor", 1);
            Stats.instance.setStat(player, "Level", 1);
            Stats.instance.setStat(player, "XP", 0);
            Stats.instance.setStat(player, "Health", 100);
            Stats.instance.setStat(player, "Attack", 1);
            Stats.instance.setStat(player, "Stamina", 1);
            Stats.instance.setStat(player, "Dexterity", 1);
            Stats.instance.setStat(player, "Stealth", 1);
            Stats.instance.setStat(player, "Wisdom", 1);
            Stats.instance.setStat(player, "Luck", 1);
            Stats.instance.setStat(player, "Points", 2);
            p.sendMessage("§aСтатистика сброшена!");
            return true;
        }
        if (args.length == 1) {
            String name = args[0];
            Player player = Bukkit.getPlayer(name);
            if (player == null) {
                p.sendMessage("§cИгрок не найден!");
                return true;
            }
            p.sendMessage("§eСтатистика игрока " + name + ":");
            p.sendMessage("§e- Этаж: " + Stats.instance.getStat(player, "Floor"));
            p.sendMessage("§e- Уровень: " + Stats.instance.getStat(player, "Level"));
            p.sendMessage("§e- Опыт: " + Stats.instance.getStat(player, "XP"));
            p.sendMessage("§e- Здоровье: " + Stats.instance.getStat(player, "Health"));
            p.sendMessage("§e- Атака: " + Stats.instance.getStat(player, "Attack"));
            p.sendMessage("§e- Выносливость: " + Stats.instance.getStat(player, "Stamina"));
            p.sendMessage("§e- Ловкость: " + Stats.instance.getStat(player, "Dexterity"));
            p.sendMessage("§e- Скрытность: " + Stats.instance.getStat(player, "Stealth"));
            p.sendMessage("§e- Мудрость: " + Stats.instance.getStat(player, "Wisdom"));
            p.sendMessage("§e- Удача: " + Stats.instance.getStat(player, "Luck"));
            p.sendMessage("§e- Очки навыков: " + Stats.instance.getStat(player, "Points"));
            return true;
        }
        return true;
    }

}
