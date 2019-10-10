package test.test.data;


import java.util.List;

public class RockingQianShuManager {
    private String Money;
    private List<Trigger> trigger;
    public RockingQianShuManager(String a,List<Trigger> trigger){
        setMoney(a);
        setTrigger(trigger);
    }
    public String getMoney() {
        return Money;
    }

    public void setMoney(String money) {
        Money = money;
    }

    public List<Trigger> getTrigger() {
        return trigger;
    }

    public void setTrigger(List<Trigger> trigger) {
        this.trigger = trigger;
    }
}
