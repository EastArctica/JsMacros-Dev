package com.jsmacrosce.jsmacros.client.api.helper.world.entity.specialized.passive;

//? if >=1.21.11 {
/*import net.minecraft.world.entity.animal.polarbear.PolarBear;
*///? } else {
import net.minecraft.world.entity.animal.PolarBear;
//?}

/**
 * @author Etheradon
 * @since 1.8.4
 */
@SuppressWarnings("unused")
public class PolarBearEntityHelper extends AnimalEntityHelper<PolarBear> {

    public PolarBearEntityHelper(PolarBear base) {
        super(base);
    }

    /**
     * @return {@code true} if the polar bear is standing up to attack, {@code false} otherwise.
     * @since 1.8.4
     */
    public boolean isAttacking() {
        return base.isStanding();
    }

}
