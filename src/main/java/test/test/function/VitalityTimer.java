package test.test.function;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import test.test.api.GuildPlayerAPI;
import test.test.data.Trigger;
import test.test.data.VitalityManager;
import test.test.util.GuildUtil;


public class VitalityTimer extends Thread{
    public VitalityTimer(){

        Bukkit.getConsoleSender().sendMessage("§f[§bGuild§f]: §aEnable VitalityTimer thread tasks...");
    }
    @Override
    public void run() {
        while (true){

            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                GuildPlayerAPI playerAPI = new GuildPlayerAPI(onlinePlayer);
                long online = System.currentTimeMillis() - GuildUtil.getOnlinetime().get(onlinePlayer);
                int time = (int) (online / 1000) + (playerAPI.getOfflinePlayerAPI().getOnlineTime());

                for (VitalityManager vitalityManager : GuildUtil.getVitalityManagerMap().values()) {
                    if (time == vitalityManager.getTime()) {
                        playerAPI.getOfflinePlayerAPI().addVitality(vitalityManager.getValue());
                        for (Trigger trigger : vitalityManager.getTriggers()) {
                            trigger.implement(onlinePlayer, 0);
                        }
                    }
                }
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

