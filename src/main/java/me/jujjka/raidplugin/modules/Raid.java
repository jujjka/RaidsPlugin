package me.jujjka.raidplugin.modules;

import com.palmergames.adventure.text.Component;
import com.palmergames.adventure.text.TextComponent;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.Town;
import me.jujjka.raidplugin.Raidplugin;
import me.jujjka.raidplugin.bossbar.BossBarAttackPREPARATION;
import me.jujjka.raidplugin.bossbar.BossBarAttackREMAINING;
import me.jujjka.raidplugin.language.LanguageMgr;
import me.jujjka.raidplugin.utils.FormaterTime;
import me.jujjka.raidplugin.utils.ProtectOnTown;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class Raid {
    public int protectTime = Raidplugin.getInstance().getConfig().getInt("RaidsPlugin.time_Protection");
    private static List<Raid> raids = new ArrayList<>();
    private static List<Town> TownsOnRaid = new ArrayList<>();
    private static List<Town> ProtectTowns = new ArrayList<>();
    private List<Town> members = new ArrayList<>();
    boolean raidbool;
    boolean raidbool1;
    private String name;
    private Player senderRaid;
    private Player targetRaid;
    private Town targetTown;
    private Town senderTown;
    private boolean Preparation;
    private boolean remaining;
    private int PREPARATION;
    private int REMAINING_TIME;
    private BossBarAttackPREPARATION bossBarAttackPREPARATION;
    private BossBarAttackREMAINING bossBarAttackREMAINING;
    public Raid(String name, Player senderRaid, Player targetRaid, Town targetTown,Town senderTown, List members) {
        this.name = name;
        this.senderRaid = senderRaid;
        this.targetRaid = targetRaid;
        this.targetTown = targetTown;
        this.senderTown = senderTown;
        this.PREPARATION = Raidplugin.getInstance().getConfig().getInt("RaidsPlugin.time_Preparation");
        this.REMAINING_TIME = Raidplugin.getInstance().getConfig().getInt("RaidsPlugin.time_Remaining");
        this.members = members;
    }

    public void createRaid(String name, Player senderRaid, Player targetRaid, Town targetTown,Town senderTown,List members){
        Raid raid = new Raid(name,senderRaid,targetRaid,targetTown,senderTown,members);
        raid.startPREPARATION_TIME();
        raids.add(raid);
        getTownsOnRaid().add(targetTown);
        getTownsOnRaid().add(senderTown);
    }
    public void addMembers(Town town){
        members.add(town);
    }


    //START PREPARATION TIME
    public void startPREPARATION_TIME(){

        try {
            setRaidbool(true);

            setBossBarAttackPREPARATION(new BossBarAttackPREPARATION());

            setBarPrep(getBossBarAttackPREPARATION());


            new BukkitRunnable(){

                @Override
                public void run() {
                    if(getPREPARATION() != 0 && isRaidbool()){
                        for(Town town: members){
                            for(Resident resident: town.getResidents()){
                                if(resident.getPlayer().isOnline()) {
                                    getBossBarAttackPREPARATION().addPlayerBar(resident.getPlayer());
                                }
                            }
                        }
                        for(Resident resident: targetTown.getResidents()){
                            if(resident.getPlayer().isOnline()) {
                                getBossBarAttackPREPARATION().addPlayerBar(resident.getPlayer());
                            }
                        }
                        UpdateBarPrep(getBossBarAttackPREPARATION());
                        setPreparation(true);
                        setPREPARATION(getPREPARATION() - 1);
                    }
                    else {
                        setPreparation(false);
                        RemoveBarPrep(getBossBarAttackPREPARATION());
                        this.cancel();
                        if (!isRaidbool()){
                            cancelRaid();
                        } else {
                            startRaid();
                            startREMAINING_TIME();
                        }
                    }

                }
            }.runTaskTimer(Raidplugin.getInstance(),10,20);
        }catch (Exception e){

        }



    }

    //START REMAINING TIME
    public void startREMAINING_TIME(){

        try {
            setBossBarAttackREMAINING(new BossBarAttackREMAINING());
            setBarRem(getBossBarAttackREMAINING());
            new BukkitRunnable(){

                @Override
                public void run() {
                    if(getREMAINING_TIME() != 0 && isRaidbool()){
                        for(Town town: members){
                            for(Resident resident: town.getResidents()){
                                if(resident.getPlayer() != null) {
                                    getBossBarAttackREMAINING().addPlayerBar(resident.getPlayer());
                                }
                            }
                        }
                        for(Resident resident: targetTown.getResidents()){
                            if (resident.getPlayer() != null){
                                getBossBarAttackREMAINING().addPlayerBar(resident.getPlayer());
                            }
                        }
                        UpdateBarRem(getBossBarAttackREMAINING());
                        setRemaining(true);
                        setREMAINING_TIME(getREMAINING_TIME() - 1);
                    } else {
                        setRemaining(false);
                        RemoveBarRem(getBossBarAttackREMAINING());
                        cancelRaid();
                        this.cancel();
                    }

                }
            }.runTaskTimer(Raidplugin.getInstance(),10,20);
        }catch (Exception e){

        }
    }

    public void setProtect(Town town){
        ProtectTowns.add(town);
        new BukkitRunnable(){

            @Override
            public void run() {
                if(protectTime != 0){
                    protectTime = protectTime - 1;
                    ProtectOnTown.setProtect(town,protectTime);
                } else {
                    ProtectOnTown.remove(town);
                    ProtectTowns.remove(town);
                    this.cancel();
                }
            }
        }.runTaskTimer(Raidplugin.getInstance(),0,20);
    }


    //BAR METHODS
    //
    public void setBarPrep(BossBarAttackPREPARATION bossBarAttackPREPARATION){
        bossBarAttackPREPARATION.createBarr(this);
    }
    public void RemoveBarPrep(BossBarAttackPREPARATION bossBarAttackPREPARATION){
        bossBarAttackPREPARATION.removeBarr();
    }

    public void UpdateBarPrep(BossBarAttackPREPARATION bossBarAttackPREPARATION){
        bossBarAttackPREPARATION.cast(this);
    }
    public void setBarRem(BossBarAttackREMAINING bossBarAttackREMAINING){
        bossBarAttackREMAINING.createBarr(this);
    }
    public void RemoveBarRem(BossBarAttackREMAINING bossBarAttackREMAINING){
        bossBarAttackREMAINING.removeBarr();
    }

    public void UpdateBarRem(BossBarAttackREMAINING bossBarAttackREMAINING){
        bossBarAttackREMAINING.cast(this);
    }
    //
    public static void sendRisedentMessage(Town town,String message){
        for(Resident resident : town.getResidents()) {
            TextComponent textComponent = Component.text(message);
            resident.sendMessage(textComponent);
        }
    }
    public static void sendAllMessages(String message){
        for(Player player: Bukkit.getOnlinePlayers()){
            player.sendMessage(message);
        }
    }
    public static Raid getRaidByTown(Town town){
        for (Raid raid: raids){
            if(raid.senderTown == town || raid.targetRaid == town || raid.members.contains(town)){
                return raid;
            }
        }
        return null;
    }
    public boolean getInPlayers(){
        float ratio = (float) Raidplugin.getInstance().getConfig().getDouble("RaidsPlugin.ratio_Players");
        double targetPlayer = getOnlinePlayerForTargetTown();
        double membersPlayer = getOnlinePlayerForMembers();
        double integer = targetPlayer/membersPlayer;
        return integer >= ratio;
    }
    public int finggetInPlayers() {
        double ratio = Raidplugin.getInstance().getConfig().getDouble("RaidsPlugin.ratio_Players");
        int min = 0;
        double targetPlayer = getOnlinePlayerForTargetTown();
        double membersPlayer = getOnlinePlayerForMembers();
        for(double i = targetPlayer ; i / membersPlayer < ratio; i++){
            min++;
        }
        return min;
    }
    public int getOnlinePlayerForMembers(){
        int amount = 0;
        for(Town town: members){
            for(Resident resident: town.getResidents()){
                if(resident.isOnline()){
                    amount++;
                }
            }
        }
        return amount;
    }
    public int getOnlinePlayerForTargetTown(){
        int amount1 = 0;
            for(Resident resident: targetTown.getResidents()){
                    if (resident.isOnline()) {
                        amount1++;
                    }
            }
        return amount1;
    }
    public void startRaid(){

        String startRaid = LanguageMgr.getLang().getString("OpenRaid");
        sendAllMessages(String.format(startRaid,senderTown.getName(),targetTown.getName(), FormaterTime.FormatTimeFromInt(getREMAINING_TIME())));

    }

    //ОТМЕНЯЕТ РЕЙД
    public void cancelRaid(){

        String cancel =LanguageMgr.getLang().getString("CancelRaid");
        sendAllMessages(String.format(cancel, senderTown.getName(),targetTown.getName()));
        setRaidbool(false);
        setRaidbool1(false);
        getTownsOnRaid().remove(targetTown);
        getTownsOnRaid().remove(senderTown);

        setProtect(targetTown);

        getRaids().remove(this);

        for (Town town: members){
            getTownsOnRaid().remove(town);
        }
    }
    public void specifiCancelRaid(){
        setRaidbool(false);
    }

    /**
     * GETTERS AND SETTERS
     */public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Player getSenderRaid() {
        return senderRaid;
    }

    public void setSenderRaid(Player senderRaid) {
        this.senderRaid = senderRaid;
    }

    public Player getTargetRaid() {
        return targetRaid;
    }

    public void setTargetRaid(Player targetRaid) {
        this.targetRaid = targetRaid;
    }

    public boolean isPreparation() {
        return Preparation;
    }

    public void setPreparation(boolean preparation) {
        Preparation = preparation;
    }

    public boolean isRemaining() {
        return remaining;
    }

    public void setRemaining(boolean remaining) {
        this.remaining = remaining;
    }

    public int getPREPARATION() {
        return PREPARATION;
    }

    public void setPREPARATION(int PREPARATION) {
        this.PREPARATION = PREPARATION;
    }

    public int getREMAINING_TIME() {
        return REMAINING_TIME;
    }

    public void setREMAINING_TIME(int REMAINING_TIME) {
        this.REMAINING_TIME = REMAINING_TIME;
    }

    public Town getTargetTown() {
        return targetTown;
    }

    public void setTargetTown(Town targetTown) {
        this.targetTown = targetTown;
    }

    public Town getSenderTown() {
        return senderTown;
    }

    public void setSenderTown(Town senderTown) {
        this.senderTown = senderTown;
    }

    public static List<Raid> getRaids() {
        return raids;
    }

    public void setRaids(List<Raid> raids) {
        Raid.raids = raids;
    }

    public static List<Town> getTownsOnRaid() {
        return TownsOnRaid;
    }


    public List<Town> getMembers() {
        return members;
    }

    public void setMembers(List<Town> members) {
        this.members = members;
    }



    public boolean isRaidbool() {
        return raidbool;
    }


    public void setRaidbool(boolean raidbool) {
        this.raidbool = raidbool;
    }

    public static void setTownsOnRaid(List<Town> townsOnRaid) {
        TownsOnRaid = townsOnRaid;
    }

    public boolean isRaidbool1() {
        return raidbool1;
    }

    public static List<Town> getProtectTowns() {
        return ProtectTowns;
    }

    public static void setProtectTowns(List<Town> protectTowns) {
        ProtectTowns = protectTowns;
    }

    public BossBarAttackPREPARATION getBossBarAttackPREPARATION() {
        return bossBarAttackPREPARATION;
    }

    public void setBossBarAttackPREPARATION(BossBarAttackPREPARATION bossBarAttackPREPARATION) {
        this.bossBarAttackPREPARATION = bossBarAttackPREPARATION;
    }

    public BossBarAttackREMAINING getBossBarAttackREMAINING() {
        return bossBarAttackREMAINING;
    }

    public void setBossBarAttackREMAINING(BossBarAttackREMAINING bossBarAttackREMAINING) {
        this.bossBarAttackREMAINING = bossBarAttackREMAINING;
    }

    public void setRaidbool1(boolean raidbool1) {
        this.raidbool1 = raidbool1;
    }
}
