package me.jujjka.raidplugin.events;

import com.palmergames.bukkit.towny.event.damage.TownyPlayerDamagePlayerEvent;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.Town;
import me.jujjka.raidplugin.modules.Raid;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PvpEventTrg implements Listener {

    @EventHandler
    public void damage(TownyPlayerDamagePlayerEvent e) {
        if (e.isCancelled()) {
            Resident attack = e.getAttackingResident();
            Town victimTown = e.getVictimTown();
                if (Raid.getRaidByTown(victimTown) != null) {
                    Raid raid = Raid.getRaidByTown(victimTown);
                    if (raid.getTargetTown().getResidents().contains(attack) && raid.getMembers().contains(victimTown)) {
                        if (raid.isRemaining()) {
                            e.setCancelled(false);
                        }
                    }
                }
        }
    }
}