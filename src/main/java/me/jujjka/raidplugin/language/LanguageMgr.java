package me.jujjka.raidplugin.language;

import me.jujjka.raidplugin.Raidplugin;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class LanguageMgr {
    public static FileConfiguration getLang(){
        String lang = Raidplugin.getInstance().getConfig().getString("RaidsPlugin.lang");
        File file = new File(Raidplugin.getInstance().getDataFolder().getPath(),lang);
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        return config;
    }
}
