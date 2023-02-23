package me.jujjka.raidplugin.messages;

import com.palmergames.adventure.text.Component;
import com.palmergames.bukkit.towny.object.Town;
import me.jujjka.raidplugin.Raidplugin;
import me.jujjka.raidplugin.language.LanguageMgr;
import me.jujjka.raidplugin.modules.Raid;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.chat.TextComponentSerializer;
import org.bukkit.entity.Player;

public class MessagerMg extends TextComponentSerializer {


    public static void messageInvite(Town town,Town senderInvite){
        Raid raid = Raid.getRaidByTown(senderInvite);
        if(raid!= null){
            TextComponent component1 = new TextComponent(LanguageMgr.getLang().getString("Accept"));
            TextComponent component2 = new TextComponent(LanguageMgr.getLang().getString("Decline"));
            component1.setColor(ChatColor.GOLD);
            component1.setBold(true);
            component1.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,"/raid accept " + senderInvite.getName()));
            component1.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                    new ComponentBuilder(LanguageMgr.getLang().getString("AcceptMsg")).italic(true).create()));
            component2.setColor(ChatColor.GOLD);
            component2.setBold(true);
            component2.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/raid clone " + senderInvite.getName()));
            component2.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                    new ComponentBuilder(LanguageMgr.getLang().getString("DeclineMsg")).italic(true).create()));
            com.palmergames.adventure.text.TextComponent textComponent = Component.text(Raidplugin.getInstance().getConfig().getString("InviteMessage") + " [" + raid.getTargetTown() + "]");
            town.getMayor().sendMessage(textComponent);
            TextComponent textComponent1 = new TextComponent(component1,component2);
            if(town.getMayor().getPlayer() != null){
                town.getMayor().getPlayer().spigot().sendMessage(textComponent1);
            } else {
                if(town.getMayor().getPlayer() == null){
                    senderInvite.getMayor().getPlayer().sendMessage(Raidplugin.getInstance().getConfig().getString("MayorNotOnlile"));
                }

            }
        } else {
            senderInvite.getMayor().getPlayer().sendMessage(Raidplugin.getInstance().getConfig().getString("NotRaid"));
        }

    }
    public static void messageDelete(Player player){
        TextComponent component1 = new TextComponent();
        component1.setColor(ChatColor.GOLD);
        component1.setBold(true);
        component1.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/"));
        component1.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                new ComponentBuilder().color(ChatColor.GOLD).italic(true).create()));
        player.spigot().sendMessage(component1);
    }
}
