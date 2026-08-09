package womp.shellfishmod.item;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.EntityBucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.event.GameEvent;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.component.type.TooltipDisplayComponent;
import net.minecraft.entity.Bucketable;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import java.util.function.Consumer;
import java.util.function.Supplier;

import org.jetbrains.annotations.Nullable;

public class ShellfishBucketItem extends EntityBucketItem {
    private final Supplier<EntityType<? extends MobEntity>> entityType;
    private final boolean hasTooltip;
    public ShellfishBucketItem(Supplier<EntityType<? extends MobEntity>> entityType, Fluid fluid, Item item, boolean hasTooltip, Settings settings) {
        super(entityType.get(), fluid, SoundEvents.ITEM_BUCKET_EMPTY_FISH, settings);
        this.entityType = entityType;
        this.hasTooltip = hasTooltip;
    }
    
    @Override
    public void onEmptied(@Nullable LivingEntity user, World world, ItemStack stack, BlockPos pos) {
        if (world instanceof ServerWorld) {
            this.spawnEntity((ServerWorld)world, stack, pos);
            world.emitGameEvent(user, GameEvent.ENTITY_PLACE, pos);
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, TooltipDisplayComponent displayComponent, Consumer<Text> textConsumer, TooltipType type) {
        super.appendTooltip(stack, context, displayComponent, textConsumer, type);
        if (hasTooltip && stack.contains(DataComponentTypes.BUCKET_ENTITY_DATA)) {
            assert stack.get(DataComponentTypes.BUCKET_ENTITY_DATA) != null;
            textConsumer.accept((Text.translatable(entityType.get().getTranslationKey() + "." + stack.get(DataComponentTypes.BUCKET_ENTITY_DATA).copyNbt().getInt("Variant").get())).formatted(Formatting.GRAY, Formatting.ITALIC));
        }
    }

    private void spawnEntity(ServerWorld world, ItemStack stack, BlockPos pos) {
        MobEntity mobEntity = (MobEntity)this.entityType.get().create(world, EntityType.copier(world, stack, (LivingEntity)null), pos, SpawnReason.BUCKET, true, false);
        if (mobEntity instanceof Bucketable) {
            Bucketable bucketable = (Bucketable)mobEntity;
            NbtComponent nbtComponent = (NbtComponent)stack.getOrDefault(DataComponentTypes.BUCKET_ENTITY_DATA, NbtComponent.DEFAULT);
            bucketable.copyDataFromNbt(nbtComponent.copyNbt());
            bucketable.setFromBucket(true);
        }

        if (mobEntity != null) {
            world.spawnEntityAndPassengers(mobEntity);
            mobEntity.playAmbientSound();
        }

    }

    public static ItemStack getEmptiedStack(ItemStack stack, PlayerEntity player) {
        if (!player.getAbilities().creativeMode) {
            return new ItemStack(Items.BUCKET);
        }
        return stack;
    }

    @Override
    protected void playEmptyingSound(@Nullable LivingEntity user, WorldAccess world, BlockPos pos) {
        world.playSound(user, pos, SoundEvents.ITEM_BUCKET_EMPTY_FISH, SoundCategory.NEUTRAL, 1.0F, 1.0F);
    }
}