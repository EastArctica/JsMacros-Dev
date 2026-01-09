package xyz.wagyourtail.jsmacros.client.api.helper.world.entity.specialized.passive;

import net.minecraft.world.entity.animal.Pig;
import xyz.wagyourtail.doclet.DocletCategory;

/**
 * @author Etheradon
 * @since 1.8.4
 */
@DocletCategory("Entity Helpers")
@SuppressWarnings("unused")
public class PigEntityHelper extends AnimalEntityHelper<Pig> {

    public PigEntityHelper(Pig base) {
        super(base);
    }

    /**
     * @return {@code true} if this pig is saddled, {@code false} otherwise.
     * @since 1.8.4
     */
    public boolean isSaddled() {
        return base.isSaddled();
    }

}
