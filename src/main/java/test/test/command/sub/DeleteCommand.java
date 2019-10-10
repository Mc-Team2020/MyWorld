package test.test.command.sub;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import test.test.api.ProjectGuildAPI;

public class DeleteCommand {
    private CommandSender sender;
    public DeleteCommand(CommandSender sender){
        setSender(sender);
        Command();
    }
    private void Command(){
        new ProjectGuildAPI().DeleteGuild((Player) getSender());
    }
    public CommandSender getSender() {
        return sender;
    }

    public void setSender(CommandSender sender) {
        this.sender = sender;
    }
}
