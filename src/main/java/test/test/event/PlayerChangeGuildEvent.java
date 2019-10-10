package test.test.event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import test.test.api.GuildOfflinePlayerAPI;

import java.awt.*;

public class PlayerChangeGuildEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
    public static HandlerList getHandlerList() {
        return handlers;
    }

    private String OffLinePlayerName;
    private GuildOfflinePlayerAPI offlinePlayerAPI;

    public PlayerChangeGuildEvent(String offLinePlayerName){
        setOffLinePlayerName(offLinePlayerName);
        setOfflinePlayerAPI(new GuildOfflinePlayerAPI(getOffLinePlayerName()));
    }

    public String getOffLinePlayerName() {
        return OffLinePlayerName;
    }

    public void setOffLinePlayerName(String offLinePlayerName) {
        OffLinePlayerName = offLinePlayerName;
    }

    public GuildOfflinePlayerAPI getOfflinePlayerAPI() {
        return offlinePlayerAPI;
    }

    public void setOfflinePlayerAPI(GuildOfflinePlayerAPI offlinePlayerAPI) {
        this.offlinePlayerAPI = offlinePlayerAPI;
    }
}
