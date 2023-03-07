package me.jujjka.raidplugin.bossbar;

import me.jujjka.raidplugin.Raidplugin;
import me.jujjka.raidplugin.language.LanguageMgr;
import me.jujjka.raidplugin.modules.Raid;
import me.jujjka.raidplugin.utils.FormaterTime;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class BossBarAttackREMAINING {

    //REMAINING BAR
    BossBar bossBar;
    public void addPlayerBar(Player player){
        bossBar.addPlayer(player);
    }
    public BossBar getBar(){
        return bossBar;
    }

    public void createBarr(Raid raid){
        String data = FormaterTime.FormatTimeRemai(raid);
        bossBar = Bukkit.createBossBar(format(LanguageMgr.getLang().getString("TimeRemi").formatted(FormaterTime.FormatTimeRemai(raid))),BarColor.BLUE, BarStyle.SOLID, BarFlag.CREATE_FOG);
        bossBar.setVisible(true);
    }
    public void removeBarr(){
        bossBar.removeAll();
    }
    public void removeBarFrom(Player player){
        bossBar.removePlayer(player);
    }

    public void cast(Raid raid){

        new BukkitRunnable(){

            @Override
            public void run() {
                int time = raid.getREMAINING_TIME();
                if(time == 0 || !raid.isRaidbool()){
                    bossBar.removeAll();
                    this.cancel();
                } else {
                    double percentage = time / Raidplugin.getInstance().getConfig().getDouble("RaidsPlugin.time_Remaining");
                    bossBar.setTitle(format(LanguageMgr.getLang().getString("TimeRemi").formatted(FormaterTime.FormatTimeRemai(raid))));
                    bossBar.setProgress(percentage);
                }

            }
        }.runTaskTimer(Raidplugin.getInstance(),10,10);
    }
    private String format(String message){
        return ChatColor.translateAlternateColorCodes('&',message);
    }
}

