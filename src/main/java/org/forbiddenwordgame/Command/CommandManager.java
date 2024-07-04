package org.forbiddenwordgame.Command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.forbiddenwordgame.ForbiddenWordGame;
import org.forbiddenwordgame.Message.BaseMessage;
import org.forbiddenwordgame.Module.GameModule.GameModule;

import java.util.Objects;

// 커맨드 를 할 수 있게 해줍니다!
public class CommandManager implements CommandExecutor {

    private final GameModule gameModule;

    public CommandManager(ForbiddenWordGame plugin) {
        this.gameModule = new GameModule(plugin);
        Objects.requireNonNull(plugin.getCommand(BaseMessage.COMMAND_FORBIDDEN_WORD_GAME.getMessage())).setExecutor(this);
        Objects.requireNonNull(plugin.getCommand(BaseMessage.COMMAND_FORBIDDEN_WORD_GAME.getMessage())).setTabCompleter(new CommandSub());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] data) {
        if (data.length <= 1) return false;
        if (command.getName().equalsIgnoreCase(BaseMessage.COMMAND_FORBIDDEN_WORD_GAME.getMessage())) {
            BaseMessage byMessage = BaseMessage.getByMessage(data[0]);
            switch (byMessage) {
                case COMMAND_TEST -> gameModule.testGame((Player) sender);
                case COMMAND_RELOAD -> gameModule.reloadGame((Player) sender);
                case COMMAND_ROULETTE -> gameModule.roulette((Player) sender);
                case COMMAND_START -> gameModule.startGame((Player) sender);
                case COMMAND_STOP -> gameModule.stopGame((Player) sender);
            }
        }
        return true;
    }
}
