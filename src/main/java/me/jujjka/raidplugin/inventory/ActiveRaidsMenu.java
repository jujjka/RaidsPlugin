package me.jujjka.raidplugin.inventory;

import me.jujjka.raidplugin.language.LanguageMgr;
import me.jujjka.raidplugin.modules.Raid;
import me.jujjka.raidplugin.utils.FormaterTime;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ActiveRaidsMenu implements InventoryHolder {

    private Inventory inv;

    public ArrayList<ItemStack> items = new ArrayList<>();

    public ActiveRaidsMenu(){
        init();
    }

    private void init(){
        ItemStack item;

        for(Raid raid : Raid.getRaids()){
            List<String > lore = new ArrayList<>();
            String nameSenderRaid = raid.getSenderRaid().getName();

            //VERY INCONVENIENT WAY OF LOR
            lore.add(LanguageMgr.getLang().getString("ActiveInventory"));
            lore.add(LanguageMgr.getLang().getString("1").formatted(raid.getTargetRaid().getName()));
            lore.add(LanguageMgr.getLang().getString("2"));
            lore.add(LanguageMgr.getLang().getString("3"));
                lore.add("§6 - " + raid.getMembers());
            lore.add(LanguageMgr.getLang().getString("4"));
            if(raid.isPreparation()){
                lore.add(LanguageMgr.getLang().getString("5"));
                lore.add(LanguageMgr.getLang().getString("6"));
                lore.add(LanguageMgr.getLang().getString("7"));
                lore.add("" + FormaterTime.FormatTime(raid));
            } else {
                lore.add(LanguageMgr.getLang().getString("8"));
                lore.add(LanguageMgr.getLang().getString("9"));
                lore.add(LanguageMgr.getLang().getString("10"));
                lore.add("§6" + FormaterTime.FormatTimeRemai(raid));
            }

            lore.add(LanguageMgr.getLang().getString("ActiveInventory"));
            item = createItem("         §6" + nameSenderRaid ,Material.PLAYER_HEAD,lore);

            items.add(item);
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
