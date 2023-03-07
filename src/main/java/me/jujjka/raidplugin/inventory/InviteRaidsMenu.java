package me.jujjka.raidplugin.inventory;

import com.palmergames.bukkit.towny.TownyUniverse;
import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.Town;
import me.jujjka.raidplugin.language.LanguageMgr;
import me.jujjka.raidplugin.modules.Raid;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class InviteRaidsMenu implements InventoryHolder {
    private Inventory inv;

    private final Player player;
    public ArrayList<ItemStack> items = new ArrayList<>();

    public InviteRaidsMenu(Player player) throws NotRegisteredException {
        this.player = player;
        init();
    }

    private void init() throws NotRegisteredException {
        ItemStack item;

        for(Town town: TownyUniverse.getInstance().getTowns()){
            //VARIABLES
            TownyUniverse townyUniverse = TownyUniverse.getInstance();
            Resident resident = townyUniverse.getResident(player.getUniqueId());
            String TownName = town.getName();
            String mayorName = town.getMayor().getName();
            //EXAMINATION PLAYERS
            if(resident.hasTown()){
            Town townPlayer = resident.getTown();
            List<String > lore = new ArrayList<>();
            if(!Raid.getTownsOnRaid().contains(town)){
                if(town.getMayor().getPlayer() != null){
                    if(town.getMayor().getPlayer().isOnline()) {
                        if (townPlayer != town) {
                            if(Raid.getRaidByTown(townPlayer) != null){
                                Raid raid = Raid.getRaidByTown(townPlayer);
                                if(!raid.getTargetRaid().equals(town) && !raid.getMembers().contains(town)){
                                    lore.add(LanguageMgr.getLang().getString("TownElement").formatted(mayorName));
                                    lore.add(LanguageMgr.getLang().getString("InviteElement"));
                                    item = createItem(LanguageMgr.getLang().getString("Moment").formatted(TownName), Material.PLAYER_HEAD, lore);
                                    items.add(item);
                                }
                            }


                        }
                    }
                    }
                }

            }
        }
    }

    private ItemStack createItem(String name, Material mat, List<String > lore){
        ItemStack item = new ItemStack(mat);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    @Override
    public Inventory getInventory() {
        return inv;
    }
}
