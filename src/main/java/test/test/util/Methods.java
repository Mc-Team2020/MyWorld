package test.test.util;

import test.test.Main;
import test.test.data.GuildLevel;
import test.test.data.VitalityManager;

public class Methods {
    public static void load(){
        loadGuildLevel();
        loadVitality();
    }
    private static void loadGuildLevel(){
        for(String key : Main.getMain().getConfig().getConfigurationSection("Settings.GuildLevel.Configure").getKeys(false)){
           GuildUtil.getGuildLevelMap().put(Integer.parseInt(key),new GuildLevel(Main.getMain().getConfig(),key));
        }
    }
    private static void loadVitality(){
        for(String key : Main.getMain().getConfig().getConfigurationSection("Settings.Activity").getKeys(false)){
            GuildUtil.getVitalityManagerMap().put(Integer.parseInt(key),
                    new VitalityManager(Integer.parseInt(key),Main.getMain().getConfig().getInt("Settings.Activity."+key+".Vitality"),"Settings.Activity."+key+".Trigger"));
        }
    }
}
