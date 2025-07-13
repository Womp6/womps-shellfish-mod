package womp.shellfishmod.mixins;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.world.entity.MobCategory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(targets = "net.minecraft.world.level.LocalMobCapCalculator$MobCounts")
public interface DensityCapGetter {

    @Accessor("counts")
    public Object2IntMap<MobCategory> getSpawnGroupsToDensity();
}

