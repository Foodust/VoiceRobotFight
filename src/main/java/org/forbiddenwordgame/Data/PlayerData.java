package org.forbiddenwordgame.Data;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;

public class PlayerData {

    public static HashMap<Player, List<String>> words = new HashMap<>();

    public static void release(){
        words.clear();
    }
}
