package me.jujjka.raidplugin.commands;

import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import me.jujjka.raidplugin.inventory.RaidMenu;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Raids implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player p){
            if(command.getName().equalsIgnoreCase("raids")) {
                RaidMenu raidMenu;
                try {
                    raidMenu = new RaidMenu();
                    p.openInventory(raidMenu.getInventory());
                } catch (NotRegisteredException e) {
                    throw new RuntimeException(e);
                }
            }

        }
        return false;
    }
}
