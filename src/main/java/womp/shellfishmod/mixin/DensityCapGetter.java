package womp.shellfishmod.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.world.entity.MobCategory;

@Mixin(targets = "net.minecraft.world.level.LocalMobCapCalculator$MobCounts")
public interface DensityCapGetter {

    @Accessor("counts")
    public Object2IntMap<MobCategory> getSpawnGroupsToDensity();
}
