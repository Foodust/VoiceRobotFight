package org.forbiddenwordgame.Module.GameModule;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.forbiddenwordgame.Data.GameData;
import org.forbiddenwordgame.Data.PlayerData;
import org.forbiddenwordgame.Data.TaskData;
import org.forbiddenwordgame.Data.TickData;
import org.forbiddenwordgame.ForbiddenWordGame;
import org.forbiddenwordgame.Message.BaseMessage;
import org.forbiddenwordgame.Module.BaseModule.ConfigModule;
import org.forbiddenwordgame.Module.BaseModule.MessageModule;
import org.forbiddenwordgame.Module.BaseModule.TaskModule;

public class GameModule {
    private final MessageModule messageModule;
    private final TaskModule taskModule;
    private final ConfigModule configModule;

    public GameModule(ForbiddenWordGame plugin) {
        this.messageModule = new MessageModule(plugin);
        this.taskModule = new TaskModule(plugin);
        this.configModule = new ConfigModule(plugin);
    }

    public void testGame(Player player) {
        GameData.isTest = true;
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            TextComponent textComponent = Component.text("<bold>클릭하여 접속하기!</bold>").clickEvent(ClickEvent.openUrl("http://okaung.kro.kr:8080/uuid=?" + onlinePlayer.getUniqueId()));
            messageModule.sendPlayerC(onlinePlayer, textComponent);
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
        if (GameData.isGame) {
            GameData.release();
            TaskData.release();
        } else {
            messageModule.sendPlayerC(player, BaseMessage.ERROR_GAME_IS_NOT_START.getMessage());
        }
    }
}
