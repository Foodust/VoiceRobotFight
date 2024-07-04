package org.forbiddenwordgame.Command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.forbiddenwordgame.Message.BaseMessage;

import java.util.*;
import java.util.stream.Collectors;

public class CommandSub implements TabCompleter {
    Set<String> mainSub = EnumSet.range(BaseMessage.COMMAND_START, BaseMessage.COMMAND_STOP).stream().map(BaseMessage::getMessage).collect(Collectors.toSet());

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> completions = new ArrayList<>();
        if (args.length == 1) {
            StringUtil.copyPartialMatches(args[0], mainSub, completions);
        }
        Collections.sort(completions);
        return completions;
    }
}
