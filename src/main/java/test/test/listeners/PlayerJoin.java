package test.test.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;
import test.test.Main;
import test.test.util.GuildAPI;
import test.test.util.GuildUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.regex.Pattern;

public class PlayerJoin implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        CreatePlayer(e.getPlayer());
        new GuildAPI(e.getPlayer()).addInvitationMap();

        //数据库存的时间
        long time = System.currentTimeMillis();
        //存map
        GuildUtil.getOnlinetime().put(e.getPlayer(),time);

    }
    private void CreatePlayer(Player player){
        try {
            Connection connection = Main.getMain().getConnection();
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM playerdata WHERE name=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,player.getName());
            ResultSet resultSet = preparedStatement.executeQuery();
            if(!resultSet.next()){
                String CreateSQL = "insert into playerdata values('"+player.getName()+"','-','-','0','0','0','3','0')";
                System.out.println("创建玩家数据: "+player.getName());
                statement.executeUpdate(CreateSQL);
            }
            resultSet.close();
            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
