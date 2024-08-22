package org.artess.arCore;

import org.bukkit.Location;
import org.bukkit.entity.Monster;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;


public class MobSpawn implements Listener {

    public void onMobSpawn(EntitySpawnEvent e) {
        if(e.getEntity() instanceof Monster) {
            e.setCancelled(true);
            Location loc = e.getLocation();
            double x = loc.getX();
            double z = loc.getZ();
            for (int i = 0; i < ArCore.getInstance().getConfig().getDoubleList("Borders.Radius").size(); i++) {
                double radius = ArCore.getInstance().getConfig().getDoubleList("Borders.Radius").get(i);
                double bx = ArCore.getInstance().getConfig().getDoubleList("Borders.X").get(i);
                double bz = ArCore.getInstance().getConfig().getDoubleList("Borders.Z").get(i);
                double sq = Math.sqrt(Math.pow(x - bx, 2) + Math.pow(z - bz, 2));
                if (sq > radius-3) {
                    return;
                }
            }
        }
    }

}
