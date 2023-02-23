package me.jujjka.raidplugin.events;

import com.palmergames.bukkit.towny.event.TownRemoveResidentEvent;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.Town;
import me.jujjka.raidplugin.modules.Raid;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerLeftTown implements Listener {

    @EventHandler
    public void onLeft(TownRemoveResidentEvent e){
        Town playerTown = e.getTown();
        Resident resident = e.getResident();
        Player player = e.getResident().getPlayer();
        if(Raid.getRaidByTown(playerTown) != null){
            Raid raid = Raid.getRaidByTown(playerTown);
            if(raid.isRemaining()){
                raid.getBossBarAttackREMAINING().removeBarFrom(player);
            } else if(raid.isPreparation()){
                raid.getBossBarAttackPREPARATION().removeBarFrom(player);
            }
        }
    }
}
