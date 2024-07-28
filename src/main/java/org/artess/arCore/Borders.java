package org.artess.arCore;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.ArrayList;
import java.util.List;

public class Borders implements Listener {

    public static Borders instance = new Borders();

    @EventHandler
    public void onBorder(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        Location loc = p.getLocation();
        double x = loc.getX();
        double z = loc.getZ();
        for (int i = 0; i < ArCore.instance.borders.getConfigurationSection("Borders").getKeys(false).size(); i++) {
            String id = ArCore.instance.borders.getConfigurationSection("Borders").getKeys(false).toArray()[i].toString();
            double radius = ArCore.instance.borders.getDouble("Borders." + id + ".Radius");
            double bx = ArCore.instance.borders.getDouble("Borders." + id + ".X");
            double bz = ArCore.instance.borders.getDouble("Borders." + id + ".Z");
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

    public void setBorder(Player p, double radius, String id) {
        ArCore.instance.borders.set("Borders." + id + ".Radius", radius);
        ArCore.instance.borders.set("Borders." + id + ".X", p.getLocation().getX());
        ArCore.instance.borders.set("Borders." + id + ".Z", p.getLocation().getZ());
        ArCore.instance.saveBorders();
    }

    public void removeBorder(String id) {
        ArCore.instance.borders.set("Borders." + id, null);
        ArCore.instance.saveBorders();
    }

    public List<String> getBorders() {
        List<String> borders = new ArrayList<>();
        for (int i = 0; i < ArCore.instance.borders.getConfigurationSection("Borders").getKeys(false).size(); i++) {
            String id = ArCore.instance.borders.getConfigurationSection("Borders").getKeys(false).toArray()[i].toString();
            borders.add("§e" + id + " Центр" + " §7" + ArCore.instance.borders.getInt("Borders." + id + ".X") + " "
                    + ArCore.instance.borders.getInt("Borders." + id + ".Z") + " §eРадиус§7 " + ArCore.instance.borders.getInt("Borders." + id + ".Radius"));
        }
        return borders;
    }

}
