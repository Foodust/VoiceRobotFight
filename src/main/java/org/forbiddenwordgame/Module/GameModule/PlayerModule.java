package org.forbiddenwordgame.Module.GameModule;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.forbiddenwordgame.Data.Info.PlayerInfo;
import org.forbiddenwordgame.Data.PlayerData;
import org.forbiddenwordgame.ForbiddenWordGame;

public class PlayerModule {

    public PlayerModule(ForbiddenWordGame plugin) {

    }

    public void playerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        if (!PlayerData.playerInfo.containsKey(player)) return;
        PlayerInfo playerInfo = PlayerData.playerInfo.get(player);
        playerInfo.setDeathCount(playerInfo.getDeathCount() + 1);
    }
}
