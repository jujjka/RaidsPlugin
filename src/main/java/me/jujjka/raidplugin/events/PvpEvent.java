package me.jujjka.raidplugin.events;

import com.palmergames.bukkit.towny.event.damage.TownyPlayerDamagePlayerEvent;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.Town;
import me.jujjka.raidplugin.modules.Raid;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PvpEvent implements Listener {

    @EventHandler
    public void damage(TownyPlayerDamagePlayerEvent e){
        if(e.isCancelled()){
            Resident victim = e.getVictimResident();
            Town attackTown = e.getAttackerTown();
                if(Raid.getRaidByTown(attackTown) != null){
                    Raid raid = Raid.getRaidByTown(attackTown);
                    if(raid.getTargetTown().getResidents().contains(victim) && raid.getMembers().contains(attackTown)){
                        if(raid.isRemaining()){
                            e.setCancelled(false);
                        }
                    }
                }
        }

    }
}
