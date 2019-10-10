package test.test.api;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import test.test.Main;
import test.test.event.PlayerChangeContributionEvent;
import test.test.event.PlayerChangeGuildEvent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class GuildOfflinePlayerAPI{
    /*
    * Name
    * Group
    * Guild
    * OnLineTime
    * Contribution
    * Vitality
    * Prestige
    * RockQianShuSize
    * */

    private String player;

    public GuildOfflinePlayerAPI(String name){
        setPlayer(name);
    }

    public String getGuild(){
        try {
            return (String)getAttribute("guild");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public boolean is(){
        if(getGuild().equalsIgnoreCase("-"))return false;
        return false;
    }

    public void saveDefaultAttribute(){
        setGroup("-");
        setVitality(0);
        setContribution(0);
        setPrestige(0);
    }

    public void setGuild(String GuildName){
        Bukkit.getPluginManager().callEvent(new PlayerChangeGuildEvent(getPlayer()));
        setAttribute("guild",GuildName);
    }

    public void setGroup(String Group){

        setAttribute("groups",Group);
    }

    public void setContribution(int Con){
        int old = getContribution();
        int news = Con;
        Bukkit.getPluginManager().callEvent(new PlayerChangeContributionEvent(news,old, (Player) Bukkit.getOfflinePlayer(getPlayer())));
        setAttribute("Contribution",Con);
    }

    public int getOnlineTime(){
        try {
            return (int) getAttribute("OnlineTime");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void setOnlineTime(int onlineTime){
        setAttribute("OnlineTime",onlineTime);
    }

    public void addOnlineTime(int onlineTime){
        setOnlineTime(getOnlineTime()+onlineTime);
    }

    public void takeOnlineTime(int OnlineTime){
        setOnlineTime(getOnlineTime()-OnlineTime);
    }

    public void takeContribution(int Con){
        setContribution(getRockQianShuSize()-Con);
    }

    public void addContribution(int Con){
        setContribution(getContribution()+Con);
    }

    public void setRockQianShuSize(int value){
        setAttribute("Rock",value);
    }

    public void addRockQianShuSize(int value){
        setRockQianShuSize(getRockQianShuSize()+value);
    }

    public void takeRockQianShuSize(int value){
        setRockQianShuSize(getRockQianShuSize()-value);
    }

    public int getRockQianShuSize(){
        try {
            return (int) getAttribute("Rock");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void setVitality(int p){
        setAttribute("Vitality",p);
    }

    public void addVitality(int p){
        setVitality(getVitality()+p);
    }

    public void takeVitality(int p){
        setVitality(getVitality()-p);
    }

    public int getPrestige(){
        try {
            return (int) getAttribute("Prestige");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public String getPlayerGroup(){
        try {
            return (String)getAttribute("groups");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public void setPrestige(int i){
        setAttribute("Prestige",i);
    }

    public int getVitality(){
        try {
            return (int)getAttribute("Vitality");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getContribution(){

        try {
            return (int)getAttribute("Contribution");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void setAttribute(String Path, Object object){
        new BukkitRunnable() {
            @Override
            public void run() {
                try {
                    Connection connection = Main.getMain().getConnection();
                    PreparedStatement preparedStatement = connection.prepareStatement("UPDATE playerdata SET "+Path+"=? WHERE name=?");
                    preparedStatement.setString(1, String.valueOf(object));
                    preparedStatement.setString(2,getPlayer());
                    preparedStatement.executeLargeUpdate();
                    preparedStatement.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.runTaskAsynchronously(Main.getMain());
    }

    public Object getAttribute(String Path) throws Exception {

        //此时字段是: Prestige 但是这个字段明显存在，后台提示 Illegal operation on empt
        Connection connection = Main.getMain().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM playerdata WHERE name=?");
        preparedStatement.setString(1,getPlayer());
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        Object obj = resultSet.getObject(Path);
        resultSet.close();
        preparedStatement.close();
        return obj;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }
}
