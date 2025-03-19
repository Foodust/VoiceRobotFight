package org.voicerobotfight.Module.BaseModule;

import org.bukkit.Location;
import org.bukkit.entity.Interaction;
import org.bukkit.entity.Player;
import org.voicerobotfight.VoiceRobotFight;

public class InteractionModule {

    public InteractionModule(VoiceRobotFight plugin) {
    }

    public Interaction makeInteraction(Player player, Location location, Float height, Float width, String title) {
        Interaction interaction = player.getWorld().spawn(location, Interaction.class);
        interaction.setInteractionHeight(height);
        interaction.setInteractionWidth(width);
        interaction.setCustomName(title);
        interaction.setCustomNameVisible(false);
        interaction.setGravity(false);
        interaction.setInvulnerable(true);
        interaction.setResponsive(true);
        return interaction;
    }
}
