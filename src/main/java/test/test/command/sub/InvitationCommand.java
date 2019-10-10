package test.test.command.sub;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import test.test.Main;
import test.test.api.GuildPlayerAPI;
import test.test.data.InvitationManager;
import test.test.util.GuildUtil;
import test.test.util.Message_CN;

import java.util.ArrayList;
import java.util.List;

public class InvitationCommand {
    private CommandSender sender;
    private Player InvitationPlayer;
    private Message_CN message_cn;
    private GuildPlayerAPI playerAPI;
    private int time = Main.getMain().getConfig().getInt("Settings.Invitation_InformationTime");

    public InvitationCommand(CommandSender a,Player b){
        setSender(a);
        setMessage_cn(new Message_CN((Player) getSender()));
        setInvitationPlayer(b);
        setPlayerAPI(new GuildPlayerAPI((Player) getSender()));
        Command();
    }

    public void Command(){
        if(getPlayerAPI().getOfflinePlayerAPI().getGuild().equalsIgnoreCase("-")){
            getSender().sendMessage(Message_CN.getMessage("PlayerGuildExix"));
           return;
        }
        if(getInvitationPlayer() == null){
            getSender().sendMessage(Message_CN.getMessage("PlayerExix"));
            return;
        }
        String SenderGuild = playerAPI.getOfflinePlayerAPI().getGuild();
        addPlayerList(getInvitationPlayer(),SenderGuild);
    }
    public void addPlayerList(Player player,String guild){
            //如果玩家的邀请处理列表里存在这个玩家
            //邀请信息列表
            List<InvitationManager> guilds = GuildUtil.getPlayerInvitations().get(player.getUniqueId());
            //如果里面存在这条邀请信息

            if(guilds.contains(guild)){
                getSender().sendMessage(Message_CN.getMessage("MessageExix"));
                return;
            }else{
                guilds.add(new InvitationManager(guild,time, (Player) getSender()));
                GuildUtil.getPlayerInvitations().put(player.getUniqueId(),guilds);
                getSender().sendMessage(Message_CN.getMessage("Invitation.Message"));
                String Message = Message_CN.getMessage("Invitation.Information").replace("<Player>",getSender().getName()).replace("<Guild>",getPlayerAPI().getOfflinePlayerAPI().getGuild());
                getInvitationPlayer().sendMessage(Message);
            }
    }

    public Player getInvitationPlayer() {
        return InvitationPlayer;
    }

    public void setInvitationPlayer(Player invitationPlayer) {
        InvitationPlayer = invitationPlayer;
    }

    public CommandSender getSender() {
        return sender;
    }

    public void setSender(CommandSender sender) {
        this.sender = sender;
    }


    public Message_CN getMessage_cn() {
        return message_cn;
    }

    public void setMessage_cn(Message_CN message_cn) {
        this.message_cn = message_cn;
    }

    public GuildPlayerAPI getPlayerAPI() {
        return playerAPI;
    }

    public void setPlayerAPI(GuildPlayerAPI playerAPI) {
        this.playerAPI = playerAPI;
    }
}
