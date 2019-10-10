package test.test.command.sub;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import test.test.api.GuildPlayerAPI;

public class SetMainCommand {
    private CommandSender sender;
    private String ArgsPlayer;
    private GuildPlayerAPI playerAPI;

    public SetMainCommand(CommandSender sender,String b){
        setSender(sender);
        setArgsPlayer(b);
        setPlayerAPI(new GuildPlayerAPI((Player) getSender()));
        Command();
    }

    private void Command() {
        getPlayerAPI().setGuildMain(getArgsPlayer());
    }

    public CommandSender getSender() {
        return sender;
    }

    public void setSender(CommandSender sender) {
        this.sender = sender;
    }

    public String getArgsPlayer() {
        return ArgsPlayer;
    }

    public void setArgsPlayer(String argsPlayer) {
        ArgsPlayer = argsPlayer;
    }

    public GuildPlayerAPI getPlayerAPI() {
        return playerAPI;
    }

    public void setPlayerAPI(GuildPlayerAPI playerAPI) {
        this.playerAPI = playerAPI;
    }
}
