package org.forbiddenwordgame.Data.Info;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlayerInfo {
    @Builder.Default
    private List<String> words = new ArrayList<>();
    @Builder.Default
    private Integer count = 0;
    @Builder.Default
    private Integer deathCount = 0;
}
