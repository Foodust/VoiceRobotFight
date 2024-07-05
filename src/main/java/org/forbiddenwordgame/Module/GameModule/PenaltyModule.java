package org.forbiddenwordgame.Module.GameModule;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;
import org.forbiddenwordgame.ForbiddenWordGame;

import java.util.Random;

public class PenaltyModule {
    public PenaltyModule(ForbiddenWordGame plugin) {

    }

    public void penaltyPlayer(Player player) {
        int random = new Random().nextInt(10);
        switch (random) {
            case 0 -> deathPenalty(player);
            case 1 -> hitPenalty(player);
            case 2 -> jumpPenalty(player);
            case 3 -> confusionPenalty(player);
            case 4 -> blindPenalty(player);
            case 5 -> poisonPenalty(player);
        }
    }

    public void deathPenalty(Player player) {
        player.damage(100);
    }

    public void hitPenalty(Player player) {
        for (int i = 0; i < player.getHealth(); i++) {
            player.damage(1);
            player.setNoDamageTicks(0);
        }
    }

    public void jumpPenalty(Player player) {
        player.setVelocity(new Vector(0, 10, 0));
    }

    public void confusionPenalty(Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 4, 1));
    }

    public void blindPenalty(Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 4, 5));
    }

    public void poisonPenalty(Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 4, 4));
    }
}
