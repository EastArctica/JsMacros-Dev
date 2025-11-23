package xyz.wagyourtail.jsmacros.fabric.client.mixins.access;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.MouseHandler;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.input.MouseButtonEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import xyz.wagyourtail.jsmacros.client.access.IScreenInternal;

@Mixin(MouseHandler.class)
public class MixinMouse {

    @WrapOperation(method = "onButton", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/Screen;mouseClicked(Lnet/minecraft/client/input/MouseButtonEvent;Z)Z"))
    private boolean onMouseClicked(Screen instance, MouseButtonEvent buttonEvent, boolean debounce, Operation<Boolean> original) {
        ((IScreenInternal) instance).jsmacros_mouseClicked(buttonEvent.x(), buttonEvent.y(), buttonEvent.button());
        return original.call(instance, buttonEvent, debounce);
    }

    @WrapOperation(method = "onButton", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/Screen;mouseReleased(Lnet/minecraft/client/input/MouseButtonEvent;)Z"))
    private boolean onMouseReleased(Screen instance, MouseButtonEvent buttonEvent, Operation<Boolean> original) {
        ((IScreenInternal) instance).jsmacros_mouseReleased(buttonEvent.x(), buttonEvent.y(), buttonEvent.button());
        return original.call(instance, buttonEvent);
    }

    @WrapOperation(method = "handleAccumulatedMovement", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/Screen;mouseDragged(Lnet/minecraft/client/input/MouseButtonEvent;DD)Z"))
    private boolean onMouseDragged(Screen instance, MouseButtonEvent buttonEvent, double dx, double dy, Operation<Boolean> original) {
        ((IScreenInternal) instance).jsmacros_mouseDragged(buttonEvent.x(), buttonEvent.y(), buttonEvent.button(), dx, dy);
        return original.call(instance, buttonEvent, dx, dy);
    }

    @WrapOperation(method = "onScroll", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/Screen;mouseScrolled(DDDD)Z"))
    private boolean onMouseScrolled(Screen instance, double x, double y, double dx, double dy, Operation<Boolean> original) {
        ((IScreenInternal) instance).jsmacros_mouseScrolled(x, y, dx, dy);
        return original.call(instance, x, y, dx, dy);
    }

}
