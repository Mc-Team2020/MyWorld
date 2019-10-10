package test.test.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import test.test.api.GuildPlayerAPI;
import test.test.util.Message_CN;

public class GuildPVPDamage implements Listener {
    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e){
        if((e.getDamager() instanceof Player)&&(e.getEntity() instanceof Player)){
            Player Damager = (Player) e.getDamager();
            Player Entitys = (Player) e.getEntity();
            GuildPlayerAPI playerAPI = new GuildPlayerAPI(Damager);
            if((!playerAPI.getOfflinePlayerAPI().getGuild().equalsIgnoreCase("-"))&&playerAPI.isPlayertoGuild(Entitys)&&!(playerAPI.getGuildAPI().getPVP())){
                Damager.sendMessage(Message_CN.getMessage("Damage.Damager"));
                e.setCancelled(true);
            }
        }
    }
}
