package womp.shellfishmod.item;

import java.util.function.Consumer;
import java.util.function.Supplier;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluid;
import org.jetbrains.annotations.Nullable;

public class ShellfishBucketItem extends MobBucketItem {
    private final Supplier<? extends EntityType<? extends Mob>> entityType;
    private final boolean hasTooltip;
    public ShellfishBucketItem(Supplier<? extends EntityType<? extends Mob>> entityType, Fluid fluid, Item item, boolean hasTooltip, Properties settings) {
        super(entityType, () -> fluid, () -> SoundEvents.BUCKET_EMPTY_FISH, settings);
        this.entityType = entityType;
        this.hasTooltip = hasTooltip;
    }

    @Override
    public void checkExtraContent(@Nullable LivingEntity user, Level world, ItemStack stack, BlockPos pos) {
        if (world instanceof ServerLevel) {
            this.spawn((ServerLevel)world, stack, pos);
            world.gameEvent(user, GameEvent.ENTITY_PLACE, pos);
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay displayComponent, Consumer<Component> textConsumer, TooltipFlag type) {
        super.appendHoverText(stack, context, displayComponent, textConsumer, type);
        if (hasTooltip && stack.has(DataComponents.BUCKET_ENTITY_DATA)) {
            assert stack.get(DataComponents.BUCKET_ENTITY_DATA) != null;
            textConsumer.accept((Component.translatable(entityType.get().getDescriptionId() + "." + stack.get(DataComponents.BUCKET_ENTITY_DATA).copyTag().getInt("Variant").get())).withStyle(ChatFormatting.GRAY, ChatFormatting.ITALIC));
        }
    }

    private void spawn(ServerLevel world, ItemStack stack, BlockPos pos) {
        Mob mobEntity = (Mob)this.entityType.get().create(world, EntityType.createDefaultStackConfig(world, stack, (LivingEntity)null), pos, EntitySpawnReason.BUCKET, true, false);
        if (mobEntity instanceof Bucketable) {
            Bucketable bucketable = (Bucketable)mobEntity;
            CustomData nbtComponent = (CustomData)stack.getOrDefault(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY);
            bucketable.loadFromBucketTag(nbtComponent.copyTag());
            bucketable.setFromBucket(true);
        }

        if (mobEntity != null) {
            world.addFreshEntityWithPassengers(mobEntity);
            mobEntity.playAmbientSound();
        }

    }

    public static ItemStack getEmptySuccessItem(ItemStack stack, Player player) {
        if (!player.getAbilities().instabuild) {
            return new ItemStack(Items.BUCKET);
        }
        return stack;
    }

    @Override
    protected void playEmptySound(@Nullable LivingEntity user, LevelAccessor world, BlockPos pos) {
        world.playSound(user, pos, SoundEvents.BUCKET_EMPTY_FISH, SoundSource.NEUTRAL, 1.0F, 1.0F);
    }
}