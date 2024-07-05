package org.forbiddenwordgame.Module.GameModule;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.forbiddenwordgame.Data.GameData;
import org.forbiddenwordgame.Data.PlayerData;
import org.forbiddenwordgame.Data.TaskData;
import org.forbiddenwordgame.ForbiddenWordGame;
import org.forbiddenwordgame.Message.BaseMessage;
import org.forbiddenwordgame.Module.BaseModule.ConfigModule;
import org.forbiddenwordgame.Module.BaseModule.MessageModule;
import org.forbiddenwordgame.Module.BaseModule.TaskModule;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
            TextComponent textComponent = Component.text("클릭하여 접속하기!").clickEvent(ClickEvent.openUrl("https://okangun.kro.kr:8080/?uuid=" + onlinePlayer.getUniqueId()));
            messageModule.sendPlayerNoPrefixC(onlinePlayer, textComponent);
        }
    }

    public void reloadGame(Player player) {
        GameData.release();
        TaskData.release();
        PlayerData.release();
        configModule.getWords();
    }

    public void roulette(Player player) {
        List<String> names = new ArrayList<>();
        for (Map.Entry<Player, List<String>> playerListEntry : PlayerData.words.entrySet()) {
            names.add(playerListEntry.getKey().getName());
        }
        startRoulette(names, 20L, 5L, 7L);
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

    public void startRoulette(List<String> items, long initialDelay, long finalDelay, long durationSeconds) {
        taskModule.runBukkitTaskLater(new BukkitRunnable() {
            int index = 0;
            long currentDelay = initialDelay;
            long startTime = System.currentTimeMillis();
            long endTime = startTime + durationSeconds * 1000;

            @Override
            public void run() {
                if (System.currentTimeMillis() >= endTime) {
                    // 룰렛 종료
                    cancel();
                    messageModule.sendAllPlayerTitle(items.get(index), 0, 3, 0);
                    return;
                }

                // 아이템 선택
                messageModule.sendAllPlayerTitle(items.get(index), 0, 1, 0);
                index = (index + 1) % items.size();

                // 딜레이 증가
                currentDelay = Math.min(currentDelay + 1, finalDelay);
                taskModule.runBukkitTaskLater(new BukkitRunnable() {
                    @Override
                    public void run() {
                        startRoulette(items, currentDelay, finalDelay, (endTime - System.currentTimeMillis()) / 1000);
                    }
                }, currentDelay);
            }
        }, initialDelay);
    }
}
