package womp.shellfishmod.item;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluid;
import womp.shellfishmod.entity.MossBallEntity;
import womp.shellfishmod.entity.parents.ShellfishEntity;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Supplier;

public class ShellfishBucketItem extends BucketItem {

    private final Supplier<? extends EntityType<?>> entityType;
    private final boolean hasTooltip;
    public ShellfishBucketItem(Supplier<? extends EntityType<?>> entityType, Fluid fluid, Item item, boolean hasTooltip, Item.Properties settings) {
        super(fluid, settings);
        this.entityType = entityType;
        this.hasTooltip = hasTooltip;
    }

    @Override
    public void checkExtraContent(@Nullable Player player, Level world, ItemStack stack, BlockPos pos) {
        if (world instanceof ServerLevel) {
            this.spawnEntity((ServerLevel)world, stack, pos);
            world.gameEvent(player, GameEvent.ENTITY_PLACE, pos);
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag type) {
        super.appendHoverText(stack, context, tooltip, type);
        if (hasTooltip && stack.has(DataComponents.BUCKET_ENTITY_DATA)) {
            assert stack.get(DataComponents.BUCKET_ENTITY_DATA) != null;
            tooltip.add((Component.translatable(entityType.get().getDescriptionId() + "." + stack.get(DataComponents.BUCKET_ENTITY_DATA).copyTag().getInt("Variant"))).withStyle(ChatFormatting.GRAY, ChatFormatting.ITALIC));
        }
    }

    private void spawnEntity(ServerLevel world, ItemStack stack, BlockPos pos) {
        Entity entity = this.entityType.get().spawn(world, stack, null, pos, MobSpawnType.BUCKET, true, false);
        if (entity != null) {
            if (entity instanceof Entity) {
                Bucketable bucketable = (Bucketable)entity;
                CompoundTag nbt;
                if (stack.has(DataComponents.BUCKET_ENTITY_DATA)) nbt = stack.get(DataComponents.BUCKET_ENTITY_DATA).copyTag();
                else nbt = new CompoundTag();
                bucketable.loadFromBucketTag(nbt);
                bucketable.setFromBucket(true);
                if (!nbt.contains("Variant")) {
                    RandomSource random = RandomSource.create();
                    if (bucketable instanceof ShellfishEntity<?> shellfish) shellfish.setVariantNumerical(random.nextIntBetweenInclusive(0, shellfish.getMaxVariants() - 1));
                    if (bucketable instanceof MossBallEntity mossBall) mossBall.setVariant(MossBallEntity.Variant.byId(random.nextIntBetweenInclusive(0, 1)));
                }
            }
        }
    }

    public static ItemStack getEmptiedStack(ItemStack stack, Player player) {
        if (!player.getAbilities().instabuild) {
            return new ItemStack(Items.BUCKET);
        }
        return stack;
    }
}
