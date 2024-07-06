package org.forbiddenwordgame.Module.GameModule;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Transformation;
import org.bukkit.util.Vector;
import org.forbiddenwordgame.Data.GameData;
import org.forbiddenwordgame.Data.Info.PlayerInfo;
import org.forbiddenwordgame.Data.PlayerData;
import org.forbiddenwordgame.Data.TaskData;
import org.forbiddenwordgame.ForbiddenWordGame;
import org.forbiddenwordgame.Message.BaseMessage;
import org.forbiddenwordgame.Module.BaseModule.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GameModule {
    private final MessageModule messageModule;
    private final TaskModule taskModule;
    private final ConfigModule configModule;
    private final DisplayModule displayModule;
    private final ForbiddenWordGame plugin;

    public GameModule(ForbiddenWordGame plugin) {
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

    public void rouletteGame(Player player) {
        List<String> names = new ArrayList<>();
        for (Map.Entry<Player, PlayerInfo> playerListEntry : PlayerData.playerInfo.entrySet()) {
            names.add(playerListEntry.getKey().getName());
        }
        startRoulette(names, 20L, 5L, 7L);
    }

    public void resultGame(Player player) {

        Location location = player.getLocation().clone();
        Vector direction = location.getDirection().clone().multiply(4);

        Location textDisplayLocation = location.clone().add(direction);
        textDisplayLocation.setY(location.getY() + 5);
        Vector textDisplayDirection = location.toVector().subtract(textDisplayLocation.toVector()).normalize();
        textDisplayDirection.setY(0);
        float yaw = (float) Math.toDegrees(Math.atan2(textDisplayDirection.getZ(), textDisplayDirection.getX())) - 90;
        float pitch = (float) Math.toDegrees(Math.asin(textDisplayDirection.getY()));

        // GUI 생성
        BlockDisplay blockDisplay = displayModule.makeBlockDisplay(player, textDisplayLocation, Material.BLACK_CONCRETE, 10.0);
        blockDisplay.setRotation(yaw, pitch);
        Transformation transformation = blockDisplay.getTransformation();
        transformation.getScale().set(10, 10, 0.01);
        blockDisplay.setTransformation(transformation);

        TextDisplay statusInventoryDisplay = displayModule.makeTextDisplay(player, textDisplayLocation.add(textDisplayDirection.clone().normalize().multiply(0.01)), "결과", 2.0);
        statusInventoryDisplay.setRotation(yaw, pitch);
        statusInventoryDisplay.setAlignment(TextDisplay.TextAlignment.CENTER);

        float i = 0.5f;
        for (Map.Entry<Player, PlayerInfo> entry : PlayerData.playerInfo.entrySet()) {
            i += 0.5f;
            Player key = entry.getKey();
            PlayerInfo value = entry.getValue();
            Vector right = textDisplayDirection.clone().crossProduct(new Vector(0, 1, 0)).normalize().multiply(i);
            Location resultRight = textDisplayLocation.clone().add(right);
            Location nameLocation = resultRight.clone().add(0, -1, 0).add(textDisplayDirection.clone().normalize().multiply(0.01));
            Location countLocation = resultRight.clone().add(0, -2, 0).add(textDisplayDirection.clone().normalize().multiply(0.01));
            Location deathCountLocation = resultRight.clone().add(0, -4, 0).add(textDisplayDirection.clone().normalize().multiply(0.01));

            TextDisplay nameDisplay = displayModule.makeTextDisplay(player, nameLocation, key.getName(), 2.0);
            nameDisplay.setRotation(yaw, pitch);
            nameDisplay.setAlignment(TextDisplay.TextAlignment.CENTER);

            TextDisplay countDisplay = displayModule.makeTextDisplay(player, countLocation, String.valueOf(value.getCount()), 2.0);
            countDisplay.setRotation(yaw, pitch);
            countDisplay.setAlignment(TextDisplay.TextAlignment.CENTER);

            TextDisplay deathCountDisplay = displayModule.makeTextDisplay(player, deathCountLocation, String.valueOf(value.getDeathCount()), 2.0);
            deathCountDisplay.setRotation(yaw, pitch);
            deathCountDisplay.setAlignment(TextDisplay.TextAlignment.CENTER);
        }


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
