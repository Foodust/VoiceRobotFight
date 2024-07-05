package org.forbiddenwordgame.Data;

import org.bukkit.entity.Player;
import org.forbiddenwordgame.Data.Info.PlayerInfo;

import java.util.HashMap;

public class PlayerData {

    public static HashMap<Player, PlayerInfo> playerInfo = new HashMap<>();

    public static void release(){
        playerInfo.clear();
    }
}
