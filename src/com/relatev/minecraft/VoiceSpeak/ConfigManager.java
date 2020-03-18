package com.relatev.minecraft.VoiceSpeak;

import org.bukkit.configuration.file.*;
import java.util.logging.*;
import java.io.*;

public class ConfigManager
{
    private static File ConfigFile;
    public static YamlConfiguration config;
    
    public static void init() {
        ConfigManager.ConfigFile = new File(VoiceSpeak.MainPlugin.getDataFolder(), "config.yml");
        if (!ConfigManager.ConfigFile.exists()) {
            ConfigManager.ConfigFile.getParentFile().mkdirs();
            try {
                ConfigManager.ConfigFile.createNewFile();
            }
            catch (IOException ex) {
                Logger.getLogger(ConfigManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        ConfigManager.config = YamlConfiguration.loadConfiguration(ConfigManager.ConfigFile);
        if (ConfigManager.config.getInt("Version") != 101) {
            ConfigManager.config.set("Version", (Object)101);
            ConfigManager.config.set("Debug", (Object)false);
            ConfigManager.config.set("VoiceRecord.SampleRate", (Object)8000.0f);
            ConfigManager.config.set("VoiceRecord.SampleSizeInBits", (Object)8);
            ConfigManager.config.set("VoiceRecord.Channels", (Object)1);
            ConfigManager.config.set("VoiceRecord.MaxRecordMS", (Object)8000);
            ConfigManager.config.set("Messages.NoMod", (Object)"The server has VoiceSpeak installed. Download it here.");
            ConfigManager.config.set("Messages.HasMod", (Object)" has VoiceSpeak mod installed.");
            ConfigManager.config.set("Messages.Download", (Object)"Click to download");
            try {
                ConfigManager.config.save(ConfigManager.ConfigFile);
            }
            catch (IOException ex) {
                Logger.getLogger(ConfigManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
