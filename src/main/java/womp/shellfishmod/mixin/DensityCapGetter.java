package womp.shellfishmod.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.entity.SpawnGroup;

@Mixin(targets = "net.minecraft.world.SpawnDensityCapper$DensityCap")
public interface DensityCapGetter {

    @Accessor("spawnGroupsToDensity")
    public Object2IntMap<SpawnGroup> getSpawnGroupsToDensity();
}
