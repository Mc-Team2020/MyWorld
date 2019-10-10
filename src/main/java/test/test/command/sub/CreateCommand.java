package test.test.command.sub;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import test.test.api.ProjectGuildAPI;

public class CreateCommand {
    private CommandSender commandSender;
    private String GuildName;
    public CreateCommand(CommandSender a,String b){
        setCommandSender(a);
        setGuildName(b);
        Command();
    }

    public void Command(){
        new ProjectGuildAPI().CreateGuild(getGuildName(), (Player) getCommandSender());
    }

    public CommandSender getCommandSender() {
        return commandSender;
    }

    public void setCommandSender(CommandSender commandSender) {
        this.commandSender = commandSender;
    }

    public String getGuildName() {
        return GuildName;
    }

    public void setGuildName(String guildName) {
        GuildName = guildName;
    }
}
