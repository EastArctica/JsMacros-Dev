package xyz.wagyourtail.jsmacros.client.mixin.events;

import net.minecraft.client.KeyboardHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.input.KeyEvent;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.wagyourtail.jsmacros.client.api.event.impl.EventKey;

@Mixin(KeyboardHandler.class)
class MixinKeyboard {

    @Shadow
    @Final
    private Minecraft minecraft;

    @Inject(at = @At("HEAD"), method = "keyPress", cancellable = true)
    private void keyPress(long window, int action, KeyEvent keyEvent, CallbackInfo info) {
        if (window != minecraft.getWindow().handle()) {
            return;
        }
        if (keyEvent.key() == -1 || action == 2) {
            return;
        }
        if (EventKey.parse(keyEvent.key(), keyEvent.scancode(), action, keyEvent.modifiers())) {
            info.cancel();
        }
    }

}
