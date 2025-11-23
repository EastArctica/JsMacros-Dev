package xyz.wagyourtail.jsmacros.client.mixin.events;

import net.minecraft.client.Minecraft;
import net.minecraft.client.MouseHandler;
import net.minecraft.client.input.MouseButtonInfo;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.wagyourtail.jsmacros.client.api.event.impl.EventKey;
import xyz.wagyourtail.jsmacros.client.api.event.impl.EventMouseScroll;

@Mixin(MouseHandler.class)
class MixinMouse {

    @Shadow
    @Final
    private Minecraft minecraft;

    @Inject(at = @At("HEAD"), method = "onButton", cancellable = true)
    private void onButton(long window, MouseButtonInfo mouseButtonInfo, int action, final CallbackInfo info) {
        if (window != minecraft.getWindow().handle()) {
            return;
        }
        if (mouseButtonInfo.button() == -1 || action == 2) {
            return;
        }
        if (EventKey.parse(mouseButtonInfo.button(), -1, action, mouseButtonInfo.modifiers())) {
            info.cancel();
        }
    }

    @Inject(at = @At("HEAD"), method = "onScroll", cancellable = true)
    private void onMouseScroll(long window, double horizontal, double vertical, CallbackInfo ci) {
        if (window != minecraft.getWindow().handle()) return;
        if (minecraft.getOverlay() != null || minecraft.screen != null || minecraft.player == null) return;
        if (vertical == 0.0 && horizontal == 0.0) return;
        EventMouseScroll event = new EventMouseScroll(horizontal, vertical);
        event.trigger();
        if (event.isCanceled()) ci.cancel();
    }

}
