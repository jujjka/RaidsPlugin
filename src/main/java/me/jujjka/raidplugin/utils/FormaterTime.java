package me.jujjka.raidplugin.utils;

import me.jujjka.raidplugin.language.LanguageMgr;
import me.jujjka.raidplugin.modules.Raid;

public class FormaterTime {


    public int DAY;
    public int HOUR;

    public int Minute;
    public int SECOND;


    public static String FormatTime(Raid raid) {
                    int time = raid.getPREPARATION();
                    int hour = time / 3600;
                    int min = time/60;
                    int sec = time%60;
                    if(hour != 0 && min != 0 && sec != 0){
                        return LanguageMgr.getLang().getString("Hours") + hour + LanguageMgr.getLang().getString("Minutes") + min + LanguageMgr.getLang().getString("seconds") + sec;
                    }
                    else if(hour == 0 && min != 0 && sec != 0){
                        return  LanguageMgr.getLang().getString("Minutes")  + min + LanguageMgr.getLang().getString("seconds") + sec;
                    }
                    else if(hour == 0 && min == 0 && sec != 0){
                        return LanguageMgr.getLang().getString("seconds") + sec;
        }
        return LanguageMgr.getLang().getString("seconds") + sec;
    }
    public static String FormatTimeRemai(Raid raid) {
        int time = raid.getREMAINING_TIME();
        int hour = time / 3600;
        int min = time/60;
        int sec = time%60;
        if(hour != 0 && min != 0 && sec != 0){
            return LanguageMgr.getLang().getString("Hours") + hour +  LanguageMgr.getLang().getString("Minutes") + min + LanguageMgr.getLang().getString("seconds") + sec;
        }
        else if(hour == 0 && min != 0 && sec != 0){
            return  LanguageMgr.getLang().getString("Minutes")  + min + LanguageMgr.getLang().getString("seconds") + sec;
        }
        else if(hour == 0 && min == 0 && sec != 0){
            return LanguageMgr.getLang().getString("seconds") + sec;
        }
        return LanguageMgr.getLang().getString("seconds") + sec;
    }
    public static String FormatTimeFromInt(int time) {
        int hour = time / 3600;
        int min = time/60;
        int sec = time%60;
        if(hour != 0 && min != 0 && sec != 0){
            return LanguageMgr.getLang().getString("Hours") + hour + LanguageMgr.getLang().getString("Minutes") + min + LanguageMgr.getLang().getString("seconds") + sec;
        }
        else if(hour == 0 && min != 0 && sec != 0){
            return  LanguageMgr.getLang().getString("Minutes") + min + LanguageMgr.getLang().getString("seconds") + sec;
        }
        else if(hour == 0 && min == 0 && sec != 0){
            return LanguageMgr.getLang().getString("seconds") + sec;
        }
        return LanguageMgr.getLang().getString("seconds") + sec;
    }


    public static String FormatTimeProtect(Raid raid) {
        int time = raid.protectTime;
        int hour = time / 3600;
        int min = time/60;
        int sec = time%60;
        if(hour != 0 && min != 0 && sec != 0){
            return LanguageMgr.getLang().getString("Hours") + hour + LanguageMgr.getLang().getString("Minutes") + min + LanguageMgr.getLang().getString("seconds") + sec;
        }
        else if(hour == 0 && min != 0 && sec != 0){
            return  LanguageMgr.getLang().getString("Minutes") + min + LanguageMgr.getLang().getString("seconds") + sec;
        }
        else if(hour == 0 && min == 0 && sec != 0){
            return LanguageMgr.getLang().getString("seconds") + sec;
        }
        return LanguageMgr.getLang().getString("seconds") + sec;
    }


    public int getDAY() {
        return DAY;
    }

    public void setDAY(int DAY) {
        this.DAY = DAY;
    }

    public int getHOUR() {
        return HOUR;
    }

    public void setHOUR(int HOUR) {
        this.HOUR = HOUR;
    }

    public int getMinute() {
        return Minute;
    }

    public void setMinute(int minute) {
        Minute = minute;
    }

    public int getSECOND() {
        return SECOND;
    }

    public void setSECOND(int SECOND) {
        this.SECOND = SECOND;
    }
}


