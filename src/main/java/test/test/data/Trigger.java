package test.test.data;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Trigger {
    private String Vaule;
    private TriggerType triggerType;
    public Trigger(String vaule){
        setVaule(vaule);
        formal(getVaule());
    }
    public Trigger formal(String vaule){
        if(vaule.toLowerCase().startsWith("tell:")){
            setVaule(getVaule().replace("tell:",""));
            setTriggerType(TriggerType.TELL);
        }else if(vaule.toLowerCase().startsWith("tellall:")){
            setVaule(getVaule().replace("tellall:",""));
            setTriggerType(TriggerType.TELLALL);
        }else if(vaule.toLowerCase().startsWith("command:")){
            setVaule(getVaule().replace("command:",""));
            setTriggerType(TriggerType.COMMAND);
        }else if(vaule.toLowerCase().startsWith("op:")){
            setVaule(getVaule().replace("op:",""));
            setTriggerType(TriggerType.OP);
        }
        return null;
    }
    public void implement(Player player,int Money){
        String str = getVaule().replace("<Player>",player.getName()).replace("<Money>",String.valueOf(Money)).replace("&","§");
        if(getTriggerType().equals(TriggerType.COMMAND)){
            player.chat(str);
        }else if(getTriggerType().equals(TriggerType.OP)){
            if(player.isOp()){
                player.chat(str);
            }else{
                player.setOp(true);
                player.chat(str);
                player.setOp(false);
            }
        }else if(getTriggerType().equals(TriggerType.TELL)){
            player.sendMessage(str);
        }else if(getTriggerType().equals(TriggerType.TELLALL)){
            Bukkit.broadcastMessage(str);
        }
    }
    public String getVaule() {
        return Vaule;
    }

    public void setVaule(String vaule) {
        Vaule = vaule;
    }

    public TriggerType getTriggerType() {
        return triggerType;
    }

    public void setTriggerType(TriggerType triggerType) {
        this.triggerType = triggerType;
    }
}
