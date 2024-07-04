package org.forbiddenwordgame.Module.GameModule;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.forbiddenwordgame.Data.GameData;
import org.forbiddenwordgame.ForbiddenWordGame;
import org.forbiddenwordgame.Module.BaseModule.MessageModule;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class RequestModule implements HttpHandler {
    private final MessageModule messageModule;
    private final WordModule wordModule;

    public RequestModule(ForbiddenWordGame plugin) {
        this.messageModule = new MessageModule(plugin);
        this.wordModule = new WordModule(plugin);
    }

    @Override
    public void handle(HttpExchange exchange) {
        if ("POST".equals(exchange.getRequestMethod())) {
            CompletableFuture.runAsync(() -> {
                try {
                    // 요청 처리 로직
                    InputStreamReader inputStreamReader = new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8);
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line);
                    }
                    String requestBody = stringBuilder.toString();
                    // RequestBody를 JSON으로 변환
                    ObjectMapper objectMapper = new ObjectMapper();
                    JsonNode jsonNode = objectMapper.readTree(requestBody);
                    // JSON 처리 로직
                    messageModule.logInfo("Received JSON: " + jsonNode.toString());
                    UUID uuid = UUID.fromString(jsonNode.get("uuid").toString());
                    String data = jsonNode.get("data").toString();
                    // Spigot API 작업
                    Player player = Bukkit.getPlayer(uuid);
                    if (player == null) {
                        return;
                    }
                    if (GameData.isTest) {
                        messageModule.broadcastMessageC(player.getName() + " : " + data);
                        return;
                    }
                    if (GameData.isGame) {
                        wordModule.playerWording(player, data);
                    }

                } catch (IOException ignore) {
                }
            });
        }
    }
}
