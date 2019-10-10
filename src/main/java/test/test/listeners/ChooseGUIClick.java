package test.test.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.scheduler.BukkitRunnable;
import test.test.Main;
import test.test.api.GuildAPIPlus;
import test.test.api.GuildPlayerAPI;
import test.test.api.ProjectGuildAPI;
import test.test.data.InvitationManager;
import test.test.util.GuildUtil;
import test.test.util.Message_CN;

import java.util.List;

public class ChooseGUIClick implements Listener {

    @EventHandler
    public void onclick(InventoryClickEvent event) {
        boolean is = new GuildAPIPlus(event.getInventory().getTitle()).is();
        if (is) {
            event.setCancelled(true);
            int a = Main.getMain().getConfig().getInt("Settings.ChooseGUI.AcceptItem.size");
            int r = Main.getMain().getConfig().getInt("Settings.ChooseGUI.RefuseItem.size");
            Player player = (Player) event.getWhoClicked();
            InvitationManager invitationManager = getManager(player,event.getInventory().getTitle());
            ProjectGuildAPI projectGuildAPI = new ProjectGuildAPI();
            GuildPlayerAPI playerAPI = projectGuildAPI.getPlayerAPI(player);
            if (event.getRawSlot() == a) {
                if(playerAPI.is()){
                    player.sendMessage(Message_CN.getMessage("Invitationis"));
                    event.getWhoClicked().closeInventory();
                   return;
                }

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        player.sendMessage(Message_CN.getMessage("Invitation.Accept"));
                        projectGuildAPI.JoinGuild(invitationManager.getPlayer(),player);
                    }
                }.runTaskAsynchronously(Main.getMain());
            }
            if (event.getRawSlot() == r) {
                projectGuildAPI.RefuseInvitation(player,event.getInventory().getTitle());
                player.sendMessage(Message_CN.getMessage("Invitation.Refuse"));
            }
            event.getWhoClicked().closeInventory();
        }
    }
    private InvitationManager getManager(Player player,String Title){

        List<InvitationManager> invitationManager = GuildUtil.getPlayerInvitations().get(player.getUniqueId());
        for (InvitationManager manager : invitationManager) {
            if(manager.getGuild().equalsIgnoreCase(Title))return manager;
        }
        return null;
    }
}
