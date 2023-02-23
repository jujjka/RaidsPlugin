package me.jujjka.raidplugin.events;

import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import me.jujjka.raidplugin.inventory.RaidMenu;
import me.jujjka.raidplugin.inventory.ScrollerInventory;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class ClickPage implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onClick(InventoryClickEvent event) throws NotRegisteredException {
        if(!(event.getWhoClicked() instanceof Player p)) return;
        //Get the current scroller inventory the player is looking at, if the player is looking at one.
        if(!ScrollerInventory.users.containsKey(p.getUniqueId())) return;
        ScrollerInventory inv = ScrollerInventory.users.get(p.getUniqueId());
        if(event.getCurrentItem() == null) return;
        if(event.getCurrentItem().getItemMeta() == null) return;
        if(event.getCurrentItem().getItemMeta().getDisplayName() == null) return;
        //If the pressed item was a nextpage button
        if(event.getCurrentItem().getItemMeta().getDisplayName().equals(ScrollerInventory.nextPageName)){
            event.setCancelled(true);
            //If there is no next page, don't do anything
            if(inv.currpage >= inv.pages.size()-1){
            }else{
                //Next page exists, flip the page
                inv.currpage += 1;
                p.openInventory(inv.pages.get(inv.currpage));
            }
            //if the pressed item was a previous page button
        }else if(event.getCurrentItem().getItemMeta().getDisplayName().equals(ScrollerInventory.previousPageName)){
            event.setCancelled(true);
            //If the page number is more than 0 (So a previous page exists)
            if(inv.currpage > 0){
                //Flip to previous page
                inv.currpage -= 1;
                p.openInventory(inv.pages.get(inv.currpage));
            }
        }
        else if(event.getCurrentItem().getItemMeta().getDisplayName().equals(ScrollerInventory.prevMenuS)){
            event.setCancelled(true);
            RaidMenu raidMenu = new RaidMenu();
                p.openInventory(raidMenu.getInventory());

        }
    }
}
