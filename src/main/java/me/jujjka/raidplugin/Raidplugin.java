package me.jujjka.raidplugin;

import me.jujjka.raidplugin.commands.CreateRaid;
import me.jujjka.raidplugin.commands.Raids;
import me.jujjka.raidplugin.events.*;
import me.jujjka.raidplugin.language.EngLang;
import me.jujjka.raidplugin.language.RuLang;
import org.bukkit.plugin.java.JavaPlugin;

public final class Raidplugin extends JavaPlugin{


    private static Raidplugin plugin;



    @Override
    public void onEnable() {

            plugin = this;

            //CONSOLE MESSAGES
            this.getLogger().info("====================      RaidPlugin     ========================");
            this.getLogger().info("Version: " + getDescription().getVersion());
            this.getLogger().info("Author: " + getDescription().getAuthors());
            this.getLogger().info("====================      RaidPlugin     ========================");

            //COMMANDS
            getCommand("raid").setExecutor(new CreateRaid());
            getCommand("raids").setExecutor(new Raids());
            //EVENTS
            getServer().getPluginManager().registerEvents(new ClickMenu(), this);
            getServer().getPluginManager().registerEvents(new SwitchEvents(), this);
            getServer().getPluginManager().registerEvents(new ClickPage(), this);
            getServer().getPluginManager().registerEvents(new PvpEvent(), this);
            getServer().getPluginManager().registerEvents(new PvpEventTrg(), this);
            getServer().getPluginManager().registerEvents(new CliclMenuSlot(), this);
            getServer().getPluginManager().registerEvents(new PlayerLeftTown(), this);
            //Files
            this.getConfig().options().copyDefaults();
            this.saveDefaultConfig();
            EngLang.setupMessages();
            RuLang.setupMessages();

            //bStats
            int pluginId = 17774; // <-- Replace with the id of your plugin!
            Metrics metrics = new Metrics(this, pluginId);
    }
    public static Raidplugin getInstance() {
        return plugin;
    }
}
