package womp.shellfishmod.mixin;

import java.util.function.Consumer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.datafixers.util.Pair;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.util.MultiNoiseUtil;
import net.minecraft.world.biome.source.util.VanillaBiomeParameters;
import net.minecraft.world.biome.source.util.MultiNoiseUtil.ParameterRange;
import womp.shellfishmod.registry.ShellfishWorldgen;

@Mixin(VanillaBiomeParameters.class)
public class AddMarshMixin {

    private static MultiNoiseUtil.ParameterRange temp = ParameterRange.of(-0.15f, 0.55f),
        humidity = ParameterRange.of(-1.0f, 1.0f),
        continentalness = ParameterRange.combine(ParameterRange.of(-0.11f, 0.03f), ParameterRange.of(0.3f, 1.0f)),
        continentalness2 = ParameterRange.combine(ParameterRange.of(-0.11f, 0.55f), ParameterRange.of(0.3f, 1.0f)),
        erosion = ParameterRange.of(0.55f, 1.0f);


    @Inject(method = "writeLowBiomes", at = @At("RETURN"))
    public void writeLowBiomes(Consumer<Pair<MultiNoiseUtil.NoiseHypercube, RegistryKey<Biome>>> parameters, MultiNoiseUtil.ParameterRange weirdness, CallbackInfo callback) {
        if (!FabricLoader.getInstance().isModLoaded("terrablender")) {
            this.writeBiomeParameters(parameters, temp, humidity, continentalness, erosion, weirdness, 0.0f, ShellfishWorldgen.MARSH);
        }
    }

    @Inject(method = "writeValleyBiomes", at = @At("RETURN"))
    public void writeValleyBiomes(Consumer<Pair<MultiNoiseUtil.NoiseHypercube, RegistryKey<Biome>>> parameters, MultiNoiseUtil.ParameterRange weirdness, CallbackInfo callback) {
        if (!FabricLoader.getInstance().isModLoaded("terrablender")) {
            this.writeBiomeParameters(parameters, temp, humidity, continentalness2, erosion, weirdness, 0.0f, ShellfishWorldgen.MARSH);
        }
    }

    private void writeBiomeParameters(Consumer<Pair<MultiNoiseUtil.NoiseHypercube, RegistryKey<Biome>>> parameters, MultiNoiseUtil.ParameterRange temperature, MultiNoiseUtil.ParameterRange humidity, MultiNoiseUtil.ParameterRange continentalness, MultiNoiseUtil.ParameterRange erosion, MultiNoiseUtil.ParameterRange weirdness, float offset, RegistryKey<Biome> biome) {
        parameters.accept(Pair.of(MultiNoiseUtil.createNoiseHypercube(temperature, humidity, continentalness, erosion, MultiNoiseUtil.ParameterRange.of(0.0f), weirdness, offset), biome));
        parameters.accept(Pair.of(MultiNoiseUtil.createNoiseHypercube(temperature, humidity, continentalness, erosion, MultiNoiseUtil.ParameterRange.of(1.0f), weirdness, offset), biome));
    }
}
