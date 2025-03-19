package org.voicerobotfight.Module.GameModule;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;
import org.voicerobotfight.Data.TickData;
import org.voicerobotfight.VoiceRobotFight;
import org.voicerobotfight.Module.BaseModule.TaskModule;

import java.util.Random;

public class PenaltyModule {
    private final TaskModule taskModule;

    public PenaltyModule(VoiceRobotFight plugin) {
        this.taskModule = new TaskModule(plugin);
    }

    public void penaltyPlayer(Player player) {
        int random = new Random().nextInt(8);
        switch (random) {
            case 0 -> deathPenalty(player);
            case 1 -> hitPenalty(player);
            case 2 -> jumpPenalty(player);
            case 3 -> confusionPenalty(player);
            case 4 -> blindPenalty(player);
            case 5 -> poisonPenalty(player);
            case 6 -> witherPenalty(player);
            case 7 -> slowPenalty(player);
        }
    }

    public void deathPenalty(Player player) {
        player.damage(100);
    }

    public void hitPenalty(Player player) {
        for (int i = 0; i < 10; i++) {
            taskModule.runBukkitTaskLater(() -> {
                player.damage(1);
                player.setNoDamageTicks(0);
            }, i);
        }
    }

    public void jumpPenalty(Player player) {
        player.setVelocity(new Vector(0, 10, 0));
    }

    public void confusionPenalty(Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 10 * TickData.intTick, 5));
    }

    public void blindPenalty(Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 4 * TickData.intTick, 5));
    }

    public void poisonPenalty(Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 4 * TickData.intTick, 4));
    }

    public void witherPenalty(Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 4 * TickData.intTick, 5));
    }
    public void slowPenalty(Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 4 * TickData.intTick, 5));
    }
}
