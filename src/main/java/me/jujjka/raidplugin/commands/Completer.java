package me.jujjka.raidplugin.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.List;

public class Completer implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {

        if(command.getName().equalsIgnoreCase("raid") && args.length == 1){
            if(sender.isOp()){
                return List.of(
                        "invite",
                        "menu",
                        "cancel",
                        "protect",
                        "forcestart",
                        "forcecancel"
                );
            } else {
                return List.of(
                        "invite",
                        "menu",
                        "cancel",
                        "protect"
                );
            }

        }
        return null;
    }
}
