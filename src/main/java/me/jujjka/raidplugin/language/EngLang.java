package me.jujjka.raidplugin.language;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class EngLang {

    private static File file;
    private static FileConfiguration customFile;

    //Finds or generates the custom config file
    public static void setup(){
        file = new File(Bukkit.getServer().getPluginManager().getPlugin("Raidplugin").getDataFolder(), "EngLang");

        if (!file.exists()){
            try{
                file.createNewFile();
            }catch (IOException e){
            }
        }
        customFile = YamlConfiguration.loadConfiguration(file);
    }

    public static FileConfiguration get(){
        return customFile;
    }

    public static void save(){
        try{
            customFile.save(file);
        }catch (IOException e){
            System.out.println("Couldn't save file EngLang");
        }
    }

    public static void setupMessages(){
        setup();
        get().set("TownName", "§6Town [%s]");
        get().set("MenuRaids","§6Raid Menu");
        get().set("MenuActRaids","§6Active Raid Menu");
        get().set("MenuInvRaids","§6Invitation menu");
        get().set("MenuCr_raid","§6Menu of available townies");
        get().set("slot1","§6Start a raid");
        get().set("slot2","§6Invite townies");
        get().set("slot3","§6Active raids");
        get().set("ActiveTownForRaid","§6Available towns for the raid");
        get().set("ActiveRaids","§6Active raids");
        get().set("InviteTownMenu","§6invite Town");
        get().set("ActiveInventory"," --------------------");
        get().set("TownElement","§6Player town - [%s]");
        get().set("InviteElement","§6Invite to raid!");
        get().set("RaidElement","§6By clicking here you will start the raid!");
        get().set("nextPage","§bNext Page");
        get().set("prevPage","§bPrevious Page");
        get().set("prevMenuBro","§bPrevious Menu");
        get().set("1","   §6Raid on the town [%s]");
        get().set("2"," --------------------");
        get().set("3","   §6members: ");
        get().set("4"," --------------------");
        get().set("5","  §6Stage - Preparation:");
        get().set("6"," --------------------");
        get().set("7","      §6Left - ");
        get().set("8","  §6Stage - Raid in progress:");
        get().set("9"," --------------------");
        get().set("10","       §6Remaining -");
        get().set("Hours","§6(Hours) - ");
        get().set("Minutes","§6(Minutes) - ");
        get().set("seconds","§6(Seconds) - ");
        get().set("TimePrep","§6Preparation started,left - %s");
        get().set("TimeRemi","§6Raid started, left - %s");
        get().set("InviteMessages","§6You sent an offer to join the raid");
        get().set("Accept","&2[ACCEPT] |");
        get().set("Decline","§c| [DECLINE]");
        get().set("AcceptMsg","&2You accepted the raid");
        get().set("DeclineMsg","§cYou declined the raid");
        get().set("-MemderRaid","§6You turned down an offer to raid!");
        get().set("StartRaid","§6Town [%s] started a raid on town [%s], %s left before the attack");
        get().set("OpenRaid","§6Town [%s] is attacking town [%s], %s left before the attack");
        get().set("RaidOтMine","§4You can't attack your town!");
        get().set("ErrorInviteTargetTown","§4This town cannot be invited!");
        get().set("ErrorStartRaidTown","§4One of these townies is being raided!");
        get().set("CancelRaid","§6Town [%s] raid on town [%s] ended");
        get().set("+MemderRaid","§6You are now a member of the raid!");
        get().set("MinPlayer","§4The town of [%s] must have [%s] online players to start the raid!");
        get().set("ProtectTown","§4The town [%s] is protected from attack. [%s] left");
        get().set("TownNotExists","§4There is no such town!");
        get().set("OnlyMayor","§4The raid can only be started by the mayor of the town");
        get().set("TownName1", "§6Town ");
        get().set("Moment","§6%s");
        get().set("OnlyResident","§4You are not a resident of the town!");
        get().set("NotArguments","§4Not enough arguments!");
        get().set("NotActiveRaid","§4You have no active raids!");
        get().set("NotRaid","§4You haven't started the raid yet!");
        get().set("NotMayorOrTown","§4You are not the mayor or you do not have a town!");
        get().set("TownDontForce","§4The town did not start the raid!");
        get().set("TownDontOnline","§4One of the townies is not online!");
        get().set("MayorNotOnlile","§4The mayor of the town is offline!");
        get().set("CommandOnlyMayor","§4This command is available only to the mayor!");
        get().set("NotNullTime","§4Time must be greater than 0!");
        get().set("DontOperator","§4You are not an operator!");
        get().set("Error","§4Error!");
        get().set("TownExistsOnRaid","§4This or your town is already on the raid!");
        get().set("TownAlreadyOnRaid","§4This town is already on the raid!");
        get().set("NotOnlinePlayers","§4Error due to lack of players online!");
        get().set("SendInviteMesssage","§6You have sent an offer to join the raid!");
        get().set("InviteMessage","§6You have been invited to join the raid on the Town");
        get().set("AcceptInvite","§6The town [%s] has accepted the application to join the raid");
        get().set("CloneInvite","§4The town of [%s] has rejected the request to join the raid");


        get().set("HelpMessage","§b" +
                "/raid [Town] - starts a town raid \n" +
                "/raid invite [Town] - throws a raid invite \n" +
                "/raid menu - opens the main menu of the plugin \n" +
                "/raid cancel - cancel your raid\n" +
                "/raid protect [Town] [time in seconds] - establishes protection for the town \n" +
                "/raid forcestart [attack Town] [target Town]- also creates a raid \n" +
                "/raid forcecancel [Town that started the raid] - also cancels the raid \n" +
                "/raids - also opens the plugin menu \n");
        get().options().copyDefaults(true);
        save();
    }


    public static void reload(){
        customFile = YamlConfiguration.loadConfiguration(file);
    }

}
