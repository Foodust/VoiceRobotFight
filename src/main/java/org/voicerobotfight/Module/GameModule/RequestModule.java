package org.voicerobotfight.Module.GameModule;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.voicerobotfight.Data.GameData;
import org.voicerobotfight.VoiceRobotFight;
import org.voicerobotfight.Module.BaseModule.MessageModule;
import org.voicerobotfight.Module.BaseModule.TaskModule;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.stream.Collectors;

public class RequestModule implements HttpHandler {
    private final MessageModule messageModule;
    private final TaskModule taskModule;
    private final WordModule wordModule;

    public RequestModule(VoiceRobotFight plugin) {
        this.taskModule = new TaskModule(plugin);
        this.messageModule = new MessageModule(plugin);
        this.wordModule = new WordModule(plugin);
    }

    @Override
    public void handle(HttpExchange exchange) {
        if (exchange.getRequestMethod().equals("POST")) {
            taskModule.runBukkitTask(() -> {
                // 응답 헤더 설정
                try {
                    // 요청 처리 로직
                    JsonNode jsonNode = getJsonNode(exchange);
                    // JSON 처리 로직
                    messageModule.logInfo("Received JSON: " + jsonNode.toString());
                    UUID uuid = UUID.fromString(jsonNode.get("uuid").textValue());
                    String data = jsonNode.get("data").toString();
                    // Spigot API 작업
                    Player player = Bukkit.getPlayer(uuid);
                    if (player == null) {
                        messageModule.logInfo("Player uuid : " + uuid + " not found");
                        return;
                    }
                    if (GameData.isTest) {
                        messageModule.broadcastMessageC(player.getName() + " : " + data);
                    }
                    else if (GameData.isGame) {
                        wordModule.playerWording(player, data);
                    }
                    String response = "{\"status\": \"success\", \"message\": \"Data received successfully\"}";
                    byte[] responseBytes = response.getBytes(StandardCharsets.UTF_8);
                    exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
                    exchange.sendResponseHeaders(200, responseBytes.length);
                    // 응답 본문 작성
                    OutputStream os = exchange.getResponseBody();
                    os.write(responseBytes);
                    os.close();

                } catch (Exception exception) {
                    messageModule.broadcastMessageC(exception.getMessage());
                }
            });
        }
    }

    public JsonNode getJsonNode(HttpExchange exchange) throws IOException {
        String collect = new BufferedReader(new InputStreamReader(exchange.getRequestBody())).lines().collect(Collectors.joining("\n"));
        // RequestBody를 JSON으로 변환
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readTree(collect);
    }
}
