package test.test.command.sub;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import test.test.util.GuildAPI;
import test.test.util.Message_CN;

public class OpenInvitationCommand {
    private CommandSender sender;
    private Player OpenPlayer;
    public OpenInvitationCommand(CommandSender sender,Player player){
        setOpenPlayer(player);
        setSender(sender);
        Command();
    }
    public void Command(){
        if(getOpenPlayer() == null){
            new Message_CN((Player) getSender()).sendMessage("PlayerExix",1);
            return;
        }
        new GuildAPI(getOpenPlayer()).openInvitationGui();
    }
    public CommandSender getSender() {
        return sender;
    }

    public void setSender(CommandSender sender) {
        this.sender = sender;
    }

    public Player getOpenPlayer() {
        return OpenPlayer;
    }

    public void setOpenPlayer(Player openPlayer) {
        OpenPlayer = openPlayer;
    }
}
