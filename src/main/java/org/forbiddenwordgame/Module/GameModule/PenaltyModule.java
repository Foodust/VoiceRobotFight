package org.forbiddenwordgame.Module.GameModule;

import org.bukkit.Material;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;
import org.forbiddenwordgame.Data.TickData;
import org.forbiddenwordgame.ForbiddenWordGame;
import org.forbiddenwordgame.Module.BaseModule.TaskModule;

import java.util.Random;

public class PenaltyModule {
    private final TaskModule taskModule;

    public PenaltyModule(ForbiddenWordGame plugin) {
        this.taskModule = new TaskModule(plugin);
    }

    public void penaltyPlayer(Player player) {
        int random = new Random().nextInt(10);
        switch (random) {
            case 0, 7, 8, 9 -> deathPenalty(player);
            case 1 -> hitPenalty(player);
            case 2 -> jumpPenalty(player);
            case 3 -> confusionPenalty(player);
            case 4 -> blindPenalty(player);
            case 5 -> poisonPenalty(player);
            case 6 -> anvilPenalty(player);
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

    public void anvilPenalty(Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 4, 99));
        FallingBlock fallingBlock = player.getWorld().spawnFallingBlock(player.getLocation().add(0, 20, 0), Material.ANVIL.createBlockData());
        fallingBlock.setHurtEntities(true);
        fallingBlock.setMaxDamage(4000);
        fallingBlock.setDropItem(false);
        fallingBlock.setCustomName("정의의 모루");
        fallingBlock.setCustomNameVisible(true);
        taskModule.runBukkitTaskLater(fallingBlock::remove, 5 * TickData.longTick);
    }
}
