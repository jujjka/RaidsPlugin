package me.jujjka.raidplugin.utils;

import com.palmergames.bukkit.towny.object.Town;

import java.util.HashMap;

public class ProtectOnTown {


    public static HashMap<Town, Integer> protect = new HashMap<>();


    public static void setProtect(Town town,int protectTime){
        protect.put(town,protectTime);
    }
    public static Integer getProtect(Town town, int protectTime){
        return protect.get(town);
    }
    public static void remove(Town town){
        protect.remove(town);
    }
}
