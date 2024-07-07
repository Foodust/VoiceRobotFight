package org.forbiddenwordgame.Message;

import lombok.Getter;
import org.bukkit.ChatColor;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

@Getter
public enum BaseMessage {

    // prefix
    PREFIX(""),
    PREFIX_C("<gradient:blue:green><bold>[금칙어게임]</bold></gradient> "),

    // command
    COMMAND_FORBIDDEN_WORD_GAME("금칙어게임"),
    COMMAND_START("시작"),
    COMMAND_TEST("테스트"),
    COMMAND_ROULETTE("룰렛"),
    COMMAND_RELOAD("리로드"),
    COMMAND_RESULT("결과"),
    COMMAND_STOP("종료"),

    // 기본
    DEFAULT("기본"),

    // Error

    ERROR("에러"),
    ERROR_COMMAND("<dark_red>잘못된 명령어입니다.</dark_red>"),

    ERROR_GAME_IS_START("<dark_red>게임이 이미 시작 되었습니다..</dark_red>"),
    ERROR_GAME_IS_NOT_START("<dark_red>게임이 시작 되지 않았습니다.</dark_red>"),
    ;

    private final String message;

    BaseMessage(String message) {
        this.message = message;
    }

    private static final Map<String, BaseMessage> commandInfo = new HashMap<>();

    static {
        for (BaseMessage baseMessage : EnumSet.range(COMMAND_FORBIDDEN_WORD_GAME, COMMAND_STOP)) {
            commandInfo.put(baseMessage.message, baseMessage);
        }
    }

    public static BaseMessage getByMessage(String message) {
        return commandInfo.getOrDefault(message,ERROR);
    }
}
