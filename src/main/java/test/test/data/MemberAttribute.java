package test.test.data;


public class MemberAttribute {
    private String player;
    private String Group;
    private int Prestige;
    public MemberAttribute(String player,String a,int b){
        setGroup(a);
        setPrestige(b);
        setPlayer(player);
    }


    public int getPrestige() {
        return Prestige;
    }

    public void setPrestige(int prestige) {
        Prestige = prestige;
    }


    public String getGroup() {
        return Group;
    }

    public void setGroup(String group) {
        Group = group;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }
}
