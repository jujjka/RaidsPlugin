package me.jujjka.raidplugin.language;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class RuLang {

    private static File file;
    private static FileConfiguration customFile;

    //Finds or generates the custom config file
    public static void setup(){
        file = new File(Bukkit.getServer().getPluginManager().getPlugin("Raidplugin").getDataFolder(), "RuLang");

        if (!file.exists()){
            try{
                file.createNewFile();
            }catch (IOException e){
                //owww
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
            System.out.println("Couldn't save file RuLang");
        }
    }

    public static void setupMessages(){
        setup();
        //INVENTORY
        get().set("MenuActRaids","§6Меню активных рейдов");
        get().set("MenuInvRaids","§6Меню приглашений");
        get().set("MenuCr_raid","§6Меню создания рейдов");
        get().set("slot1","§6Начните рейд");
        get().set("slot2","§6Пригласите города");
        get().set("slot3","§6Активные рейды");
        get().set("TownName", "§6Город [%s]");
        get().set("MenuRaids","§6Меню рейдов");
        get().set("ActiveTownForRaid","§6Доступные города для рейда");
        get().set("ActiveRaids","§6Активные рейды");
        get().set("InviteTownMenu","§6Пригласить город");
        get().set("ActiveInventory"," --------------------");
        get().set("TownElement","§6Город игрока - [%s]");
        get().set("InviteElement","§6Пригласите на рейд!");
        get().set("RaidElement","§6Нажав сюда вы начнете рейд!");
        get().set("nextPage","§bСледующий страница");
        get().set("prevPage","§bПредыдущая страница");
        get().set("prevMenuBro","§bВернуться в меню");
        get().set("1","   §6Рейд на Город [%s]");
        get().set("2"," --------------------");
        get().set("3","   §6Участники: ");
        get().set("4"," --------------------");
        get().set("5","  §6Стадия - Подготовка:");
        get().set("6"," --------------------");
        get().set("7","      §6Осталось - ");
        get().set("8","  §6Стадия - Идет рейд:");
        get().set("9"," --------------------");
        get().set("10","       §6Осталось - ");
        //Time
        get().set("Hours","(Часов) - ");
        get().set("Minutes","(Минут) - ");
        get().set("seconds","(Секунд) - ");
        //Messages
        get().set("Accept","&2[ПРИНЯТЬ] |");
        get().set("Decline","§c| [ОТКЛОНИТЬ]");
        get().set("TownName1", "§6Город ");
        get().set("AcceptMsg","&2Вы примите заявку");
        get().set("DeclineMsg","§cВы отклоните заявку");
        get().set("TimePrep","&2Началась подготовка, осталось - [%s]");
        get().set("TimeRemi","&2Начался рейд, осталось -  [%s]");
        get().set("InviteMessages","§6Вы отправили предложение на вступление в рейд");
        get().set("-MemderRaid","§6Вы отклонили предложение на рейд!");
        get().set("StartRaid","§6Город [%s] начал рейд на город [%s], до нападения осталось - %s");
        get().set("OpenRaid","§6Город [%s] атакует город [%s], до нападения осталось - %s");
        get().set("RaidOтMine","§4На свой город нельзя напасть!");
        get().set("ErrorInviteTargetTown","§4Этот город нельзя пригласить!");
        get().set("ErrorStartRaidTown","§4У одного из этих городов идет!");
        get().set("CancelRaid","§6Рейд Города [%s] на город [%s] окончен");
        get().set("+MemderRaid","§6Теперь вы участник рейда!");
        get().set("MinPlayer","§4В городе [%s] должно быть [%s] игроков в сети для начала рейда!");
        get().set("ProtectTown","§4У города [%s] защита от нападения. Осталось [%s]");
        get().set("TownNotExists","§4Такого города нету!");
        get().set("OnlyMayor","§4Рейд может начать только мэр города");
        get().set("OnlyResident","§4Вы не житель города!");
        get().set("NotArguments","§4Недостаточно аргументов!");
        get().set("NotActiveRaid","§4У вас нет активных рейдов!");
        get().set("NotRaid","§4Вы еще не начали рейд!");
        get().set("NotMayorOrTown","§4Вы не мэр либо у вас нету города!");
        get().set("MayorNotOnlile","§4Мэр города не в сети!");
        get().set("CommandOnlyMayor","§4Эта команда доступна только мэру!");
        get().set("NotNullTime","§4Время должно быть больше 0!");
        get().set("DontOperator","§4Вы не оператор!");
        get().set("Error","§4Ошибка!");
        get().set("TownExistsOnRaid","§4Этот либо ваш город уже на рейде!");
        get().set("TownAlreadyOnRaid","§4Этот город уже на рейде!");
        get().set("NotOnlinePlayers","§4Ошибка из за отсутствия игроков в сети!");
        get().set("SendInviteMesssage","§6Вы отправили предложение на вступление в рейд!");
        get().set("InviteMessage","§6Вас пригласили вступить в рейд на Город");
        get().set("AcceptInvite","§6Город [%s] принял заявку о вступлении на рейд");
        get().set("CloneInvite","§4Город [%s] отклонил заявку о вступлении на рейд");


        get().set("HelpMessage","§b" +
                "/raid [город] - начинает Рейд на город \n" +
                "/raid invite [город] - кидает приглашение на рейд \n" +
                "/raid menu - открывает основное меню плагина \n" +
                "/raid cancel - отменяет рейд \n" +
                "/raid protect [город] [время в сек] - устанавливает защиту на город \n" +
                "/raid forcestart [Атакующий город] [Таргет город]- так же создает рейд \n" +
                "/raid forcecancel [город который начал рейд] - так же отменяет рейд\n" +
                "/raids - так же открывает меню плагина \n");
        get().options().copyDefaults(true);
        save();
    }


    public static void reload(){
        customFile = YamlConfiguration.loadConfiguration(file);
    }

}
