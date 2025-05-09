package womp.shellfishmod.mixin;

import java.util.Arrays;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.client.gui.screen.option.AccessibilityOptionsScreen;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.SimpleOption;
import womp.shellfishmod.util.config.ShellfishGameOptions;

@Mixin(AccessibilityOptionsScreen.class)
public class ShellfishGraphicsMixin {

    @Inject(
        method = "getOptions(Lnet/minecraft/client/option/GameOptions;)[Lnet/minecraft/client/option/SimpleOption;",
        at = @At(value = "RETURN"),
        cancellable = true
    )
    private static void injectShellfishGraphicsMode(GameOptions gameOptions, CallbackInfoReturnable<SimpleOption<?>[]> cir) {
        SimpleOption<?>[] originalOptions = cir.getReturnValue();
        SimpleOption<?>[] newOptions = Arrays.copyOf(originalOptions, originalOptions.length + 1);

        newOptions[newOptions.length - 1] = ShellfishGameOptions.getShellfishGraphicsMode();
        
        cir.setReturnValue(newOptions);
    }
}
