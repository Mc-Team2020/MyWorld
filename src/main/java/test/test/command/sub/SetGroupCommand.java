package test.test.command.sub;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import test.test.api.GuildPlayerAPI;

public class SetGroupCommand {
    private CommandSender sender;
    private String PlayerName;
    private String Group;
    private GuildPlayerAPI playerAPI;
    public SetGroupCommand(CommandSender sender,String Playername,String Group){
        setGroup(Group);
        setSender(sender);
        setPlayerAPI(new GuildPlayerAPI((Player) getSender()));
        setPlayerName(Playername);
        Command();
    }
    public void Command(){
        getPlayerAPI().setMemberGroup(getPlayerName(),getGroup());
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

    public String getGroup() {
        return Group;
    }

    public void setGroup(String group) {
        Group = group;
    }

    public GuildPlayerAPI getPlayerAPI() {
        return playerAPI;
    }

    public void setPlayerAPI(GuildPlayerAPI playerAPI) {
        this.playerAPI = playerAPI;
    }
}
