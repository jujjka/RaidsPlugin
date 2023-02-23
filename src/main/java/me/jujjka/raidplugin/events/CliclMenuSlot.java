package me.jujjka.raidplugin.events;

import com.palmergames.bukkit.towny.TownyUniverse;
import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.Town;
import me.jujjka.raidplugin.language.LanguageMgr;
import me.jujjka.raidplugin.messages.MessagerMg;
import me.jujjka.raidplugin.modules.Raid;
import me.jujjka.raidplugin.utils.FormaterTime;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class CliclMenuSlot implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent e) throws NotRegisteredException {
        Player player = (Player) e.getWhoClicked();


        if (e.getView().getTitle().equalsIgnoreCase(LanguageMgr.getLang().getString("ActiveTownForRaid"))){
            e.setCancelled(true);
            if(e.getCurrentItem() != null){
                ItemStack item = e.getCurrentItem();
                if(item.getType() != Material.GREEN_STAINED_GLASS && item.getType() != Material.RED_STAINED_GLASS && item.getType() != Material.COMPASS){

                    TownyUniverse townyUniverse = TownyUniverse.getInstance();
                    Town targetTown = townyUniverse.getTown(item.getItemMeta().getDisplayName().substring(8));
                        Resident resident = townyUniverse.getResident(player.getUniqueId());
                        if(!Raid.getTownsOnRaid().contains(targetTown) && !Raid.getTownsOnRaid().contains(resident.getTown())){
                        List<Town> members = new ArrayList<>();
                        members.add(resident.getTown());
                        Raid raid = new Raid(targetTown.getName(),player, targetTown.getMayor().getPlayer(), targetTown, resident.getTown(), members);
                        raid.createRaid(targetTown.getName(),player, targetTown.getMayor().getPlayer(), targetTown, resident.getTown(), members);
                        String startRaid = LanguageMgr.getLang().getString("StartRaid");
                        Raid.sendAllMessages(String.format(startRaid, targetTown.getName(), targetTown.getName(), FormaterTime.FormatTime(raid)));
                    } else {
                            player.sendMessage(LanguageMgr.getLang().getString("TownAlreadyOnRaid"));
                            player.closeInventory();
                        }


                }
            }
        } else if(e.getView().getTitle().equalsIgnoreCase(LanguageMgr.getLang().getString("InviteTownMenu"))) {
            if (e.getCurrentItem() != null) {
                ItemStack item = e.getCurrentItem();
                if (item.getType() != Material.GREEN_STAINED_GLASS && item.getType() != Material.RED_STAINED_GLASS  && item.getType() != Material.COMPASS) {
                    e.setCancelled(true);
                    TownyUniverse townyUniverse = TownyUniverse.getInstance();
                    Town targetTown = townyUniverse.getTown(item.getItemMeta().getDisplayName().substring(8));
                    Resident resident = townyUniverse.getResident(player.getUniqueId());
                    Raid raid = Raid.getRaidByTown(resident.getTown());
                    if(raid!= null){
                        if(!Raid.getTownsOnRaid().contains(targetTown)){
                            MessagerMg.messageInvite(targetTown, resident.getTown());
                            player.sendMessage(LanguageMgr.getLang().getString("SendInviteMesssage"));
                        } else {
                            player.sendMessage(LanguageMgr.getLang().getString("TownAlreadyOnRaid"));
                        }
                    } else {
                        player.sendMessage(LanguageMgr.getLang().getString("NotRaid"));
                    }

                }
            }
        } else if(e.getView().getTitle().equalsIgnoreCase(LanguageMgr.getLang().getString("ActiveRaids"))) {
            e.setCancelled(true);
        }
    }
}
