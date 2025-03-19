package org.voicerobotfight.Event;

import org.bukkit.Server;
import org.voicerobotfight.VoiceRobotFight;

// Event Listener 들을 등록하기 위해 만들어 졌습니다
public class EventManager {
    public EventManager(Server server, VoiceRobotFight plugin) {
        server.getPluginManager().registerEvents(new PlayerEvent(plugin),plugin);
    }
}
