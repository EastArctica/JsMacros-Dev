package xyz.wagyourtail.jsmacros.client.api.event.impl;

import xyz.wagyourtail.doclet.DocletCategory;
import xyz.wagyourtail.jsmacros.client.JsMacrosClient;
import xyz.wagyourtail.jsmacros.core.event.BaseEvent;
import xyz.wagyourtail.jsmacros.core.event.Event;

/**
 * @author Etheradon
 * @since 1.8.4
 */
@DocletCategory("System/Lifecycle")
@Event(value = "QuitGame")
public class EventQuitGame extends BaseEvent {

    public EventQuitGame() {
        super(JsMacrosClient.clientCore);
    }

    @Override
    public String toString() {
        return String.format("%s:{}", this.getEventName());
    }

}
