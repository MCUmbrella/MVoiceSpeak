package com.relatev.minecraft.VoiceSpeak;

import org.bukkit.plugin.messaging.*;
import org.bukkit.*;
import org.bukkit.plugin.*;
import org.bukkit.entity.*;
import org.bukkit.event.player.*;
import com.relatev.minecraft.VoiceSpeak.event.*;
import org.bukkit.event.*;
import com.relatev.minecraft.VoiceSpeak.protocol.packet.*;
import java.util.*;
import com.relatev.minecraft.VoiceSpeak.ConfigManager;
//import com.relatev.minecraft.VoiceSpeak.VoiceSpeakAPI;

public class PluginMessager implements PluginMessageListener, Listener
{
    private static PluginMessager Instance;
    public static final String ChannelID = "VoiceSpeak:Packet";
    public static final List<String> LoginedPlayers;
    
    public static void init() {
        PluginMessager.Instance = new PluginMessager();
        Bukkit.getMessenger().registerIncomingPluginChannel((Plugin)VoiceSpeak.MainPlugin, "VoiceSpeak:Packet", (PluginMessageListener)PluginMessager.Instance);
        Bukkit.getMessenger().registerOutgoingPluginChannel((Plugin)VoiceSpeak.MainPlugin, "VoiceSpeak:Packet");
        Bukkit.getPluginManager().registerEvents((Listener)PluginMessager.Instance, (Plugin)VoiceSpeak.MainPlugin);
        VoiceSpeak.MainPlugin.getLogger().info("PluginMessage registed.");
    }
    
    public static void sendPacket(final Player player, final PMPacket packet) {
        player.sendPluginMessage((Plugin)VoiceSpeak.MainPlugin, "VoiceSpeak:Packet", packet.toArray());
    }
    
    @EventHandler
    public void onQuit(final PlayerQuitEvent event) {
        PluginMessager.LoginedPlayers.remove(event.getPlayer().getName());
    }
    
    public void onPluginMessageReceived(final String string, final Player player, final byte[] bytes) {
    	String hasMod=(String) ConfigManager.config.get("Messages.HasMod");
        final PMPacket pmp = PMPacket.toPacket(bytes);
        if (pmp != null) {
            if (pmp instanceof ClientVSReadyPacket) {
                if((boolean) ConfigManager.config.get("Debug")){VoiceSpeak.MainPlugin.getLogger().info(player.getName() + hasMod);}//send HasModMsg
                sendPacket(player, new ServerVSInitPacket((float)ConfigManager.config.getDouble("VoiceRecord.SampleRate"), ConfigManager.config.getInt("VoiceRecord.SampleSizeInBits"), ConfigManager.config.getInt("VoiceRecord.Channels"), ConfigManager.config.getLong("VoiceRecord.MaxRecordMS")));
                if (!PluginMessager.LoginedPlayers.contains(player.getName())) {
                    PluginMessager.LoginedPlayers.add(player.getName());
                }//else {VoiceSpeakAPI.sendNoModMessage(null, player);}//sendNoModMsg
                //TODO: find a place to send noModMsg to mod-missing players when they join
            }
            if (pmp instanceof ClientVoiceSendPacket) {
                final ClientVoiceSendPacket packet = (ClientVoiceSendPacket)pmp;
                final PlayerVoiceSendEvent event = new PlayerVoiceSendEvent(player, packet);
                Bukkit.getPluginManager().callEvent((Event)event);
            }
            if (pmp instanceof ClientVSSettingButtonPacket) {
                VSGUIManager.open(player);
            }
        }
    }
    
    static {
        LoginedPlayers = new ArrayList<String>();
    }
}
