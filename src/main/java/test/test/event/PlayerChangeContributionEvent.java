package test.test.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import test.test.api.GuildPlayerAPI;

import java.util.List;

public class PlayerChangeContributionEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
    public static HandlerList getHandlerList() {
        return handlers;
    }

    private int newValue;
    private int oldValue;
    private Player player;
    private GuildPlayerAPI playerAPI;
    public PlayerChangeContributionEvent(int newValue,int oldValue,Player player){
        setNewValue(newValue);
        setOldValue(oldValue);
        setPlayer(player);
        setPlayerAPI(new GuildPlayerAPI(getPlayer()));
    }
    public int getNewValue() {
        return newValue;
    }

    public void setNewValue(int newValue) {
        this.newValue = newValue;
    }

    public int getOldValue() {
        return oldValue;
    }

    public void setOldValue(int oldValue) {
        this.oldValue = oldValue;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public GuildPlayerAPI getPlayerAPI() {
        return playerAPI;
    }

    public void setPlayerAPI(GuildPlayerAPI playerAPI) {
        this.playerAPI = playerAPI;
    }
}
