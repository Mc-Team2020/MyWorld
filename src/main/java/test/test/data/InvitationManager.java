package test.test.data;

import org.bukkit.entity.Player;

public class InvitationManager {
    private String Guild;
    private int time;
    private Player player;
    public InvitationManager(String a,int b,Player player){
        setGuild(a);
        setTime(b);
        setPlayer(player);
    }
    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getGuild() {
        return Guild;
    }

    public void setGuild(String guild) {
        Guild = guild;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
