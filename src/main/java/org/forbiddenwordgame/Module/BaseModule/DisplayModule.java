package org.forbiddenwordgame.Module.BaseModule;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Transformation;

public class DisplayModule {
    public ItemDisplay makeItemDisplay(Player player, Location location, Material material , Double size){
        ItemDisplay itemDisplay = player.getWorld().spawn(location, ItemDisplay.class);
        itemDisplay.setItemStack(new ItemStack(material));
        Transformation transformation = itemDisplay.getTransformation();
        transformation.getScale().set(size);
        itemDisplay.setTransformation(transformation);
        return itemDisplay;
    }
    public ItemDisplay makeItemDisplay(Player player, Location location, ItemStack itemStack, Double size, Display.Billboard billboard){
        ItemDisplay itemDisplay = player.getWorld().spawn(location, ItemDisplay.class);
        itemDisplay.setItemStack(itemStack);
        itemDisplay.setBillboard(billboard);
        Transformation transformation = itemDisplay.getTransformation();
        transformation.getScale().set(size);
        itemDisplay.setTransformation(transformation);
        return itemDisplay;
    }
    public ItemDisplay makeItemDisplay(Player player, Location location,ItemStack itemStack, Double size){
        ItemDisplay itemDisplay = player.getWorld().spawn(location, ItemDisplay.class);
        itemDisplay.setItemStack(itemStack);
        Transformation transformation = itemDisplay.getTransformation();
        transformation.getScale().set(size);
        itemDisplay.setTransformation(transformation);
        return itemDisplay;
    }
    public BlockDisplay makeBlockDisplay(Player player , Location location, Material material,Double size){
        BlockDisplay blockDisplay = player.getWorld().spawn(location, BlockDisplay.class);
        blockDisplay.setBlock(material.createBlockData());
        Transformation transformation = blockDisplay.getTransformation();
        transformation.getScale().set(size);
        blockDisplay.setTransformation(transformation);
        return blockDisplay;
    }
    public TextDisplay makeTextDisplay(Player player , Location location, String text, Double size){
        TextDisplay textDisplay = player.getWorld().spawn(location, TextDisplay.class);
        textDisplay.setText(text);
        Transformation transformation = textDisplay.getTransformation();
        transformation.getScale().set(size);
        textDisplay.setTransformation(transformation);
        return textDisplay;
    }
}
