package test.test.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import test.test.event.PlayerChangeContributionEvent;

public class PlayerContributionChange implements Listener {
    @EventHandler
    public void change(PlayerChangeContributionEvent e){
        if(e.getNewValue() > e.getOldValue()){
            int value = e.getNewValue()-e.getOldValue();
            e.getPlayerAPI().getGuildAPI().addContribution(value);
        }
    }
}
