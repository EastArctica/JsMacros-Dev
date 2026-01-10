package xyz.wagyourtail.jsmacros.client.api.event.impl.player;

import xyz.wagyourtail.doclet.DocletCategory;
import xyz.wagyourtail.doclet.DocletReplaceReturn;
import xyz.wagyourtail.jsmacros.client.JsMacrosClient;
import xyz.wagyourtail.jsmacros.client.api.helper.world.BlockDataHelper;
import xyz.wagyourtail.jsmacros.core.event.BaseEvent;
import xyz.wagyourtail.jsmacros.core.event.Event;

@DocletCategory("Inputs/Interactions")
@Event("AttackBlock")
public class EventAttackBlock extends BaseEvent {
    public final BlockDataHelper block;
    @DocletReplaceReturn("Side")
    public final int side;

    public EventAttackBlock(BlockDataHelper block, int side) {
        super(JsMacrosClient.clientCore);
        this.block = block;
        this.side = side;
    }

    @Override
    public String toString() {
        return String.format("%s:{\"block\": %s}", this.getEventName(), block);
    }

}
