package womp.shellfishmod.mixins;

import com.mojang.datafixers.util.Pair;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.OverworldBiomeBuilder;
import net.minecraftforge.fml.ModList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import womp.shellfishmod.registry.ShellfishWorldgen;

import java.util.function.Consumer;

@Mixin(OverworldBiomeBuilder.class)
public class AddMarshMixin {

    private static Climate.Parameter temp = Climate.Parameter.span(-0.15f, 0.55f),
            humidity = Climate.Parameter.span(-1.0f, 1.0f),
            continentalness = Climate.Parameter.span(Climate.Parameter.span(-0.11f, 0.03f), Climate.Parameter.span(0.3f, 1.0f)),
            continentalness2 = Climate.Parameter.span(Climate.Parameter.span(-0.11f, 0.55f), Climate.Parameter.span(0.3f, 1.0f)),
            erosion = Climate.Parameter.span(0.55f, 1.0f);


    @Inject(method = "addLowSlice", at = @At("RETURN"))
    public void writeLowBiomes(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> parameters, Climate.Parameter weirdness, CallbackInfo callback) {
        if (!ModList.get().isLoaded("terrablender")) {
            this.writeBiomeParameters(parameters, temp, humidity, continentalness, erosion, weirdness, 0.0f, ShellfishWorldgen.MARSH);
        }
    }

    @Inject(method = "addValleys", at = @At("RETURN"))
    public void writeValleyBiomes(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> parameters, Climate.Parameter weirdness, CallbackInfo callback) {
        if (!ModList.get().isLoaded("terrablender")) {
            this.writeBiomeParameters(parameters, temp, humidity, continentalness2, erosion, weirdness, 0.0f, ShellfishWorldgen.MARSH);
        }
    }

    private void writeBiomeParameters(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> parameters, Climate.Parameter temperature, Climate.Parameter humidity, Climate.Parameter continentalness, Climate.Parameter erosion, Climate.Parameter weirdness, float offset, ResourceKey<Biome> biome) {
        parameters.accept(Pair.of(Climate.parameters(temperature, humidity, continentalness, erosion, Climate.Parameter.point(0.0f), weirdness, offset), biome));
        parameters.accept(Pair.of(Climate.parameters(temperature, humidity, continentalness, erosion, Climate.Parameter.point(1.0f), weirdness, offset), biome));
    }
}
