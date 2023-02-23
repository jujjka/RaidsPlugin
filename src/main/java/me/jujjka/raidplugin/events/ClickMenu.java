package me.jujjka.raidplugin.events;

import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import me.jujjka.raidplugin.inventory.ActiveRaidsMenu;
import me.jujjka.raidplugin.inventory.CreateRaidMenu;
import me.jujjka.raidplugin.inventory.InviteRaidsMenu;
import me.jujjka.raidplugin.inventory.ScrollerInventory;
import me.jujjka.raidplugin.language.LanguageMgr;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class ClickMenu implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent e) throws NotRegisteredException {
        Player player = (Player) e.getWhoClicked();
            if(e.getView().getTitle().equalsIgnoreCase(LanguageMgr.getLang().getString("MenuRaids"))){
                if(e.getCurrentItem() != null){
                    if(e.getCurrentItem().getType() == Material.DIAMOND_SWORD){
                        e.setCancelled(true);
                        CreateRaidMenu createRaidMenu = new CreateRaidMenu(player);
                        new ScrollerInventory(createRaidMenu.items,LanguageMgr.getLang().getString("ActiveTownForRaid"),player);
                    } else if(e.getCurrentItem().getType() == Material.NETHER_STAR){
                        e.setCancelled(true);
                        ActiveRaidsMenu activeRaidsMenu = new ActiveRaidsMenu();
                        new ScrollerInventory(activeRaidsMenu.items,LanguageMgr.getLang().getString("ActiveRaids"),player);
                    } else if(e.getCurrentItem().getType() == Material.BELL){
                        e.setCancelled(true);
                        InviteRaidsMenu inviteRaidsMenu = new InviteRaidsMenu(player);
                        new ScrollerInventory(inviteRaidsMenu.items,LanguageMgr.getLang().getString("InviteTownMenu"),player);
                    }
            }
        }
    }
}
