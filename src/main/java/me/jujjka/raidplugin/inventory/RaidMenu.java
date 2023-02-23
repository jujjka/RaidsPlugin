package me.jujjka.raidplugin.inventory;

import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import me.jujjka.raidplugin.language.LanguageMgr;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class RaidMenu implements InventoryHolder {
    private final Inventory inv;

    public RaidMenu() throws NotRegisteredException {
        inv = Bukkit.createInventory(this::getInventory,9,LanguageMgr.getLang().getString("MenuRaids"));
        init();
    }

    private void init() throws NotRegisteredException {
        ItemStack item;
        ItemStack item1;
        ItemStack item2;
        List<String > lore = new ArrayList<>();

        //CREATE RAID MENU
        lore.add(LanguageMgr.getLang().getString("slot1"));
        item = createItem(LanguageMgr.getLang().getString("MenuCr_raid"),Material.DIAMOND_SWORD,lore);
        List<String > lore1 = new ArrayList<>();
        //ACTIVE RAID MENU
        lore1.add(LanguageMgr.getLang().getString("slot3"));
        item1 = createItem(LanguageMgr.getLang().getString("MenuActRaids"),Material.NETHER_STAR,lore1);
        List<String > lore2 = new ArrayList<>();
        //INVITES RAID MENU
        lore2.add(LanguageMgr.getLang().getString("slot2"));
        item2 = createItem(LanguageMgr.getLang().getString("MenuInvRaids"),Material.BELL,lore2);
        //
        inv.setItem(2,item);
        inv.setItem(4,item1);
        inv.setItem(6,item2);
        //
    }

    private ItemStack createItem(String name, Material mat, List<String > lore){
        ItemStack item = new ItemStack(mat);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    @Override
    public Inventory getInventory() {
        return inv;
    }
}
