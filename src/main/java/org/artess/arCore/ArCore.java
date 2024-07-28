package org.artess.arCore;

import org.artess.arCore.commands.*;
import org.artess.arCore.tools.DelayedTask;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

public final class ArCore extends JavaPlugin {

    public static ArCore instance;

    File statsFile;
    FileConfiguration stats;
    File swordsFile;
    FileConfiguration swords;
    File floorsFile;
    public FileConfiguration floors;
    File bordersFile;
    FileConfiguration borders;
    File armorFile;
    FileConfiguration armor;
    File mobsFile;
    FileConfiguration mobs;
    File itemsFile;
    FileConfiguration items;

    public Logger logger = getLogger();

    @Override
    public void onEnable() {
        instance = this;
        saveConfig();
        statsFile = new File(getDataFolder(), "stats.yml");
        stats = YamlConfiguration.loadConfiguration(statsFile);
        saveStats();
        swordsFile = new File(getDataFolder(), "swords.yml");
        swords = YamlConfiguration.loadConfiguration(swordsFile);
        saveSwords();
        floorsFile = new File(getDataFolder(), "floors.yml");
        floors = YamlConfiguration.loadConfiguration(floorsFile);
        saveFloors();
        bordersFile = new File(getDataFolder(), "borders.yml");
        borders = YamlConfiguration.loadConfiguration(bordersFile);
        saveBorders();
        armorFile = new File(getDataFolder(), "armor.yml");
        armor = YamlConfiguration.loadConfiguration(armorFile);
        saveArmor();
        mobsFile = new File(getDataFolder(), "mobs.yml");
        mobs = YamlConfiguration.loadConfiguration(mobsFile);
        saveMobs();
        itemsFile = new File(getDataFolder(), "items.yml");
        items = YamlConfiguration.loadConfiguration(itemsFile);
        saveItems();

        new DelayedTask(this);
        Bukkit.getPluginManager().registerEvents(new Borders(), this);
        Bukkit.getPluginManager().registerEvents(new Floors(), this);
        Bukkit.getPluginManager().registerEvents(new Swords(), this);
        Bukkit.getPluginManager().registerEvents(new Stats(), this);
        Bukkit.getPluginManager().registerEvents(new MobSpawn(), this);
        Bukkit.getPluginManager().registerEvents(new Armor(), this);
        Bukkit.getPluginManager().registerEvents(new Items(), this);
        Bukkit.getPluginManager().registerEvents(new FightSystem(), this);
        this.getCommand("swords").setExecutor(new SwordsCmd());
        this.getCommand("floors").setExecutor(new FloorsCmd());
        this.getCommand("stats").setExecutor(new StatsCmd());
        this.getCommand("borders").setExecutor(new BordersCmd());
        this.getCommand("armor").setExecutor(new ArmorCmd());
        this.getCommand("mobs").setExecutor(new MobsCmd());
        this.getCommand("items").setExecutor(new ItemsCmd());
        logger.info("ArCore работает!");
    }

    @Override
    public void onDisable() {
        logger.info("ArCore выключен!");
        saveConfig();
        saveStats();
        saveSwords();
        saveFloors();
        saveBorders();
        saveArmor();
        saveMobs();
        saveItems();
    }

    public static ArCore getInstance() { return instance; }

    public void saveStats() {
        try {
            stats.save(statsFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveSwords() {
        try {
            swords.save(swordsFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveFloors() {
        try {
            floors.save(floorsFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveBorders() {
        try {
            borders.save(bordersFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveArmor() {
        try {
            armor.save(armorFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveMobs() {
        try {
            mobs.save(mobsFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveItems() {
        try {
            items.save(itemsFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

