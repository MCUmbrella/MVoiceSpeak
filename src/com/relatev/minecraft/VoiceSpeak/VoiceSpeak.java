package com.relatev.minecraft.VoiceSpeak;

import org.bukkit.plugin.java.*;
import org.bukkit.*;
import org.bukkit.plugin.*;
import org.bukkit.event.*;

public class VoiceSpeak extends JavaPlugin
{
    public static VoiceSpeak MainPlugin;
    
    public void onEnable() {
        VoiceSpeak.MainPlugin = this;
        ConfigManager.init();
        PluginMessager.init();
        VSGUIManager.init();
        this.getLogger().info("\u662f\u4e00\u6b3e\u514d\u8d39\u7684\u8bed\u97f3\u7cfb\u7edfAPI\u63d2\u4ef6");
        this.getLogger().info("\u4f5c\u8005\u63d0\u4e9a\u7c73|QQ1207223090");
    }
    
    public void onDisable() {
        Bukkit.getScheduler().cancelTasks((Plugin)VoiceSpeak.MainPlugin);
        HandlerList.unregisterAll((Plugin)VoiceSpeak.MainPlugin);
    }
}
