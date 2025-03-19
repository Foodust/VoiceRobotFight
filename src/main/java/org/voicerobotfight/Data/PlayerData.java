package org.voicerobotfight.Data;

import org.bukkit.entity.Player;
import org.voicerobotfight.Data.Info.PlayerInfo;

import java.util.HashMap;

public class PlayerData {

    public static HashMap<Player, PlayerInfo> playerInfo = new HashMap<>();

    public static void release(){
        playerInfo.clear();
    }
}
