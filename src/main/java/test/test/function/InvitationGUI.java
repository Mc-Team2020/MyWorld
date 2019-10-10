package test.test.function;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import test.test.Main;
import test.test.data.InvitationManager;
import test.test.util.GuildUtil;
import test.test.util.Message_CN;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class InvitationGUI extends BukkitRunnable {
    public InvitationGUI(){

        Bukkit.getConsoleSender().sendMessage("§f[§bGuild§f]: §aEnable InvitationGUI tasks...");
    }
    @Override
    public void run() {
        for(UUID uuid : GuildUtil.getPlayerInvitationGUIs().keySet()){
            if(!Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(uuid)) || Bukkit.getPlayer(uuid) == null || getInvitationLists(Bukkit.getPlayer(uuid)) == null)continue;
            updata(Bukkit.getPlayer(uuid));
        }
    }
    private void updata(Player player){
        Inventory inv = getPlayerInv(player);
        inv.clear();
        List<InvitationManager> lists = getInvitationLists(player);
        for(InvitationManager invitationManager : lists){
            ItemStack itemStack = getItem(invitationManager);
            inv.addItem(itemStack);
        }
        GuildUtil.getPlayerInvitationGUIs().put(player.getUniqueId(),inv);
    }
    private ItemStack getItem(InvitationManager invitationManager){
        ItemStack itemStack = new ItemStack(Main.getMain().getConfig().getInt("Settings.Invitation_GUI.Item.id"));
        ItemMeta meta = itemStack.getItemMeta();
        itemStack.setDurability((short) Main.getMain().getConfig().getInt("Settings.Invitation_GUI.Item.data"));
        meta.setDisplayName("§f§l"+invitationManager.getGuild());
        List<String> lores = new ArrayList<>();
        for(String ss : Message_CN.getList("IvtGuildInfoMation")){
            String message = ss.replace("&","§").replace("<Guild>",invitationManager.getGuild()).replace("<Time>",String.valueOf(invitationManager.getTime())).replace("<Player>",invitationManager.getPlayer().getName());
            lores.add(message);
        }
        meta.setLore(lores);
        itemStack.setItemMeta(meta);
        return itemStack;
    }
    private List<InvitationManager> getInvitationLists(Player player){
        return GuildUtil.getPlayerInvitations().get(player.getUniqueId());
    }
    private Inventory getPlayerInv(Player player){
        return GuildUtil.getPlayerInvitationGUIs().get(player.getUniqueId());
    }
}
