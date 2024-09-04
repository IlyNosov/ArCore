package org.artess.arCore;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import org.bukkit.util.Vector;

public class FightSystem implements Listener {

    public static FightSystem instance = new FightSystem();

    @EventHandler
    public void PlayerGetDamage(EntityDamageEvent e) {
        if (!(e.getEntity() instanceof Player)) return;
        Player p = (Player) e.getEntity();
        double Chance = ArCore.getInstance().stats.getInt("Players." + p.getName() + ".Wisdom") / 200.0;
        if (Math.random() < Chance) {
            e.setDamage(0);
            p.spawnParticle(Particle.HEART, p.getLocation(), 1, 0.5, 0.5, 0.5, 0);
        }
    }

    @EventHandler
    public void PlayerHit(EntityDamageByEntityEvent e) {
        if (!(e.getDamager() instanceof Player)) return;
        Player p = (Player) e.getDamager();
        if (!p.getInventory().getItemInMainHand().hasItemMeta()) return;
        String sword = p.getInventory().getItemInMainHand().getItemMeta().getLore().get(
                p.getInventory().getItemInMainHand().getItemMeta().getLore().size() - 1);
        sword = sword.substring(3);
        if (!ArCore.getInstance().swords.contains("Swords." + sword)) return;
        if (e.getEntity() instanceof ArmorStand) return;
        double damage = ArCore.getInstance().swords.getDouble("Swords." + sword + ".Damage");
        double CritChance = ArCore.getInstance().stats.getInt("Players." + p.getName() + ".Dexterity") / 200.0;
        CritChance += ArCore.getInstance().swords.getDouble("Swords." + sword + ".CritChance");
        damage += damage * ArCore.getInstance().stats.getInt("Players." + p.getName() + ".Attack") / 200.0;
        String damageInfo = "";
        damageInfo += "§c" + damage + "§c❤";
        if (Math.random() < CritChance) {
            double critDamage = ArCore.getInstance().swords.getDouble("Swords." + sword + ".CritDamage") +
                    ArCore.getInstance().stats.getInt("Players." + p.getName() + ".Attack") / 200.0 *
                            ArCore.getInstance().swords.getDouble("Swords." + sword + ".CritDamage");
            damageInfo += " §6+" + critDamage + "§6✴";
            damage += critDamage;
            p.spawnParticle(Particle.CRIT_MAGIC, e.getEntity().getLocation(), 10, 0.5, 0.5, 0.5, 0);
        }
        Location loc = e.getEntity().getLocation();
        loc.setY(loc.getY() + Math.random());
        loc.setX(loc.getX() + (Math.random() - 0.5)*2);
        loc.setZ(loc.getZ() + (Math.random() - 0.5)*2);
        ArmorStand info = e.getEntity().getWorld().spawn(loc, ArmorStand.class);
        p.sendMessage(damageInfo);
        info.setVisible(false);
        info.setCustomName(damageInfo);
        info.setCustomNameVisible(true);
        info.setGravity(true);
        info.setInvulnerable(true);
        info.setVelocity(new Vector(0, 0.1, 0));
        if(Bukkit.getServer().isStopping()) {
            info.remove();
            return;
        }
        Bukkit.getScheduler().runTaskLater(ArCore.getInstance(), info::remove, 20);
        e.setDamage(damage);
    }

    //TODO
}
