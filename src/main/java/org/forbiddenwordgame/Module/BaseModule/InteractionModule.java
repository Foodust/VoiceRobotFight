package org.forbiddenwordgame.Module.BaseModule;

import org.bukkit.Location;
import org.bukkit.entity.Interaction;
import org.bukkit.entity.Player;
import org.mcdemonas.Data.ItemData;
import org.mcdemonas.McDemonas;

public class InteractionModule {

    public InteractionModule(McDemonas plugin) {
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
        ItemData.makeEntity.add(interaction);
        return interaction;
    }
}
