package test.test.command.sub;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import test.test.api.GuildPlayerAPI;

public class KickCommand {
    private CommandSender sender;
    private String PlayerName;
    private GuildPlayerAPI playerAPI;
    public KickCommand(CommandSender sender,String playerName){
        setSender(sender);
        setPlayerAPI(new GuildPlayerAPI((Player) getSender()));
        setPlayerName(playerName);
        Command();
    }
    private void Command(){
        getPlayerAPI().KickGuildMember(getPlayerName());
    }
    public CommandSender getSender() {
        return sender;
    }

    public void setSender(CommandSender sender) {
        this.sender = sender;
    }

    public String getPlayerName() {
        return PlayerName;
    }

    public void setPlayerName(String playerName) {
        PlayerName = playerName;
    }

    public GuildPlayerAPI getPlayerAPI() {
        return playerAPI;
    }

    public void setPlayerAPI(GuildPlayerAPI playerAPI) {
        this.playerAPI = playerAPI;
    }
}
