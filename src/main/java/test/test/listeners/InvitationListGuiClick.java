package test.test.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import test.test.Main;
import test.test.util.GuildAPI;

public class InvitationListGuiClick implements Listener {
    @EventHandler
    public void onclick(InventoryClickEvent e){
        if(e.getInventory().getTitle().equalsIgnoreCase(Main.getMain().getConfig().getString("Settings.Invitation_GUI.Prefix"))){
            if(e.getRawSlot() < e.getInventory().getSize()){
                if(e.getCurrentItem() == null)return;
                String mess = e.getCurrentItem().getItemMeta().getDisplayName().replace("§f§l","");
                new GuildAPI((Player) e.getWhoClicked()).openChooseGUI(mess);
            }
            e.setCancelled(true);
        }
    }
}
