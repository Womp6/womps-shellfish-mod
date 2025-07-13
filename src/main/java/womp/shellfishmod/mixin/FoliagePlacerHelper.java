package womp.shellfishmod.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import com.mojang.serialization.Codec;

import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.foliage.FoliagePlacerType;

// Created with help from Kaupenjoe
@Mixin(FoliagePlacerType.class)
public interface FoliagePlacerHelper {
    @Invoker
    public static <P extends FoliagePlacer> FoliagePlacerType<P> callRegister(String id, Codec<P> codec) {
        throw new IllegalStateException();
    }
}