package test.test;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import test.test.command.GuildCommands;
import test.test.function.InvitationGUI;
import test.test.function.InvitationTime;
import test.test.function.TimerManager;
import test.test.function.VitalityTimer;
import test.test.listeners.*;
import test.test.util.DataSource;
import test.test.util.GuildUtil;
import test.test.util.Methods;
import test.test.util.VaultAPI;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

public class Main extends JavaPlugin {
    private static Main main;
    private DataSource dataSource;
    private VitalityTimer vitalityTimer;
    private Connection connection;

    /*
    * 本地转MYSQL全部完成 明天开始完善小功能~
    *
    * */

    @Override
    public void onLoad() {
    }
    @Override
    public void onEnable() {
        main = this;
        saveDefaultConfig();
//        new Message_CN();
        //连接数据库
        setDataSource(new DataSource(this));
        try {
            setConnection(getDataSource().getHikariDataSource().getConnection());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Bukkit.getPluginManager().registerEvents(new PlayerJoin(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerQuit(),this);
        Bukkit.getPluginManager().registerEvents(new GuildChat(), this);
        Bukkit.getPluginManager().registerEvents(new InvitationListGuiClick(), this);
        Bukkit.getPluginManager().registerEvents(new ChooseGUIClick(), this);
        Bukkit.getPluginManager().registerEvents(new GuildPVPDamage(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerGuildChange(),this);
        Bukkit.getPluginManager().registerEvents(new PlayerContributionChange(),this);
        getCommand("jlbp").setExecutor(new GuildCommands());
        VaultAPI.setupEconomy();
        CreateMYSQLTable();
        if (!getlogsFile().exists()) {
            try {
                getlogsFile().createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        vitalityTimer = new VitalityTimer();
        vitalityTimer.start();
        new TimerManager();

        new InvitationTime().runTaskTimerAsynchronously(this, 0, 20);
        new InvitationGUI().runTaskTimerAsynchronously(this, 0, 20);
        GuildUtil.addDefaultPlayer();
        Methods.load();
        run();
    }
    public void CreateMYSQLTable() {
        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = connection.getMetaData().getTables(null,null,"guilddata",null);
            if(!resultSet.next()){
                String GuildData = "create table guilddata (" +
                        "name varchar(50) not null," +
                        "mainplayer varchar(50) not null," +
                        "level int(10) not null," +
                        "Contribution int(10) not null," +
                        "PVP varchar(5) not null," +
                        "Money int(100) not null,"+
                        "Announcement varchar(50) not null," +
                        "Member varchar(100) not null)";
                statement.executeUpdate(GuildData);
                Bukkit.getConsoleSender().sendMessage("§f[§bGuild§f]: §aCreateing Table GuildData...");
            }
            ResultSet resultSet1 = connection.getMetaData().getTables(null,null,"playerdata",null);
            if(!resultSet1.next()){
                String PlayerData = "create table playerdata(" +
                        "name varchar(10) not null," +
                        "guild varchar(10) not null,"+
                        "groups varchar(10) not null," +
                        "Vitality int(30) not null," +
                        "Contribution int(10) not null," +
                        "Prestige int(30) not null," +
                        "Rock int(10) not null," +
                        "OnlineTime int(30) not null)";
                statement.executeUpdate(PlayerData);

                Bukkit.getConsoleSender().sendMessage("§f[§bGuild§f]: §aCreateing Table PlayerData...");
            }
            resultSet.close();
            resultSet1.close();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public Connection getConnection(){
        if(connection == null){
            try {
                connection = getDataSource().getHikariDataSource().getConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }
    public static Main getMain() {
        return main;
    }

    public void run() {
        new BukkitRunnable() {
            @Override
            public void run() {
                for (UUID uuid : GuildUtil.getPlayerInvitationGUIs().keySet()) {
                    if (!Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(uuid))) {
                        GuildUtil.getPlayerInvitations().remove(uuid);
                    }
                }
            }
        }.runTaskTimerAsynchronously(this, 0, 5);
    }

    private File getlogsFile(){
        File file = new File(getDataFolder(),"logs.yml");
        return file;
    }

    public void sendFile(String name,String mess){
        File file = getlogsFile();
        YamlConfiguration data = YamlConfiguration.loadConfiguration(file);
        Date now = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        data.set(simpleDateFormat.format(now)+".name",name);
        data.set(simpleDateFormat.format(now)+".logs",mess);
        data.set(simpleDateFormat.format(now)+".size","");
        try {
            data.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDisable() {
        GuildUtil.UpdataOnLineMap();
        vitalityTimer.stop();
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Bukkit.getConsoleSender().sendMessage("§f[§bGuild§f]: §aDisable VitalityTimer thread tasks...");
    }
    private DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
