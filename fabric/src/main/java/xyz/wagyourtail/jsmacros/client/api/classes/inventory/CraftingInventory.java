package xyz.wagyourtail.jsmacros.client.api.classes.inventory;

import net.minecraft.client.gui.screen.ingame.CraftingScreen;
import xyz.wagyourtail.jsmacros.client.api.helper.inventory.ItemStackHelper;

/**
 * @author Etheradon
 * @since 1.8.4
 */
public class CraftingInventory extends RecipeInventory<CraftingScreen> {

    protected CraftingInventory(CraftingScreen inventory) {
        super(inventory);
    }

    @Override
    public ItemStackHelper getOutput() {
        var handler = inventory.getScreenHandler();
        handler.getOutputSlot();
        return new ItemStackHelper(inventory.getScreenHandler().getOutputSlot().getStack());
    }

    /**
     * @param x the x position of the input from 0 to 2, going left to right
     * @param y the y position of the input from 0 to 2, going top to bottom
     * @return the input item at the given position.
     * @since 1.8.4
     */
    public ItemStackHelper getInput(int x, int y) {
        var handler = inventory.getScreenHandler();
        return new ItemStackHelper(handler.getInputSlots().get(x + y * 3).getStack());
    }

    @Override
    public int getCraftingWidth() {
        return inventory.getScreenHandler().getWidth();
    }

    @Override
    public int getCraftingHeight() {
        return inventory.getScreenHandler().getHeight();
    }

    @Override
    public int getCraftingSlotCount() {
        return getCraftingWidth() * getCraftingHeight();
    }

    @Override
    public String toString() {
        return String.format("CraftingInventory:{}");
    }

}
