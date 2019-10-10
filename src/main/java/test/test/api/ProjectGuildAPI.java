package test.test.api;

import org.bukkit.entity.Player;
import test.test.Main;
import test.test.data.GuildManager;
import test.test.data.InvitationManager;
import test.test.util.GuildUtil;
import test.test.util.Message_CN;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ProjectGuildAPI {

    public void CreateGuild(String Guild,Player SenderPlayer){
        GuildPlayerAPI playerAPI = new GuildPlayerAPI(SenderPlayer);
        if(!playerAPI.getOfflinePlayerAPI().getGuild().equalsIgnoreCase("-")){
            SenderPlayer.sendMessage(Message_CN.getMessage("GuildCreateExixtx"));
        }else if(isGuild(Guild)){
            SenderPlayer.sendMessage(Message_CN.getMessage("GuildExist"));
        }else {
            new GuildAPIPlus(Guild).Create(SenderPlayer);
        }
    }

    public void JoinGuild(Player InvPlayer,Player PlayerTwo){
        GuildPlayerAPI guildPlayerAPI = new GuildPlayerAPI(InvPlayer);
        GuildPlayerAPI guildPlayerAPI1 = new GuildPlayerAPI(PlayerTwo);
        GuildAPIPlus guildAPIPlus = new GuildAPIPlus(InvPlayer);
        guildAPIPlus.addMemberList(PlayerTwo.getName());
        guildPlayerAPI1.getOfflinePlayerAPI().setGuild(guildPlayerAPI.getOfflinePlayerAPI().getGuild());

        String mess = Message_CN.getMessage("PlayerJoinGuild").replace("<Guild>",guildPlayerAPI.getOfflinePlayerAPI().getGuild());
        String SendMessage = Message_CN.getMessage("PlayerJoinGuildSender").replace("<Player>",PlayerTwo.getName());
        PlayerTwo.sendMessage(mess);
        InvPlayer.sendMessage(SendMessage);
    }
    public void sendGuildTOP(Player player,int page){
        if(page > getListMaxPage()){
            player.sendMessage(Message_CN.getMessage("GuildListCommandInfo.MaxPage"));
            return;
        }
        List<GuildManager> managers = getGuildListPage(page);
        List<String> Mess = Message_CN.getList("GuildListCommandInfo.MessList");
        Mess.forEach((v) -> {
            if (v.toLowerCase().startsWith("${list}")) {
                for (GuildManager manager : managers) {
                    String mess = ReplacePAPI(v, manager);
                    player.sendMessage(mess.replace("&","§"));
                }
            } else {
                v = v.replace("<Page>", String.valueOf(page)).replace("<MaxPage>",String.valueOf(getListMaxPage()));
                player.sendMessage(v.replace("&","§"));
            }
        });
    }

    public void UpDataRockingSize(){
        String sql = "SELECT * FROM playerdata;";
        Connection connection;
        try {

            connection = Main.getMain().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                String name = resultSet.getString("name");
                new GuildOfflinePlayerAPI(name).setRockQianShuSize(3);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void UpDataOnLineTime(){
        String sql = "SELECT * FROM playerdata;";
        Connection connection;
        try {
            connection = Main.getMain().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                String name = resultSet.getString("name");
                new GuildOfflinePlayerAPI(name).setOnlineTime(0);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public String ReplacePAPI(String v, GuildManager manager) {
        return v
                .replace("${list}", "")
                .replace("<GuildName>", manager.getGuildName())
                .replace("<Level>", String.valueOf(manager.getLevel()))
                .replace("<Size>", String.valueOf(manager.getMemberSize()))
                .replace("<Contribution>", String.valueOf(manager.getContribution()))
                .replace("<Player>",manager.getMainPlayer());
    }
    public int getListMaxPage(){
        List<GuildManager> managers = getServerGuildList();
        if(managers.size() < 10){
            return 1;
        }else{
            double size = Double.valueOf(managers.size())/10;
            return (int) Math.ceil(size);
        }
    }
    private List<GuildManager> getServerGuildList(){
        List<GuildManager> managers = new ArrayList<>();
        String sql = "SELECT * FROM guilddata;";
        Connection connection;
        try {
            connection = Main.getMain().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                managers.add(new GuildManager(
                        resultSet.getString("name"),
                        resultSet.getInt("level"),
                        resultSet.getString("mainplayer"),
                        resultSet.getInt("Contribution"),
                        new GuildAPIPlus(resultSet.getString("name")).getMemberList().size()
                ));
            }
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Collections.sort(managers, new Comparator<GuildManager>() {
            @Override
            public int compare(GuildManager o1, GuildManager o2) {
                int i = o2.getLevel() - o1.getLevel();
                return i;
            }
        });
        return managers;
    }
    public List<GuildManager> getGuildListPage(int Page) {
        List<GuildManager> managers = getServerGuildList();
        List<GuildManager> newmanagers = new ArrayList<>();
        int size = 1;
        for (int i = getPage(Page); i < managers.size(); i++) {
            if (size > 10) break;
            GuildManager manager = managers.get(i);
            newmanagers.add(manager);
            size++;
        }
        return newmanagers;
    }

    //1 2 3 4 5 6 7 8 9 10
    public int getPage(int page) {
        if (page == 1) {
            return 0;
        } else {
            return page * 10 - 10;
        }
    }
    public void RefuseInvitation(Player player,String GuildName){
        List<InvitationManager> lists = GuildUtil.getPlayerInvitations().get(player.getUniqueId());
        for (InvitationManager list : lists) {
            if(list.getGuild().equalsIgnoreCase(GuildName)){
                lists.remove(list);
            }
        }
    }
    public GuildPlayerAPI getPlayerAPI(Player player){
        return new GuildPlayerAPI(player);
    }

    public void DeleteGuild(Player SenderPlayer){

        GuildPlayerAPI playerAPI = new GuildPlayerAPI(SenderPlayer);

        playerAPI.deleteGuild();
    }

    public boolean isGuild(String Guild){
        boolean exists = false;
        Connection connection;
        try {
            connection = Main.getMain().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("select * FROM guilddata WHERE name=?");
            preparedStatement.setString(1,Guild);
            ResultSet resultSet = preparedStatement.executeQuery();
            exists = resultSet.next();
            resultSet.close();
            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return exists;
    }
}
