package org.artess.arCore.Old;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.List;

public class BorderOld implements Listener {

    /*@EventHandler
    public void onBorder(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        Location loc = p.getLocation();
        double x = loc.getX();
        double z = loc.getZ();
        for (int i = 0; i < ArCore.getInstance().getConfig().getDoubleList("Borders.Radius").size(); i++) {
            double radius = ArCore.getInstance().getConfig().getDoubleList("Borders.Radius").get(i);
            double bx = ArCore.getInstance().getConfig().getDoubleList("Borders.X").get(i);
            double bz = ArCore.getInstance().getConfig().getDoubleList("Borders.Z").get(i);
            double sq = Math.sqrt(Math.pow(x - bx, 2) + Math.pow(z - bz, 2));
            if (radius+1 < sq && sq > radius-1) {
                p.spawnParticle(Particle.PORTAL, p.getLocation(), 100, 0.5, 0.5, 0.5, 0);
                p.playSound(p.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1, 1);
                double dx = (x - bx) * (radius / sq - 1);
                double dz = (z - bz) * (radius / sq - 1);
                p.teleport(new Location(p.getWorld(), x + dx, loc.getY(), z + dz, loc.getYaw(), loc.getPitch()));
                p.sendMessage(ChatColor.RED + "Вы достигли границы!");
            }
        }
    }

    @EventHandler
    public void SetBorder(AsyncPlayerChatEvent e) {
        if (e.getMessage().startsWith(".setborder")) {
            e.setCancelled(true);
            if (!e.getPlayer().isOp()) {
                e.getPlayer().sendMessage(ChatColor.RED + "У вас нет прав на использование этой команды!");
                return;
            }
            String[] args = e.getMessage().split(" ");
            if (args.length != 2) {
                e.getPlayer().sendMessage(ChatColor.YELLOW + "Использование: .setborder <радиус>");
                return;
            }
            double radius = Double.parseDouble(args[1]);
            List<Double> radiuses = ArCore.getInstance().getConfig().getDoubleList("Borders.Radius");
            radiuses.add(radius);
            ArCore.getInstance().getConfig().set("Borders.Radius", radiuses);
            List<Double> x = ArCore.getInstance().getConfig().getDoubleList("Borders.X");
            x.add(e.getPlayer().getLocation().getX());
            ArCore.getInstance().getConfig().set("Borders.X", x);
            List<Double> z = ArCore.getInstance().getConfig().getDoubleList("Borders.Z");
            z.add(e.getPlayer().getLocation().getZ());
            ArCore.getInstance().getConfig().set("Borders.Z", z);
            List<Integer> NN = ArCore.getInstance().getConfig().getIntegerList("Borders.NN");
            NN.add(NN.size()+1);
            ArCore.getInstance().getConfig().set("Borders.NN", NN);
            ArCore.getInstance().saveConfig();
            e.getPlayer().sendMessage(ChatColor.GREEN + "Граница установлена!");
        }
        if (e.getMessage().startsWith(".removeborder")) {
            e.setCancelled(true);
            if (!e.getPlayer().isOp()) {
                e.getPlayer().sendMessage(ChatColor.RED + "У вас нет прав на использование этой команды!");
                return;
            }
            String[] args = e.getMessage().split(" ");
            if (args.length != 2) {
                e.getPlayer().sendMessage(ChatColor.YELLOW + "Использование: .removeborder <номер границы>");
                return;
            }
            int n = Integer.parseInt(args[1]);
            List<Double> radiuses = ArCore.getInstance().getConfig().getDoubleList("Borders.Radius");
            List<Double> x = ArCore.getInstance().getConfig().getDoubleList("Borders.X");
            List<Double> z = ArCore.getInstance().getConfig().getDoubleList("Borders.Z");
            List<Integer> NN = ArCore.getInstance().getConfig().getIntegerList("Borders.NN");
            if (NN.contains(n)) {
                int i = NN.indexOf(n);
                radiuses.remove(i);
                x.remove(i);
                z.remove(i);
                NN.remove(i);
                ArCore.getInstance().getConfig().set("Borders.Radius", radiuses);
                ArCore.getInstance().getConfig().set("Borders.X", x);
                ArCore.getInstance().getConfig().set("Borders.Z", z);
                ArCore.getInstance().getConfig().set("Borders.NN", NN);
                ArCore.getInstance().saveConfig();
                e.getPlayer().sendMessage(ChatColor.GREEN + "Граница удалена!");
                for (int j = n; j < NN.size(); j++) {
                    NN.set(j, NN.get(j) - 1);
                }
            } else
            e.getPlayer().sendMessage(ChatColor.RED + "Граница не найдена!");
        }
        if(e.getMessage().startsWith(".listborder")) {
            e.setCancelled(true);
            if (!e.getPlayer().isOp()) {
                e.getPlayer().sendMessage(ChatColor.RED + "У вас нет прав на использование этой команды!");
                return;
            }
            if (ArCore.getInstance().getConfig().getIntegerList("Borders.NN").isEmpty()){
                e.getPlayer().sendMessage(ChatColor.YELLOW + "Список границ пуст!");
                return;
            }
            e.getPlayer().sendMessage(ChatColor.YELLOW + "Список границ:");
            List<Integer> NN = ArCore.getInstance().getConfig().getIntegerList("Borders.NN");
            for (int i = 0; i < NN.size(); i++) {
                e.getPlayer().sendMessage(ChatColor.YELLOW + "Граница " + NN.get(i) + ": Радиус " + ArCore.getInstance().getConfig().getDoubleList("Borders.Radius").get(i) + ", координаты X: " + ArCore.getInstance().getConfig().getDoubleList("Borders.X").get(i) + ", координаты Z: " + ArCore.getInstance().getConfig().getDoubleList("Borders.Z").get(i));
            }
        }
    } */
}
