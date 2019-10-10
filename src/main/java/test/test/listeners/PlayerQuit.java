package test.test.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import test.test.api.GuildOfflinePlayerAPI;
import test.test.util.GuildUtil;

public class PlayerQuit implements Listener {
    @EventHandler
    public void quit(PlayerQuitEvent e){
        long time = System.currentTimeMillis()- GuildUtil.getOnlinetime().get(e.getPlayer());
        //毫秒*1000
        int onlinttime = (int)time/1000;
        //存数据库
        new GuildOfflinePlayerAPI(e.getPlayer().getName()).addOnlineTime(onlinttime);
        //删除
        GuildUtil.getOnlinetime().remove(e.getPlayer());
    }
}
