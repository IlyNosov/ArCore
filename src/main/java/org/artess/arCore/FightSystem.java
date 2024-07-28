package org.artess.arCore;

import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

public class FightSystem implements Listener {

    public static FightSystem instance = new FightSystem();

    public void PlayerGetDamage(EntityDamageEvent e) {
        if (!(e.getEntity() instanceof Player)) return;
        Player p = (Player) e.getEntity();
        double wisdom = ArCore.getInstance().stats.getInt("Players." + p.getName() + ".Wisdom") / 200.0;
        if (Math.random() < wisdom) {
            e.setCancelled(true);
            p.damage(0);
            p.spawnParticle(Particle.HEART, p.getLocation(), 10, 0.5, 0.5, 0.5, 0);
        }
    }

    public void PlayerHit(EntityDamageByEntityEvent e) {
        if (!(e.getDamager() instanceof Player)) return;
        Player p = (Player) e.getDamager();
        if (!ArCore.getInstance().swords.contains(p.getInventory().getItemInMainHand().getItemMeta().getDisplayName())) return;
        ItemStack sword = p.getInventory().getItemInMainHand();
        double damage = ArCore.getInstance().swords.getDouble("Swords." + sword + ".Damage");
        double CritChance = ArCore.getInstance().stats.getInt("Players." + p.getName() + ".Dexterity") / 200.0;
        CritChance += ArCore.getInstance().swords.getDouble("Swords." + sword + ".CritChance");
        String damageInfo;
        if (Math.random() < CritChance) {
            damage += ArCore.getInstance().swords.getDouble("Swords." + sword + ".CritDamage");
            p.spawnParticle(Particle.CRIT_MAGIC, e.getEntity().getLocation(), 10, 0.5, 0.5, 0.5, 0);
        }
        damage += damage * ArCore.getInstance().stats.getInt("Players." + p.getName() + ".Attack") / 200.0;
        e.setDamage(damage);
    }
}
