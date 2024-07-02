package org.forbiddenwordgame.Event;

import ir.taher7.melodymine.api.events.PlayerChangeTalkEvent;
import ir.taher7.melodymine.api.events.PlayerStartVoiceEvent;
import ir.taher7.melodymine.models.MelodyPlayer;
import ir.taher7.melodymine.models.MelodyTalk;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.forbiddenwordgame.ForbiddenWordGame;

public class PlayerEvent implements Listener {

    public PlayerEvent(ForbiddenWordGame plugin) {

    }

    @EventHandler
    public void onPlayerChangeTalk(PlayerChangeTalkEvent event) {
        MelodyPlayer player = event.getMelodyPlayer();
        String server = event.getMelodyTalk().getServer();
        MelodyTalk melodyTalk = event.getMelodyTalk();
    }
}
