package test.test.util;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import test.test.api.GuildOfflinePlayerAPI;
import test.test.data.GuildLevel;
import test.test.data.InvitationManager;
import test.test.data.VitalityManager;

import java.util.*;

public class GuildUtil {

    private static Map<UUID, List<InvitationManager>> PlayerInvitations = new HashMap<>();
    private static Map<Integer, GuildLevel> guildLevelMap = new HashMap<>();
    private static Map<UUID, Inventory> PlayerInvitationGUIs = new HashMap<>();
    private static Map<Integer, VitalityManager> vitalityManagerMap = new HashMap<>();

    private static Map<Player, Long> onlinetime = new HashMap<>();

    public static Map<UUID, List<InvitationManager>> getPlayerInvitations() {
        return PlayerInvitations;
    }

    public static void setPlayerInvitations(Map<UUID, List<InvitationManager>> playerInvitations) {
        PlayerInvitations = playerInvitations;
    }
    public static void UpdataOnLineMap(){
        getOnlinetime().forEach((k,v) -> {
            long time = System.currentTimeMillis()-GuildUtil.getOnlinetime().get(k);
            //毫秒*1000
            int onlinttime = (int)time/1000;
            //存数据库
            new GuildOfflinePlayerAPI(k.getName()).addOnlineTime(onlinttime);
        });
    }
    public static void addDefaultPlayer(){
        for(Player player : Bukkit.getOnlinePlayers()){
            getPlayerInvitations().put(player.getUniqueId(),new ArrayList<>());
        }
    }

    public static Map<UUID, Inventory> getPlayerInvitationGUIs() {
        return PlayerInvitationGUIs;
    }

    public static void setPlayerInvitationGUIs(Map<UUID, Inventory> playerInvitationGUIs) {
        PlayerInvitationGUIs = playerInvitationGUIs;
    }

    public static Map<Integer, GuildLevel> getGuildLevelMap() {
        return guildLevelMap;
    }

    public static void setGuildLevelMap(Map<Integer, GuildLevel> guildLevelMap) {
        GuildUtil.guildLevelMap = guildLevelMap;
    }

    public static Map<Integer, VitalityManager> getVitalityManagerMap() {
        return vitalityManagerMap;
    }

    public static void setVitalityManagerMap(Map<Integer, VitalityManager> vitalityManagerMap) {
        GuildUtil.vitalityManagerMap = vitalityManagerMap;
    }

    public static Map<Player, Long> getOnlinetime() {
        return onlinetime;
    }

    public static void setOnlinetime(Map<Player, Long> onlinetime) {
        GuildUtil.onlinetime = onlinetime;
    }
}
