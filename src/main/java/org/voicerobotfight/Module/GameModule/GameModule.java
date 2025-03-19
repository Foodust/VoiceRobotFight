package org.voicerobotfight.Module.GameModule;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.voicerobotfight.Data.GameData;
import org.voicerobotfight.Data.PlayerData;
import org.voicerobotfight.Data.TaskData;
import org.voicerobotfight.Message.BaseMessage;
import org.voicerobotfight.Module.BaseModule.ConfigModule;
import org.voicerobotfight.Module.BaseModule.DisplayModule;
import org.voicerobotfight.Module.BaseModule.MessageModule;
import org.voicerobotfight.Module.BaseModule.TaskModule;
import org.voicerobotfight.VoiceRobotFight;

public class GameModule {
    private final MessageModule messageModule;
    private final TaskModule taskModule;
    private final ConfigModule configModule;
    private final DisplayModule displayModule;
    private final VoiceRobotFight plugin;

    public GameModule(VoiceRobotFight plugin) {
        this.plugin = plugin;
        this.displayModule = new DisplayModule();
        this.messageModule = new MessageModule(plugin);
        this.taskModule = new TaskModule(plugin);
        this.configModule = new ConfigModule(plugin);
    }

    public void testGame(Player player) {
        GameData.isTest = true;
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            TextComponent textComponent = Component.text("클릭하여 접속하기!").clickEvent(ClickEvent.openUrl("https://okangun.kro.kr/?uuid=" + onlinePlayer.getUniqueId()));
            messageModule.sendPlayerNoPrefixC(onlinePlayer, textComponent);
        }
    }

    public void reloadGame(Player player) {
        GameData.release();
        TaskData.release();
        PlayerData.release();
        configModule.getWords();
    }

    public void startGame(Player player) {
        if (GameData.isGame) {
            messageModule.sendPlayerC(player, BaseMessage.ERROR_GAME_IS_START.getMessage());
            return;
        }
        GameData.isTest = false;
        GameData.isGame = true;
    }

    public void stopGame(Player player) {
        if (!GameData.isGame) {
            messageModule.sendPlayerC(player, BaseMessage.ERROR_GAME_IS_NOT_START.getMessage());
            return;
        }
        GameData.release();
        TaskData.release();
    }
}
