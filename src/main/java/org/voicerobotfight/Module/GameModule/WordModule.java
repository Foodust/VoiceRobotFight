package org.voicerobotfight.Module.GameModule;

import org.bukkit.entity.Player;
import org.voicerobotfight.Data.Info.PlayerInfo;
import org.voicerobotfight.Data.PlayerData;
import org.voicerobotfight.VoiceRobotFight;

public class WordModule {
    private final PenaltyModule penaltyModule;

    public WordModule(VoiceRobotFight plugin) {
        this.penaltyModule = new PenaltyModule(plugin);
    }

    public void playerWording(Player player, String words) {
        if (!PlayerData.playerInfo.containsKey(player)) return;
        if (PlayerData.playerInfo.get(player).getWords().isEmpty()) return;
        PlayerInfo playerInfo = PlayerData.playerInfo.get(player);
        for (String word : playerInfo.getWords()) {
            if (words.contains(word)) {
                playerInfo.setCount(playerInfo.getCount() + 1);
                penaltyModule.penaltyPlayer(player);
                break;
            }
        }
    }
}
