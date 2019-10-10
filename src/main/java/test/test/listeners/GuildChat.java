package test.test.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import test.test.Main;
import test.test.api.GuildPlayerAPI;

public class GuildChat implements Listener {
    private String prefix = Main.getMain().getConfig().getString("Settings.GuildChat.Prefix");
    @EventHandler
    public void chat(AsyncPlayerChatEvent e){
        String mess = e.getMessage();
        if(mess.toLowerCase().startsWith(prefix)){
            GuildPlayerAPI guildPlayerAPI = GuildPlayerAPI.getGuildPlayerAPI();
            guildPlayerAPI.sendGuildChatMessage(mess.replace(prefix,""),e.getPlayer());
            e.setCancelled(true);
        }
    }
}
