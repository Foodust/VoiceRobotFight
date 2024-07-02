package org.forbiddenwordgame.Event;

import ir.taher7.melodymine.api.events.PlayerStartVoiceEvent;
import ir.taher7.melodymine.models.MelodyPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerEvent implements Listener {

    @EventHandler
    public void onPlayerStartVoice(PlayerStartVoiceEvent event) {
        MelodyPlayer melodyPlayer = event.getMelodyPlayer();
    }
}
