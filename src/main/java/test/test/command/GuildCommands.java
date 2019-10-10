package test.test.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import test.test.command.sub.*;
import test.test.util.Message_CN;

public class GuildCommands implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {

        try {
            if(command.getName().equalsIgnoreCase("jlbp")&&args.length == 0){
                new Message_CN((Player) commandSender).sendMessage("Command",2);
                return true;
            }
            switch (args[0]){
                case "create":
                    new CreateCommand(commandSender,args[1]);
                    break;
                case "del":
                    new DeleteCommand(commandSender);
                    break;
                case "yq":
                    new InvitationCommand(commandSender, Bukkit.getPlayer(args[1]));
                    break;
                //command inv Player
                case "inv":
                    if(args.length == 2){
                        new OpenInvitationCommand(commandSender,Bukkit.getPlayer(args[1]));
                    }else{
                        new OpenInvitationCommand(commandSender, (Player) commandSender);
                    }
                    break;
                case "member":
                    new MemberCommand(commandSender);
                    break;
                case "setgroup":
                    new SetGroupCommand(commandSender,args[1],args[2]);
                    break;
                case "kick":
                    new KickCommand(commandSender,args[1]);
                    break;
                case "setmain":
                    new SetMainCommand(commandSender,args[1]);
                    break;
                case "pvp":
                    new SetPVPCommand(commandSender,Boolean.valueOf(args[1]));
                    break;
                case "list":
                    if(args.length == 2){
                        new GuildListCommand(commandSender,Integer.parseInt(args[1]));
                    }else {
                        new GuildListCommand(commandSender,1);
                    }
                    break;
                case "up":
                    new LevelUpCommand(commandSender);
                    break;
                case "rock":
                    new RockingCommand(commandSender);
                    break;
            }
        }catch (Exception e){
            commandSender.sendMessage("指令错误！ 请使用规范的指令.");
        }

        return false;
    }
}
