package com.relatev.minecraft.VoiceSpeak;

import org.bukkit.entity.*;
import com.relatev.minecraft.VoiceSpeak.protocol.packet.*;
import net.md_5.bungee.api.chat.*;
import com.relatev.minecraft.VoiceSpeak.ConfigManager;

public class VoiceSpeakAPI
{
	static String noMod=(String) ConfigManager.config.get("Messages.NoMod");
	static String here=(String) ConfigManager.config.get("Messages.Download");
    public static void playVoiceTemp(final Player player, final byte[] compressedarray, final String sender) {
        if (PluginMessager.LoginedPlayers.contains(player.getName())) {
            PluginMessager.sendPacket(player, new ServerVoicePlayTempPacket(compressedarray, sender));
        }
        /*else {
            sendNoModMessage(sender, player);
        }*/
    }
    
    public static void sendNoModMessage(final String sender, final Player player) {
            final TextComponent showtutorialsetting = new TextComponent(noMod);
            showtutorialsetting.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://www.relatev.com/forum.php?mod=viewthread&tid=2547"));
            showtutorialsetting.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(here).create()));
            player.spigot().sendMessage((BaseComponent)showtutorialsetting);
        
    }
}
