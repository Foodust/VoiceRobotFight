package org.voicerobotfight.Module.GameModule;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.voicerobotfight.Data.Info.PlayerInfo;
import org.voicerobotfight.Data.PlayerData;
import org.voicerobotfight.VoiceRobotFight;

public class PlayerModule {

    public PlayerModule(VoiceRobotFight plugin) {

    }

    public void playerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        if (!PlayerData.playerInfo.containsKey(player)) return;
        PlayerInfo playerInfo = PlayerData.playerInfo.get(player);
        playerInfo.setDeathCount(playerInfo.getDeathCount() + 1);
    }
}
