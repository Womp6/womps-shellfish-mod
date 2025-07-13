package womp.shellfishmod.item;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
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
    public void appendHoverText(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag context) {
        super.appendHoverText(stack, world, tooltip, context);
        if (hasTooltip && stack.hasTag()) {
            assert stack.getTag() != null;
            tooltip.add((Component.translatable(entityType.get().getDescriptionId() + "." + stack.getTag().getInt("Variant"))).withStyle(ChatFormatting.GRAY, ChatFormatting.ITALIC));
        }
    }

    private void spawnEntity(ServerLevel world, ItemStack stack, BlockPos pos) {
        Entity entity = this.entityType.get().spawn(world, stack, null, pos, MobSpawnType.BUCKET, true, false);
        if (entity != null) {
            if (entity instanceof Entity) {
                Bucketable bucketable = (Bucketable)entity;
                bucketable.loadFromBucketTag(stack.getOrCreateTag());
                bucketable.setFromBucket(true);
                if (!stack.getOrCreateTag().contains("Variant")) {
                    RandomSource random = RandomSource.create();
                    if (bucketable instanceof ShellfishEntity shellfish) shellfish.setVariant(random.nextIntBetweenInclusive(0, shellfish.getMaxVariants() - 1));
                    if (bucketable instanceof MossBallEntity mossBall) mossBall.setSmall(random.nextBoolean());
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
