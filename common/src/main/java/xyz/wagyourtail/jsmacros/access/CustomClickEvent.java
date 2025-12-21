package xyz.wagyourtail.jsmacros.access;

import net.minecraft.network.chat.ClickEvent;
import org.jetbrains.annotations.NotNull;

public record CustomClickEvent(Runnable event) implements ClickEvent {

    @NotNull
    @Override
    public Action action() {
        return Action.CUSTOM;
    }
}
