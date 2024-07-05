package org.forbiddenwordgame.Module.GameModule;

import org.bukkit.entity.Player;
import org.forbiddenwordgame.Data.PlayerData;
import org.forbiddenwordgame.ForbiddenWordGame;

public class WordModule {
    private final PenaltyModule penaltyModule;

    public WordModule(ForbiddenWordGame plugin) {
        this.penaltyModule = new PenaltyModule(plugin);
    }

    public void playerWording(Player player, String words) {
        if (!PlayerData.words.containsKey(player)) return;
        if (!PlayerData.words.get(player).isEmpty()) return;

        for (String word : PlayerData.words.get(player)) {
            if (words.contains(word)) {
                penaltyModule.penaltyPlayer(player);
                break;
            }
        }
    }
}
