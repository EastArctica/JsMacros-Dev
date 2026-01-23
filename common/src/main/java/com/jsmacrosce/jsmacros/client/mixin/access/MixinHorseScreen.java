package com.jsmacrosce.jsmacros.client.mixin.access;

import net.minecraft.client.gui.screens.inventory.HorseInventoryScreen;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import com.jsmacrosce.jsmacros.client.access.IHorseScreen;

//? if >=1.21.11 {
import net.minecraft.world.entity.animal.equine.AbstractHorse;
//? } else {
/*import net.minecraft.world.entity.animal.horse.AbstractHorse;
*///? }

@Mixin(HorseInventoryScreen.class)
public class MixinHorseScreen implements IHorseScreen {
    @Shadow
    @Final
    private AbstractHorse horse;

    @Override
    public Entity jsmacros_getEntity() {
        return horse;
    }

}
