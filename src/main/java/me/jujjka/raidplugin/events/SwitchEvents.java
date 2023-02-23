package me.jujjka.raidplugin.events;

import com.palmergames.bukkit.towny.TownyUniverse;
import com.palmergames.bukkit.towny.event.actions.TownySwitchEvent;
import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.Town;
import me.jujjka.raidplugin.modules.Raid;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class SwitchEvents implements Listener {


    @EventHandler
    public void onSwitch(TownySwitchEvent e) throws NotRegisteredException {
        if(e.isCancelled()){
            Player player = e.getPlayer();
            if(e.getTownBlock() != null){
                if(e.getTownBlock().getTown() != null){
                    Town targetTown = e.getTownBlock().getTown();
                    if(TownyUniverse.getInstance().hasResident(player.getName())){
                        TownyUniverse townyUniverse = TownyUniverse.getInstance();
                        Resident clicked = townyUniverse.getResident(player.getName());
                        if(clicked.hasTown()){
                            Town clickedTown = clicked.getTown();
                            if(Raid.getRaidByTown(clickedTown) != null){
                                Raid raid = Raid.getRaidByTown(clickedTown);
                                if(raid.getTargetTown().equals(targetTown)){
                                    if(clickedTown != raid.getTargetTown()){
                                        if(raid.getMembers().contains(clickedTown)){
                                            if(raid.isRemaining()){
                                                e.setCancelled(false);
                                            }
                                        }

                                    }

                                }

                            }
                        }
                    }
                }

            }
        }

    }
}
