package xyz.wagyourtail.jsmacros.client.mixin.access;

import net.minecraft.client.gui.screens.inventory.AbstractMountInventoryScreen;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import xyz.wagyourtail.jsmacros.client.access.IHorseScreen;

@Mixin(AbstractMountInventoryScreen.class)
public class MixinAbstractMountInventoryScreen {
    @Shadow
    protected LivingEntity mount;

    @Override
    public Entity jsmacros_getEntity() {
        return mount;
    }

}
