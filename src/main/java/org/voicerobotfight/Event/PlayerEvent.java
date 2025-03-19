package org.voicerobotfight.Event;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.voicerobotfight.VoiceRobotFight;
import org.voicerobotfight.Module.GameModule.PlayerModule;

public class PlayerEvent implements Listener {

    private final PlayerModule playerModule;
    public PlayerEvent(VoiceRobotFight plugin) {
        this.playerModule = new PlayerModule(plugin);

    }
    @EventHandler
    public void playerDeathEvent(PlayerDeathEvent event){
        playerModule.playerDeath(event);
    }
}
