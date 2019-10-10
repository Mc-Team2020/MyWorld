package test.test.api;

import org.bukkit.entity.Player;
import test.test.Main;
import test.test.util.Message_CN;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;

public class GuildAPIPlus {
    private String GuildName;
    private Player player;

    public GuildAPIPlus(String GuildName){
        setGuildName(GuildName);
    }

    public GuildAPIPlus(Player player){
        setPlayer(player);
        setGuildName(new GuildOfflinePlayerAPI(getPlayer().getName()).getGuild());
    }

    public String getGuildMainPlayer(){ return (String)getAttribute("mainplayer"); }

    public void removeMember(String Name){
        setAttribute("member", getMember().replace(Name+",",""));
    }

    public int getLevel(){return (int)getAttribute("level");}

    public boolean getPVP(){
        boolean b = Boolean.valueOf(String.valueOf(getAttribute("PVP")));
        return b;
    }

    public boolean is(){
        try {
            Connection connection = Main.getMain().getConnection();
            String sql = "SELECT * FROM guilddata WHERE name=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,getGuildName());
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return true;
            }
            resultSet.close();
            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public int getContribution(){return (int)getAttribute("Contribution");}

    public String getAnnouncement(){return (String)getAttribute("Announcement");}

    public String getMember(){return (String)getAttribute("member");}

    public List<String> getMemberList(){
        List<String> list = Arrays.asList(((String) getAttribute("member")).split(","));
        return list;
    }

    public void delete(){
        String sql = "delete from guilddata where Name='" + getGuildName() + "'";
        try {
            for(String member : getMemberList()){
                new GuildOfflinePlayerAPI(member).setGuild("-");
            }
            Connection connection = Main.getMain().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeLargeUpdate();
            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setGuildMainPlayer(String player){setAttribute("mainplayer",player);}

    public void setLevel(int level){setAttribute("level",level);}

    public void setPVP(boolean pvp){
        setAttribute("PVP",pvp);
    }

    public void setContribution(int contribution){setAttribute("Contribution",contribution);}

    public void addContribution(int contribution){
        setAttribute("Contribution",getContribution()+contribution);
    }

    public void takeContribution(int contribution){
        setAttribute("Contribution",getContribution()-contribution);
    }

    public void getAnnouncement(String ann){setAttribute("Announcement",ann);}

    public void addMemberList(String player){
        setAttribute("member",getMember()+player+",");
    }

    public void Create(Player SenderPlayer){
        //实例化
        try {
            //创建一个
            Connection connection = Main.getMain().getConnection();
            String sql = "insert into guilddata (name,mainplayer,level,Contribution,PVP,Money,Announcement,Member) values(?,?,?,?,?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,getGuildName());
            preparedStatement.setString(2,SenderPlayer.getName());
            preparedStatement.setInt(3,1);
            preparedStatement.setInt(4,0);
            preparedStatement.setBoolean(5,false);
            preparedStatement.setInt(6,0);
            preparedStatement.setString(7,"-");
            preparedStatement.setString(8,SenderPlayer.getName()+",");
            preparedStatement.executeLargeUpdate();
            new GuildPlayerAPI(SenderPlayer).getOfflinePlayerAPI().setGuild(getGuildName());
            preparedStatement.close();
            SenderPlayer.sendMessage(Message_CN.getMessage("GuildCreate"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isMain(Player player){
        setPlayer(player);
        return isMain();
    }

    public boolean isMain(){
        if(getGuildMainPlayer().equalsIgnoreCase(getPlayer().getName()))return true;
        return false;
    }

    public int getMoney(){
        return (int) getAttribute("Money");
    }

    public void takeMoney(int money){
        setAttribute("Money",getMoney()-money);
    }

    public void setMoney(int money){
        setAttribute("Money",money);
    }

    public void addMoney(int money){
        setAttribute("Money",(getMoney()+money));
    }

    public Object getAttribute(String Path){

        Object obj = null;
        try {
            Connection connection = Main.getMain().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM guilddata WHERE name=?");
            preparedStatement.setString(1,getGuildName());
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            obj = resultSet.getObject(Path);
            resultSet.close();
            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return obj;
    }

    public void setAttribute(String Path,Object value){

        try {

            Connection connection = Main.getMain().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE guilddata SET "+Path+"=? WHERE name=?");
            preparedStatement.setString(1, String.valueOf(value));
            preparedStatement.setString(2,getGuildName());
            preparedStatement.executeLargeUpdate();
            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public String getGuildName() {
        return GuildName;
    }

    public void setGuildName(String guildName) {
        GuildName = guildName;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
