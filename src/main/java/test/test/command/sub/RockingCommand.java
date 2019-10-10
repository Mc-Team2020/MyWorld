package test.test.command.sub;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import test.test.api.GuildPlayerAPI;

public class RockingCommand {
    private CommandSender sender;
    private GuildPlayerAPI playerAPI;

    public RockingCommand(CommandSender sender){
        setSender(sender);
        setPlayerAPI(new GuildPlayerAPI((Player) getSender()));
        Command();
    }

    private void Command() {
        getPlayerAPI().RockQianShuFunction();
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
