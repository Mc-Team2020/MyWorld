package test.test.command.sub;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import test.test.api.GuildPlayerAPI;

public class SetPVPCommand {
    private CommandSender sender;
    private boolean aBoolean;
    public SetPVPCommand(CommandSender sender,boolean b){
        setSender(sender);
        setaBoolean(b);

        Command();
    }

    private void Command() {
        new GuildPlayerAPI((Player) getSender()).setPVP(isaBoolean());
    }

    public boolean isaBoolean() {
        return aBoolean;
    }

    public void setaBoolean(boolean aBoolean) {
        this.aBoolean = aBoolean;
    }

    public CommandSender getSender() {
        return sender;
    }

    public void setSender(CommandSender sender) {
        this.sender = sender;
    }
}
