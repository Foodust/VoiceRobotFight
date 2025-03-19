package org.voicerobotfight.Module.BaseModule;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.voicerobotfight.Data.Info.PlayerInfo;
import org.voicerobotfight.Data.PlayerData;
import org.voicerobotfight.VoiceRobotFight;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class ConfigModule {
    private final VoiceRobotFight plugin;
    private final String wordsConfig = "words.yml";

    public ConfigModule(VoiceRobotFight plugin) {
        this.plugin = plugin;
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

    public void getWords() {
        FileConfiguration config = getConfig(wordsConfig);
        for (String name : Objects.requireNonNull(config.getConfigurationSection("")).getKeys(false)) {
            List<String> words = config.getStringList(name);
            Player player = Bukkit.getPlayer(name);
            if (player == null) continue;
            PlayerInfo playerInfo = PlayerInfo.builder()
                    .words(words)
                    .build();
            PlayerData.playerInfo.put(player, playerInfo);
        }
    }
}
