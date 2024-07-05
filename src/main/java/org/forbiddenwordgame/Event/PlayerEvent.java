package org.forbiddenwordgame.Event;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.forbiddenwordgame.ForbiddenWordGame;
import org.forbiddenwordgame.Module.GameModule.PlayerModule;

public class PlayerEvent implements Listener {

    private final PlayerModule playerModule;
    public PlayerEvent(ForbiddenWordGame plugin) {
        this.playerModule = new PlayerModule(plugin);

    }
    @EventHandler
    public void playerDeathEvent(PlayerDeathEvent event){
        playerModule.playerDeath(event);
    }
}
