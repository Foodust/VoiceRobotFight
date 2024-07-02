package org.forbiddenwordgame.Module.GameModule;

import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;
import edu.cmu.sphinx.api.Microphone;
import lombok.SneakyThrows;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.forbiddenwordgame.ForbiddenWordGame;
import org.forbiddenwordgame.Module.BaseModule.MessageModule;
import org.forbiddenwordgame.Module.BaseModule.TaskModule;

import java.io.IOException;
import java.util.List;

public class STTModule {
    private final MessageModule messageModule;
    private final TaskModule taskModule;
    private final LiveSpeechRecognizer recognizer;
    @SneakyThrows
    public STTModule(ForbiddenWordGame plugin) {
        this.messageModule = new MessageModule(plugin);
        this.taskModule = new TaskModule(plugin);
        // Configuration 설정
        Configuration configuration = new Configuration();
        // 한국어 음성 인식을 위한 리소스 경로 설정
        configuration.setAcousticModelPath("resource:/edu/cmu/sphinx/models/ko/ko");
        configuration.setDictionaryPath("resource:/edu/cmu/sphinx/models/ko/ko.dict");
        configuration.setLanguageModelPath("resource:/edu/cmu/sphinx/models/ko/ko.lm.bin");
        recognizer = new LiveSpeechRecognizer(configuration);
    }

    public Boolean isForbiddenWord(String word) {
        recognizer.startRecognition(true);
        // 음성 인식 결과 가져오기
        String result = recognizer.getResult().getHypothesis();
        messageModule.logInfo("탐지 : " + result);

        // 인식된 텍스트를 기반으로 원하는 이벤트 처리 로직 추가
        if (result.contains(word)) {
            messageModule.logInfo("check");
            return true;
        }
        return false;
    }

}
