package test.test.data;


public class GuildManager {
    private String GuildName;
    private int Level;
    private String MainPlayer;
    private int Contribution;
    private int MemberSize;
    public GuildManager(String GuildName,int level,String MainPlayer,int contribution,int MemberSize){
        setMemberSize(MemberSize);
        setMainPlayer(MainPlayer);
        setContribution(contribution);
        setLevel(level);
        setGuildName(GuildName);
    }
    public String getGuildName() {
        return GuildName;
    }

    public void setGuildName(String guildName) {
        GuildName = guildName;
    }

    public int getLevel() {
        return Level;
    }

    public void setLevel(int level) {
        Level = level;
    }

    public String getMainPlayer() {
        return MainPlayer;
    }

    public void setMainPlayer(String mainPlayer) {
        MainPlayer = mainPlayer;
    }

    public int getContribution() {
        return Contribution;
    }

    public void setContribution(int contribution) {
        Contribution = contribution;
    }

    public int getMemberSize() {
        return MemberSize;
    }

    public void setMemberSize(int memberSize) {
        MemberSize = memberSize;
    }
}
