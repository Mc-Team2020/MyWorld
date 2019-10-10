package test.test.command.sub;

import org.bukkit.command.CommandSender;

public class SetAnnouncementCommand {
    private CommandSender sender;
    public SetAnnouncementCommand(CommandSender sender){
        setSender(sender);
        Command();
    }

    private void Command() {

    }

    public CommandSender getSender() {
        return sender;
    }

    public void setSender(CommandSender sender) {
        this.sender = sender;
    }
}
