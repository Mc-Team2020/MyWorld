package test.test.util;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import test.test.Main;

import java.util.ArrayList;

public class GuildAPI {
    private Player player;
    private Message_CN message_cn;

    public GuildAPI(Player player) {
        setPlayer(player);
        setMessage_cn(new Message_CN(getPlayer()));
    }

    public void openChooseGUI(String guild) {
        Inventory inventory = Bukkit.createInventory(null, Main.getMain().getConfig().getInt("Settings.ChooseGUI.size"), guild);
        inventory.setItem(Main.getMain().getConfig().getInt("Settings.ChooseGUI.AcceptItem.size"), getItem("AcceptItem"));
        inventory.setItem(Main.getMain().getConfig().getInt("Settings.ChooseGUI.RefuseItem.size"), getItem("RefuseItem"));
        getPlayer().closeInventory();
        getPlayer().openInventory(inventory);
    }

    public ItemStack getItem(String Path) {
        ItemStack itemStack = new ItemStack(Main.getMain().getConfig().getInt("Settings.ChooseGUI." + Path + ".id"));
        ItemMeta meta = itemStack.getItemMeta();
        itemStack.setDurability((short) Main.getMain().getConfig().getInt("Settings.ChooseGUI." + Path + ".data"));
        meta.setLore(Main.getMain().getConfig().getStringList("Settings.ChooseGUI." + Path + ".lore"));
        meta.setDisplayName(Main.getMain().getConfig().getString("Settings.ChooseGUI." + Path + ".name"));
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    public void openInvitationGui() {
        Inventory inv = GuildUtil.getPlayerInvitationGUIs().get(getPlayer().getUniqueId());
        getPlayer().closeInventory();
        getPlayer().openInventory(inv);
    }

    public void addInvitationMap() {
        Inventory inv = Bukkit.createInventory(null, Main.getMain().getConfig().getInt("Settings.Invitation_GUI.Size"), Main.getMain().getConfig().getString("Settings.Invitation_GUI.Prefix"));
        GuildUtil.getPlayerInvitationGUIs().put(getPlayer().getUniqueId(), inv);
        GuildUtil.getPlayerInvitations().put(getPlayer().getUniqueId(),new ArrayList<>());
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Message_CN getMessage_cn() {
        return message_cn;
    }

    public void setMessage_cn(Message_CN message_cn) {
        this.message_cn = message_cn;
    }
}
