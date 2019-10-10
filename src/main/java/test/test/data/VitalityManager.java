package test.test.data;


import test.test.Main;

import java.util.ArrayList;
import java.util.List;

public class VitalityManager {
    private int time;
    private int value;
    private List<Trigger> triggers = new ArrayList<>();
    public VitalityManager(int time,int value,String triggerspath){
        setTime(time);
        setValue(value);
        setTriggers(getTrigger(triggerspath));
    }
    public List<Trigger> getTrigger(String path){
        List<Trigger> triggers = new ArrayList<>();
        for(String key : Main.getMain().getConfig().getStringList(path)){
            triggers.add(new Trigger(key));
        }
        return triggers;
    }
    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public List<Trigger> getTriggers() {
        return triggers;
    }

    public void setTriggers(List<Trigger> triggers) {
        this.triggers = triggers;
    }
}
