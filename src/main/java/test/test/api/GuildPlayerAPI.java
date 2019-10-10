package test.test.api;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import test.test.Main;
import test.test.data.GuildLevel;
import test.test.data.MemberAttribute;
import test.test.data.RockingQianShuManager;
import test.test.data.Trigger;
import test.test.util.GuildUtil;
import test.test.util.Message_CN;
import test.test.util.VaultAPI;

import java.util.*;

public class GuildPlayerAPI {

    private Player player;
    private GuildAPIPlus guildAPI;
    private GuildOfflinePlayerAPI offlinePlayerAPI;
    private static GuildPlayerAPI guildPlayerAPI;

    static {
        guildPlayerAPI = new GuildPlayerAPI();
    }
    public GuildPlayerAPI() {
    }
    public GuildPlayerAPI(Player player) {
        setPlayer(player);
        setGuildAPI(new GuildAPIPlus(getPlayer()));
        setOfflinePlayerAPI(new GuildOfflinePlayerAPI(getPlayer().getName()));
    }

    public static GuildPlayerAPI getGuildPlayerAPI() {
        return guildPlayerAPI;
    }

    public void sendMemberInfo() {
        if (!is()) {
            getPlayer().sendMessage(Message_CN.getMessage("PlayerGuildExix"));
            return;
        }
        new BukkitRunnable() {
            @Override
            public void run() {
                List<String> getMessage = Message_CN.getList("MemberInfo");
                for (String s : getMessage) {
                    s = s.replace("&", "§");
                    if (!s.contains("${member}")) {
                        s = s
                                .replace("<MainPlayer>", getGuildAPI().getGuildMainPlayer())
                                .replace("<MemberSize>", String.valueOf(getGuildAPI().getMemberList().size()))
                                .replace("<MemberOnLineSize>", String.valueOf(getMemberOnLineSize()));
                        getPlayer().sendMessage(s);
                    } else {
                        String finalS = s;
                        List<String> messlist = new ArrayList<>();
                        messlist.add(sendmemberInfoMessage(finalS, getMainAttribute()));
                        getOnLine().forEach((v) -> messlist.add(sendmemberInfoMessage(finalS, v)));
                        getNotOnLine().forEach((v) -> messlist.add(sendmemberInfoMessage(finalS, v)));
                        messlist.forEach((v) -> getPlayer().sendMessage(v));
                    }
                }
            }
        }.runTaskAsynchronously(Main.getMain());
    }

    public void KickGuildMember(String MemberPlayer) {
        if (!is()) {
            getPlayer().sendMessage(Message_CN.getMessage("PlayerGuildExix"));
        } else if (!getGuildAPI().getMemberList().contains(MemberPlayer)) {
            getPlayer().sendMessage(Message_CN.getMessage("Player_Not_belong_to"));
        }else if(getPlayer().getName().equalsIgnoreCase(MemberPlayer)){
            getPlayer().sendMessage(Message_CN.getMessage("PlayerOneself"));
        }else {
            getGuildAPI().removeMember(MemberPlayer);
            new GuildOfflinePlayerAPI(MemberPlayer).setGuild("-");
            String mess = Message_CN.getMessage("KickMember");
            getPlayer().sendMessage(mess);
        }
    }

    public void setPVP(boolean b) {
        if (!is()) {
            getPlayer().sendMessage(Message_CN.getMessage("PlayerGuildExix"));
        } else {
            getGuildAPI().setPVP(b);
            String[] mess = Message_CN.getMessage("PVP").split("::");
            String[] args = mess[1].split(",");
            if (getGuildAPI().getPVP()) {
                getPlayer().sendMessage(mess[0].replace("<PVP>", args[0]));
            } else {
                getPlayer().sendMessage(mess[0].replace("<PVP>", args[1]));
            }
        }
    }

    public void setGuildMain(String MemberPlayer) {
        if (!is()) {
            getPlayer().sendMessage(Message_CN.getMessage("PlayerGuildExix"));
        } else if (!getGuildAPI().getMemberList().contains(MemberPlayer)) {
            getPlayer().sendMessage(Message_CN.getMessage("Player_Not_belong_to"));
        } else {
            getGuildAPI().setGuildMainPlayer(MemberPlayer);
        }
    }

    public boolean isPlayertoGuild(Player player) {
        if (is()) {
            return false;
        } else if (getGuildAPI().getMemberList().contains(player.getName())) {
            return true;
        }
        return false;
    }

    public void LevelUp() {
        if (!is()) {
            getPlayer().sendMessage(Message_CN.getMessage("PlayerGuildExix"));
        } else if (!getGuildAPI().isMain()) {
            getPlayer().sendMessage("isMainPlayer");
        } else {
            GuildLevel guildLevel = GuildUtil.getGuildLevelMap().get(getGuildAPI().getLevel() + 1);
            if (guildLevel.getMoney() > getGuildAPI().getMoney()) {
                String mess = Message_CN.getMessage("LevelUp.MoneyNotEnough").replace("<Money>", String.valueOf(guildLevel.getMoney())).replace("<GMoney>", String.valueOf(getGuildAPI().getMoney()));
                getPlayer().sendMessage(mess);
            } else if (guildLevel.getContribution() > getGuildAPI().getContribution()) {
                String mess = Message_CN.getMessage("LevelUp.ContributionNotEnough").replace("<Contribution>", String.valueOf(guildLevel.getContribution())).replace("<GContribution>", String.valueOf(getGuildAPI().getContribution()));
                getPlayer().sendMessage(mess);
            } else {
                getGuildAPI().setLevel(getGuildAPI().getLevel() + 1);
                getGuildAPI().setMoney((int) guildLevel.getMoney());
                getGuildAPI().takeContribution(guildLevel.getContribution());
                getPlayer().sendMessage("升级成功！");
            }
        }
    }

    public void RockQianShuFunction() {
        if (!is()) {
            getPlayer().sendMessage(Message_CN.getMessage("PlayerGuildExix"));
        } else if (getOfflinePlayerAPI().getRockQianShuSize() <= 0) {
            getPlayer().sendMessage(Message_CN.getMessage("RockQianShu.SizeNot"));
        } else {
            RockingQianShuManager rockingQianShuManager = getRockingQianShuManager();
            int money = RandomMoney(rockingQianShuManager.getMoney());
            VaultAPI.giveMoney(getPlayer().getName(), money);
            RockingQianShuTriggerImplement(rockingQianShuManager.getTrigger(), money);
            getOfflinePlayerAPI().takeRockQianShuSize(1);
        }
    }

    public void RockingQianShuTriggerImplement(List<Trigger> triggers, int money) {
        for (Trigger trigger : triggers) {
            trigger.implement(player, money);
        }
    }

    public RockingQianShuManager getRockingQianShuManager() {
        GuildLevel level = GuildUtil.getGuildLevelMap().get(getGuildAPI().getLevel());
        return level.getRockingQianShuManagerMap().get(getOfflinePlayerAPI().getRockQianShuSize());
    }

    //获取帮会的在线成员
    public List<MemberAttribute> getOnLine() {
        List<MemberAttribute> lists = new ArrayList<>();
        for (MemberAttribute memberAttribute : getMemberList()) {
            if (memberAttribute.getPlayer().equalsIgnoreCase(getGuildAPI().getGuildMainPlayer())) continue;
            if (getMemberState(memberAttribute)) {
                lists.add(memberAttribute);
            }
        }
        return getMemberAttributes(lists);
    }

    //获取帮会的不在线成员
    public List<MemberAttribute> getNotOnLine() {
        List<MemberAttribute> lists = new ArrayList<>();
        for (MemberAttribute memberAttribute : getMemberList()) {
            if (memberAttribute.getPlayer().equalsIgnoreCase(getGuildAPI().getGuildMainPlayer())) continue;
            if (!getMemberState(memberAttribute)) {
                lists.add(memberAttribute);
            }
        }
        return getMemberAttributes(lists);
    }

    public List<MemberAttribute> getMemberList() {

        List<MemberAttribute> lists = new ArrayList<>();
        for (String member : getGuildAPI().getMemberList()) {
            GuildOfflinePlayerAPI offlinePlayerAPI = new GuildOfflinePlayerAPI(member);
            String Group = offlinePlayerAPI.getPlayerGroup();
            int pr = offlinePlayerAPI.getPrestige();
            lists.add(new MemberAttribute(member, Group, pr));
        }

        return lists;
    }

    private List<MemberAttribute> getMemberAttributes(List<MemberAttribute> lists) {
        Collections.sort(lists, new Comparator<MemberAttribute>() {
            @Override
            public int compare(MemberAttribute o1, MemberAttribute o2) {
                int i = o2.getPrestige() - o1.getPrestige();
                return i;
            }
        });
        return lists;
    }

    public int RandomMoney(String money) {
        String[] ss = money.split("-");
        int min = Integer.parseInt(ss[0]);
        int max = Integer.parseInt(ss[1]);
        return new Random().nextInt(max - min + 1) + min;
    }

    public MemberAttribute getMainAttribute() {
        GuildOfflinePlayerAPI playerAPI = new GuildOfflinePlayerAPI(getGuildAPI().getGuildMainPlayer());
        int pr = playerAPI.getPrestige();
        String Group = playerAPI.getPlayerGroup();
        return new MemberAttribute(getGuildAPI().getGuildMainPlayer(), Group, pr);
    }

    public String sendmemberInfoMessage(String s, MemberAttribute memberAttribute) {

        String info = s
                .replace("${member}", "")
                .replace("<Player>", memberAttribute.getPlayer())
                .replace("<Group>", memberAttribute.getGroup())
                .replace("<Prestige>", String.valueOf(memberAttribute.getPrestige()))
                .replace("<MemberState>", getMemberState(Bukkit.getPlayer(memberAttribute.getPlayer())));
        return info;
    }

    public String getMemberState(Player player) {
        if (Bukkit.getOnlinePlayers().contains(player)) {
            return Message_CN.getMessage("MemberState.Online");
        } else {
            return Message_CN.getMessage("MemberState.NotOnline");
        }
    }

    //帮会成员是否在线
    public boolean getMemberState(MemberAttribute attribute) {
        if (Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(attribute.getPlayer()))) return true;
        return false;
    }

    private int getMemberOnLineSize() {
        int size = 0;
        for (String player : getGuildAPI().getMemberList()) {
            if (Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(player))) size++;
        }
        return size;
    }

    //如果没加入则返回false 如果加入了就返回true
    public boolean is() {
        if (getOfflinePlayerAPI().getGuild().equalsIgnoreCase("-")) return false;
        return true;
    }

    //解散
    public void deleteGuild() {

        if (getOfflinePlayerAPI().getGuild().equalsIgnoreCase("-")) {
            getPlayer().sendMessage(Message_CN.getMessage("PlayerGuildExix"));
        } else if (!getGuildAPI().isMain()) {
            getPlayer().sendMessage(Message_CN.getMessage("DeleteGuild.isMain"));
        } else {
            getOfflinePlayerAPI().setGuild("-");
            getGuildAPI().delete();
            getPlayer().sendMessage(Message_CN.getMessage("DeleteGuild.Success"));
        }
    }

    public void sendGuildChatMessage(String Message, Player player) {
        if (getOfflinePlayerAPI().getGuild().equalsIgnoreCase("-")) {
            player.sendMessage(Message_CN.getMessage("PlayerGuildExix"));
        } else {
            String mess = getChatFormat(player, Message);
            Bukkit.getConsoleSender().sendMessage(mess);
            getGuildAPI().getMemberList().forEach((v) -> {
                if (Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(v))) {
                    Bukkit.getPlayer(v).sendMessage(mess);
                }
            });
        }
    }

    private String getChatFormat(Player player, String mess) {
        return Main.getMain().getConfig().getString("Settings.GuildChat.Formal").replace("{player}", player.getName()).replace("{message}", mess).replace("&", "§");
    }

    public void setMemberGroup(String MemberPlayer, String Value) {
        if (!is()) {
            getPlayer().sendMessage(Message_CN.getMessage("PlayerGuildExix"));
        } else if (!new GuildAPIPlus(getPlayer()).isMain()) {
            getPlayer().sendMessage("isMainPlayer");
        } else if (!getGuildAPI().getMemberList().contains(MemberPlayer)) {
            getPlayer().sendMessage(Message_CN.getMessage("Player_Not_belong_to"));
        } else {
            new GuildOfflinePlayerAPI(MemberPlayer).setGroup(Value);
            String mess = Message_CN.getMessage("setMemberGroup").replace("<Player>", MemberPlayer).replace("<Group>", Value);
            getPlayer().sendMessage(mess);
        }

    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public GuildAPIPlus getGuildAPI() {
        return guildAPI;
    }

    public void setGuildAPI(GuildAPIPlus guildAPI) {
        this.guildAPI = guildAPI;
    }

    public GuildOfflinePlayerAPI getOfflinePlayerAPI() {
        return offlinePlayerAPI;
    }

    public void setOfflinePlayerAPI(GuildOfflinePlayerAPI offlinePlayerAPI) {
        this.offlinePlayerAPI = offlinePlayerAPI;
    }
}
