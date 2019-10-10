package test.test.data;

import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GuildLevel {
    private double Money;
    private int Contribution;
    private Map<Integer,RockingQianShuManager> rockingQianShuManagerMap = new HashMap<>();
    public GuildLevel(FileConfiguration data,String Key){
        setMoney(data.getDouble("Settings.GuildLevel.Configure."+Key+".Money"));
        setContribution(data.getInt("Settings.GuildLevel.Configure."+Key+".Contribution"));
        for(String key : data.getConfigurationSection("Settings.RockingQianShu."+Key).getKeys(false)){
            String Path = "Settings.RockingQianShu."+Key+"."+key+".";
            RockingQianShuManager rockingQianShuManager = new RockingQianShuManager(data.getString(Path+"Money"),FormalTriggerList(data,Path+"Trigger"));
            getRockingQianShuManagerMap().put(Integer.parseInt(key),rockingQianShuManager);
        }
    }
    public List<Trigger> FormalTriggerList(FileConfiguration data,String Path){
        List<Trigger> triggers = new ArrayList<>();
        for(String s : data.getStringList(Path)){
            triggers.add(new Trigger(s));
        }
        return triggers;
    }
    public double getMoney() {
        return Money;
    }

    public void setMoney(double money) {
        Money = money;
    }

    public int getContribution() {
        return Contribution;
    }

    public void setContribution(int contribution) {
        Contribution = contribution;
    }

    public Map<Integer, RockingQianShuManager> getRockingQianShuManagerMap() {
        return rockingQianShuManagerMap;
    }

    public void setRockingQianShuManagerMap(Map<Integer, RockingQianShuManager> rockingQianShuManagerMap) {
        this.rockingQianShuManagerMap = rockingQianShuManagerMap;
    }
}
