package test.test.util;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import test.test.Main;

import java.io.File;
import java.util.List;

public class Message_CN {
    private Player player;
    private static File messageFile;
    private static YamlConfiguration yamlConfiguration;
    public Message_CN(Player player){
        setPlayer(player);
    }
    public Message_CN(){
        saveDefaultFile();
    }
    static{
        messageFile = new File(Main.getMain().getDataFolder(),"Message.yml");
        yamlConfiguration = YamlConfiguration.loadConfiguration(messageFile);
    }
    public static File getMessage(){
        return new File(Main.getMain().getDataFolder(),"Message.yml");
    }
    public void sendMessage(String s,int type){
        switch (type){
            case 1:
                String mess = yamlConfiguration.getString("LevelUp.MoneyNotEnough");
                getPlayer().sendMessage(mess.replace("&","§"));
                break;
            case 2:
                List<String> messlist = yamlConfiguration.getStringList(s);
                messlist.forEach((v) -> getPlayer().sendMessage(v.replace("&","§")));
                break;
        }
    }
    public static String getMessage(String Path){
        return yamlConfiguration.getString(Path).replace("&","§");
    }
    public static List<String> getList(String Path){
        return yamlConfiguration.getStringList(Path);
    }
    public static YamlConfiguration getdata(){
        return YamlConfiguration.loadConfiguration(messageFile);
    }
    public void saveDefaultFile(){
        if(!messageFile.exists()){
            Main.getMain().saveResource(messageFile.getName(),false);

            Bukkit.getConsoleSender().sendMessage("§f[ProjectGuild]: §aMessage_CM §f加载成功！");
        }
    }
    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
