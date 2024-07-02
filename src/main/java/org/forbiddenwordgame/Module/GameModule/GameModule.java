package org.forbiddenwordgame.Module.GameModule;

import ir.taher7.melodymine.core.MelodyManager;
import ir.taher7.melodymine.models.MelodyPlayer;
import org.forbiddenwordgame.ForbiddenWordGame;
import org.forbiddenwordgame.Module.BaseModule.MessageModule;
import org.forbiddenwordgame.Module.BaseModule.TaskModule;

public class GameModule {
    private final MessageModule messageModule;
    private final TaskModule taskModule;

    public GameModule(ForbiddenWordGame plugin) {
        this.messageModule = new MessageModule(plugin);
        this.taskModule = new TaskModule(plugin);
    }

    public void checkWebConnection(String playerUUID) {
        MelodyPlayer player = MelodyManager.INSTANCE.getMelodyPlayer(playerUUID);

        if (player != null) {
            MelodyManager.INSTANCE.checkPlayerWebConnection(player);
            messageModule.logInfo("Checking web connection for player " + player.getName());
        } else {
            messageModule.logInfo("Player not found.");
        }
    }
}
