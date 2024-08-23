package org.artess.arCore;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;

public class MobsPanel implements Listener {

    public Inventory MobPanel() {
        Inventory inv = Bukkit.createInventory(null, 9, "Создание моба");

        return inv;
    }

    public void addButton() {

    }
}
