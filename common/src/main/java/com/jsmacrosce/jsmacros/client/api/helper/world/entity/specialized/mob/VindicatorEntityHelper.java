package com.jsmacrosce.jsmacros.client.api.helper.world.entity.specialized.mob;

//? if >=1.21.11 {
import net.minecraft.world.entity.monster.illager.Vindicator;
//? } else {
/*import net.minecraft.world.entity.monster.Vindicator;
*///? }

/**
 * @author Etheradon
 * @since 1.8.4
 */
@SuppressWarnings("unused")
public class VindicatorEntityHelper extends IllagerEntityHelper<Vindicator> {

    public VindicatorEntityHelper(Vindicator base) {
        super(base);
    }

    /**
     * @return {@code true} if this vindicator is johnny, {@code false} otherwise.
     * @since 1.8.4
     */
    public boolean isJohnny() {
        return base.hasCustomName() && base.getCustomName().getString().equals("Johnny");
    }

}
