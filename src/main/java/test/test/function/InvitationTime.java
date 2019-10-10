package test.test.function;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import test.test.data.InvitationManager;
import test.test.util.GuildUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class InvitationTime extends BukkitRunnable {
    public InvitationTime(){
        Bukkit.getConsoleSender().sendMessage("§f[§bGuild§f]: §aEnable InvitationTime tasks...");
    }
    @Override
    public void run() {
        for(UUID uuid : GuildUtil.getPlayerInvitations().keySet()){
            Player player = Bukkit.getPlayer(uuid);
            if(player == null){
                continue;
            }
            List<InvitationManager> newinvms = new ArrayList<>();
            for(InvitationManager invitationManager1 : GuildUtil.getPlayerInvitations().get(uuid)){
                //当他时间大于0时-1 反之直接消除
                if(invitationManager1.getTime() > 0){
                    invitationManager1.setTime(invitationManager1.getTime() - 1);
                    newinvms.add(invitationManager1);
                }
            }
            GuildUtil.getPlayerInvitations().put(uuid,newinvms);
        }
    }

}
