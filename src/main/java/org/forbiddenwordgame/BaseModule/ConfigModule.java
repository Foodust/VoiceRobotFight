package org.forbiddenwordgame.BaseModule;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.forbiddenwordgame.ForbiddenWordGame;

import java.io.File;
import java.io.IOException;

public class ConfigModule {
//    private final MessageModule messageModule;
    private final ForbiddenWordGame plugin;

    public ConfigModule(ForbiddenWordGame plugin) {
        this.plugin = plugin;
//        this.messageModule = new MessageModule(plugin);
    }
    public FileConfiguration getConfig(String fileName) {
        File configFile = new File(plugin.getDataFolder(), fileName);
        if (!configFile.exists()) {
            // 파일이 존재하지 않으면 기본 설정을 생성
            plugin.saveResource(fileName, false);
        }
        return YamlConfiguration.loadConfiguration(configFile);
    }

    public void saveConfig(FileConfiguration config, String fileName) {
        File configFile = new File(plugin.getDataFolder(), fileName);
        try {
            config.save(configFile);
        } catch (IOException e) {
            Bukkit.getLogger().info(e.getMessage());
        }
    }
}
