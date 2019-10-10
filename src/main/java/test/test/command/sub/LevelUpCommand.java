package test.test.command.sub;


import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import test.test.api.GuildPlayerAPI;

public class LevelUpCommand {
    private CommandSender sender;
    private GuildPlayerAPI playerAPI;
    public LevelUpCommand(CommandSender sender){
        setSender(sender);
        setPlayerAPI(new GuildPlayerAPI((Player) getSender()));
        Command();
    }
    private void Command(){
        getPlayerAPI().LevelUp();
    }
    public CommandSender getSender() {
        return sender;
    }

    public void setSender(CommandSender sender) {
        this.sender = sender;
    }

    public GuildPlayerAPI getPlayerAPI() {
        return playerAPI;
    }

    public void setPlayerAPI(GuildPlayerAPI playerAPI) {
        this.playerAPI = playerAPI;
    }
}
