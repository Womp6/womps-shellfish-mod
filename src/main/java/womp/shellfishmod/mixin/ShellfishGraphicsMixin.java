package womp.shellfishmod.mixin;

import java.util.Arrays;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.Options;
import net.minecraft.client.gui.screens.options.AccessibilityOptionsScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import womp.shellfishmod.util.config.ShellfishGameOptions;

@Mixin(AccessibilityOptionsScreen.class)
public class ShellfishGraphicsMixin {

    @Inject(
        method = "options(Lnet/minecraft/client/Options;)[Lnet/minecraft/client/OptionInstance;",
        at = @At(value = "RETURN"),
        cancellable = true
    )
    private static void injectShellfishGraphicsMode(Options gameOptions, CallbackInfoReturnable<OptionInstance<?>[]> cir) {
        OptionInstance<?>[] originalOptions = cir.getReturnValue();
        OptionInstance<?>[] newOptions = Arrays.copyOf(originalOptions, originalOptions.length + 1);

        newOptions[newOptions.length - 1] = ShellfishGameOptions.getShellfishGraphicsMode();
        
        cir.setReturnValue(newOptions);
    }
}
