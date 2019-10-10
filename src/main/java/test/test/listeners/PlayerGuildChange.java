package test.test.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import test.test.api.GuildOfflinePlayerAPI;
import test.test.event.PlayerChangeGuildEvent;

public class PlayerGuildChange implements Listener {
    @EventHandler
    public void onchange(PlayerChangeGuildEvent e){
        GuildOfflinePlayerAPI offlinePlayerAPI = new GuildOfflinePlayerAPI(e.getOffLinePlayerName());
        offlinePlayerAPI.saveDefaultAttribute();
    }
}
