package me.jujjka.raidplugin.commands;


import com.palmergames.bukkit.towny.TownyUniverse;
import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.Town;
import me.jujjka.raidplugin.Raidplugin;
import me.jujjka.raidplugin.inventory.RaidMenu;
import me.jujjka.raidplugin.language.LanguageMgr;
import me.jujjka.raidplugin.messages.MessagerMg;
import me.jujjka.raidplugin.modules.Raid;
import me.jujjka.raidplugin.utils.FormaterTime;
import me.jujjka.raidplugin.utils.ProtectOnTown;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class CreateRaid implements CommandExecutor {

    int protect_Time;


    public CreateRaid(){
        Raidplugin.getInstance().getCommand("raid").setExecutor(this::onCommand);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        try {
                if (sender instanceof Player p) {
                    if (!(args.length == 0)) {
                        /* CREATE RAID COMMAND */
                        if (!args[0].equals("cancel") && !args[0].equals("invite") && !args[0].equals("accept") && !args[0].equals("clone") && !args[0].equals("menu") && !args[0].equals("protect") && !args[0].equals("forcestart") && !args[0].equals("forcecancel")) {
                            if (!TownyUniverse.getInstance().hasResident(p.getName())) {p.sendMessage(LanguageMgr.getLang().getString("OnlyResident"));return false;}
                            /* Variables */
                            TownyUniverse townyUniverse = TownyUniverse.getInstance();
                            Resident resident = townyUniverse.getResident(p.getUniqueId());
                            String townTargetName = args[0];
                            /* checks errors */
                            if (!resident.getTownOrNull().isMayor(resident)) {p.sendMessage(LanguageMgr.getLang().getString("OnlyMayor"));return false;}
                            if (townyUniverse.getTown(townTargetName) == null) {
                                p.sendMessage(LanguageMgr.getLang().getString("TownNotExists"));
                                return false;
                            }
                            if (Raid.getProtectTowns().contains(townyUniverse.getTown(townTargetName))) {
                                String protect = LanguageMgr.getLang().getString("ProtectTown");
                                sender.sendMessage(String.format(protect, townTargetName, FormaterTime.FormatTimeFromInt(ProtectOnTown.protect.get(townyUniverse.getTown(townTargetName)))));
                                return false;
                            }
                            if (townTargetName.equals(townyUniverse.getTown(townTargetName).getName())) {
                                p.sendMessage(LanguageMgr.getLang().getString("RaidOтMine"));
                                return false;
                            }
                            if (Raid.getTownsOnRaid().contains(townyUniverse.getTown(townTargetName)) || Raid.getTownsOnRaid().contains(resident.getTown())) {
                                p.sendMessage(LanguageMgr.getLang().getString("TownExistsOnRaid"));
                                return false;
                            }
                            /* Variables */
                            Town townTarget = townyUniverse.getTown(townTargetName);
                            Town senderTown = resident.getTown();
                            Player targetPlayer = townTarget.getMayor().getPlayer();
                            List<Town> members = new ArrayList<>();
                            members.add(senderTown);
                            Raid raid = new Raid(townTarget.getName(), p, townTarget.getMayor().getPlayer(), townTarget, senderTown, members);
                            /* Look at the percentage of players */
                            Boolean getIn = raid.getInPlayers();
                            if (!getIn) {
                                String MIN_PLAYER = (LanguageMgr.getLang().getString("MinPlayer"));
                                sender.sendMessage(String.format(MIN_PLAYER, townTarget.getName(), raid.finggetInPlayers()));
                                return false;
                            }
                            raid.setRaidbool(true);
                            raid.createRaid(townTarget.getName(), p, targetPlayer, townTarget, senderTown, members);
                            String startRaid = LanguageMgr.getLang().getString("StartRaid");
                            Raid.sendAllMessages(String.format(startRaid, senderTown.getName(), townTarget.getName(), FormaterTime.FormatTime(raid)));
                            /* SEND HELP MESSAGE */
                            /* CANCEL COMMAND */
                        } else if (args[0].equalsIgnoreCase("cancel")) {
                            try {
                                /* Variables Towny */
                                TownyUniverse townyUniverse = TownyUniverse.getInstance();
                                Resident resident = townyUniverse.getResident(p.getUniqueId());
                                /* Check errors */
                                if (!resident.isMayor()) {
                                    p.sendMessage(LanguageMgr.getLang().getString("OnlyMayor"));
                                    return false;
                                }
                                if (!Raid.getTownsOnRaid().contains(resident.getTown())) {
                                    p.sendMessage(LanguageMgr.getLang().getString("NotActiveRaid"));
                                    return false;
                                }
                                /* Cancel Raid */
                                Town town = resident.getTown();
                                Raid raid = Raid.getRaidByTown(town);
                                raid.specifiCancelRaid();

                            } catch (RuntimeException | NotRegisteredException e) {
                                throw new RuntimeException(e);
                            }
                            /* INVITE COMMAND */
                        } else if (args[0].equalsIgnoreCase("invite")) {
                            try {
                                /* Variables Towny */
                                TownyUniverse townyUniverse = TownyUniverse.getInstance();
                                Resident resident = townyUniverse.getResident(p.getUniqueId());
                                String inviteTownName = args[1];
                                /* Check errors */
                                if (townyUniverse.getTown(inviteTownName) == null) {
                                    p.sendMessage(LanguageMgr.getLang().getString("TownNotExists"));
                                    return false;
                                }
                                if (!resident.isMayor()) {
                                    p.sendMessage(LanguageMgr.getLang().getString("NotMayorOrTown"));
                                    return false;
                                }
                                if (!inviteTownName.equals(resident.getTown().getName()) && !townyUniverse.getTown(inviteTownName).getMayor().isOnline()) {
                                    p.sendMessage(LanguageMgr.getLang().getString("ErrorInviteTargetTown"));
                                    return false;
                                }
                                Raid raid = Raid.getRaidByTown(resident.getTown());
                                if (raid != null) {
                                    p.sendMessage(LanguageMgr.getLang().getString("NotRaid"));
                                }
                                if (townyUniverse.getTown(inviteTownName).equals(raid.getTargetTown())) {
                                    p.sendMessage(LanguageMgr.getLang().getString("ErrorInviteTargetTown"));
                                    return false;
                                }
                                /* Send Invite Message */
                                MessagerMg.messageInvite(townyUniverse.getTown(inviteTownName), resident.getTown());
                                p.sendMessage(LanguageMgr.getLang().getString("SendInviteMesssage"));
                            } catch (NotRegisteredException e) {
                                throw new RuntimeException(e);
                            }

                            /* These two commands are only used for invite messages. */
                        } else if (args[0].equalsIgnoreCase("accept")) {
                            String townName = args[1];
                            TownyUniverse townyUniverse = TownyUniverse.getInstance();
                            Resident resident = townyUniverse.getResident(p.getUniqueId());
                            Town inviteTown = townyUniverse.getTown(townName);
                            Raid raid = Raid.getRaidByTown(inviteTown);
                            try {
                                raid.addMembers(resident.getTown());
                                String accept = LanguageMgr.getLang().getString("AcceptInvite");
                                p.sendMessage(LanguageMgr.getLang().getString("+MemderRaid"));
                                inviteTown.getMayor().getPlayer().sendMessage(String.format(accept, inviteTown.getName()));
                            } catch (NotRegisteredException e) {
                                throw new RuntimeException(e);
                            }
                            /* CLONE COMMAND */
                        } else if (args[0].equalsIgnoreCase("clone")) {
                            String townName = args[1];
                            TownyUniverse townyUniverse = TownyUniverse.getInstance();
                            Resident resident = townyUniverse.getResident(p.getUniqueId());
                            Town inviteTown = townyUniverse.getTown(townName);
                            Raid raid = Raid.getRaidByTown(inviteTown);
                            String clone = LanguageMgr.getLang().getString("CloneInvite");
                            p.sendMessage(LanguageMgr.getLang().getString("-MemderRaid"));
                            inviteTown.getMayor().getPlayer().sendMessage(String.format(clone, inviteTown.getName()));
                            return false;
                            /* MENU COMMAND */
                        } else if (args[0].equalsIgnoreCase("menu")) {
                            try {
                                if (!TownyUniverse.getInstance().hasResident(p.getName())) {
                                    p.sendMessage(LanguageMgr.getLang().getString("OnlyResident"));
                                    return false;
                                }
                                /* Towny variables */
                                TownyUniverse townyUniverse = TownyUniverse.getInstance();
                                Resident resident = townyUniverse.getResident(p.getUniqueId());
                                if (!resident.isMayor()) {
                                    p.sendMessage(LanguageMgr.getLang().getString("CommandOnlyMayor"));
                                    return false;
                                }
                                /* Open menu */
                                RaidMenu raidMenu = new RaidMenu();
                                p.openInventory(raidMenu.getInventory());
                            } catch (NotRegisteredException e) {
                                throw new RuntimeException(e);
                            }
                            /* PROTECT  COMMAND */
                        } else if (args[0].equalsIgnoreCase("protect")) {
                            if (!p.isOp()) {
                                p.sendMessage(LanguageMgr.getLang().getString("DontOperator"));
                                return false;
                            }
                            String targetTownName = args[1];
                            protect_Time = Integer.parseInt(args[2]);
                            TownyUniverse townyUniverse = TownyUniverse.getInstance();
                            if (townyUniverse.getTown(targetTownName) == null) {
                                p.sendMessage(LanguageMgr.getLang().getString("TownNotExists"));
                                return false;
                            }
                            Town town = townyUniverse.getTown(targetTownName);
                            if (protect_Time <= 0) {
                                p.sendMessage(LanguageMgr.getLang().getString("NotNullTime"));
                                return false;
                            }
                            Raid.getProtectTowns().add(town);
                            /* Bukkit task */
                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    if (protect_Time != 0) {
                                        protect_Time--;
                                        ProtectOnTown.setProtect(town, protect_Time);
                                    } else {
                                        Raid.getProtectTowns().remove(town);
                                        this.cancel();
                                    }
                                }
                            }.runTaskTimer(Raidplugin.getInstance(), 0, 20);
                            /* FORSESTART COMMAND */
                        } else if (args[0].equalsIgnoreCase("forcestart")) {
                            if (args.length < 3) {p.sendMessage(LanguageMgr.getLang().getString("NotArguments"));return false;}
                            if (!p.isOp()) {p.sendMessage(LanguageMgr.getLang().getString("DontOperator"));return false;}
                            TownyUniverse townyUniverse = TownyUniverse.getInstance();
                            String senderTownName = args[1];
                            String targetTownName = args[2];
                            if (townyUniverse.getTown(senderTownName) == null && townyUniverse.getTown(targetTownName) == null) {p.sendMessage(LanguageMgr.getLang().getString("TownNotExists"));return false;}
                            if (Raid.getRaidByTown(townyUniverse.getTown(senderTownName)) != null && Raid.getRaidByTown(townyUniverse.getTown(targetTownName)) != null) {p.sendMessage(LanguageMgr.getLang().getString("ErrorStartRaidTown"));return false;}
                            if (!townyUniverse.getTown(senderTownName).getMayor().isOnline() && !townyUniverse.getTown(targetTownName).getMayor().isOnline()){p.sendMessage(LanguageMgr.getLang().getString("TownDontOnline"));}
                            Town senderTown = townyUniverse.getTown(senderTownName);
                            Town targetTown = townyUniverse.getTown(targetTownName);
                            List<Town> members = new ArrayList<>();
                            members.add(senderTown);
                            Raid raid = new Raid(targetTown.getName(), senderTown.getMayor().getPlayer(), targetTown.getMayor().getPlayer(), targetTown, senderTown, members);
                            raid.createRaid(targetTown.getName(), senderTown.getMayor().getPlayer(), targetTown.getMayor().getPlayer(), targetTown, senderTown, members);
                            String startRaid = Raidplugin.getInstance().getConfig().getString("StartRaid");
                            Raid.sendAllMessages(String.format(startRaid, senderTown.getName(), targetTown.getName()));
                            /* FORSECANCEL COMMAND */
                        } else if (args[0].equalsIgnoreCase("forcecancel")) {
                            if (args.length < 2) {p.sendMessage(LanguageMgr.getLang().getString("NotArguments"));return false;}
                            if (!p.isOp()) {
                                p.sendMessage(LanguageMgr.getLang().getString("DontOperator"));
                                return false;
                            }
                            TownyUniverse townyUniverse = TownyUniverse.getInstance();
                            String senderTownName = args[1];
                            if (townyUniverse.getTown(senderTownName) == null) {
                                p.sendMessage(LanguageMgr.getLang().getString("TownNotExists"));
                                return false;
                            }
                            if (Raid.getRaidByTown(townyUniverse.getTown(senderTownName)) == null) {
                                p.sendMessage(LanguageMgr.getLang().getString("TownDontForce"));
                                return false;
                            }
                            Town senderTown = townyUniverse.getTown(senderTownName);
                            Raid raid = Raid.getRaidByTown(senderTown);
                            raid.specifiCancelRaid();
                        }
                        return false;
                    } else {
                        p.sendMessage(LanguageMgr.getLang().getString("HelpMessage"));
                        return false;}
                } else {
                    sender.sendMessage("Only players can use this command.");
                }
        } catch (RuntimeException | NotRegisteredException e) {
            sender.sendMessage(LanguageMgr.getLang().getString("Error"));
        }
        return true;
    }
}