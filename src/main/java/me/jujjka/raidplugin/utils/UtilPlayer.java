package me.jujjka.raidplugin.utils;

import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.Town;

public class UtilPlayer {


    public static int getOnlinePlayerForMembers(Town town){
        int amount = 0;
        for(Resident resident: town.getResidents()){
            if(resident.getPlayer() != null){
                if(resident.getPlayer().isOnline()) {
                    amount++;
                } else {
                }
            } else {
                }
        }
        return amount;
    }
}
