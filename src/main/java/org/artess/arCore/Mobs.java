package org.artess.arCore;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Mob;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class Mobs implements Listener {

    public void spawnMob(EntityType type, String name, int health, int level, int xp, int damage, ItemStack weapon,
                         ItemStack helmet, ItemStack chestplate, ItemStack leggings, ItemStack boots, Location loc) {
        Mob mob = (Mob) loc.getWorld().spawnEntity(loc, type);
        mob.setCustomName(name);
    }

    //TODO
}
