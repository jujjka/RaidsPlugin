package me.jujjka.raidplugin.inventory;

import com.palmergames.bukkit.towny.TownyUniverse;
import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.Town;
import me.jujjka.raidplugin.Raidplugin;
import me.jujjka.raidplugin.language.LanguageMgr;
import me.jujjka.raidplugin.modules.Raid;
import me.jujjka.raidplugin.utils.UtilPlayer;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class CreateRaidMenu implements InventoryHolder {

    private Inventory inv;

    private final Player player;

    public ArrayList<ItemStack> items = new ArrayList<>();

    public CreateRaidMenu(Player player) throws NotRegisteredException {
        this.player = player;
        init();
    }

    private void init()  {
        ItemStack item;

        for(Town town: TownyUniverse.getInstance().getTowns()){
            //VARIABLES
            TownyUniverse townyUniverse = TownyUniverse.getInstance();
            Resident resident = townyUniverse.getResident(player.getUniqueId());
            String TownName = town.getName();
            String mayorName = town.getMayor().getName();
            Town townPlayer = null;
            try {
                townPlayer = resident.getTown();
            } catch (NotRegisteredException e) {
                throw new RuntimeException(e);
            }
            try {
                List<String > lore = new ArrayList<>();
                //EXAMINATION PLAYER
                if(!Raid.getTownsOnRaid().contains(town)){
                    if(!Raid.getProtectTowns().contains(town)) {
                        double xuesos = UtilPlayer.getOnlinePlayerForMembers(town) / UtilPlayer.getOnlinePlayerForMembers(townPlayer);
                        if (xuesos > Raidplugin.getInstance().getConfig().getDouble("RaidsPlugin.ratio_Players")) {
                            if(!resident.getTown().getName().equals(TownName)) {
                                lore.add(LanguageMgr.getLang().getString("TownElement").formatted(mayorName));
                                lore.add(LanguageMgr.getLang().getString("RaidElement"));
                                item = createItem(LanguageMgr.getLang().getString("Moment").formatted(TownName), Material.PLAYER_HEAD, lore);
                                items.add(item);
                            }

                        }
                    }
                    }

            } catch (NotRegisteredException e) {
                throw new RuntimeException(e);
            }
        }
        }

    private ItemStack createItem(String name, Material mat, List<String > lore){
        ItemStack item = new ItemStack(mat);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    @Override
    public Inventory getInventory() {
        return inv;
    }
}

