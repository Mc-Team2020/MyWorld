package test.test.command.sub;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import test.test.api.ProjectGuildAPI;

public class GuildListCommand {
    private CommandSender sender;
    private int page;
    public GuildListCommand(CommandSender sender,int page){
        setSender(sender);
        setPage(page);
        Command();
    }

    private void Command() {
        new ProjectGuildAPI().sendGuildTOP((Player) getSender(),getPage());
    }

    public CommandSender getSender() {
        return sender;
    }

    public void setSender(CommandSender sender) {
        this.sender = sender;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}
