package womp.shellfishmod.item;

import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.MobBucketItem;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluid;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class ShellfishBucketItem extends MobBucketItem {

    public ShellfishBucketItem(Supplier<? extends EntityType<? extends Mob>> shellfish, Fluid water, Item.Properties builder) {
        super(shellfish, () -> water, () -> SoundEvents.BUCKET_EMPTY_FISH, builder.stacksTo(1));
    }

    @Override
    public void checkExtraContent(@Nullable LivingEntity player, Level level, ItemStack stack, BlockPos pos) {
        if (level instanceof ServerLevel) {
            this.spawnShellfish((ServerLevel)level, stack, pos);
            level.gameEvent(player, GameEvent.ENTITY_PLACE, pos);
        }
    }

    private void spawnShellfish(ServerLevel serverLevel, ItemStack stack, BlockPos pos) {
        if (this.getFishType().spawn(serverLevel, stack, null, pos, EntitySpawnReason.BUCKET, true, false) instanceof Bucketable bucketable) {
            CustomData customdata = stack.getOrDefault(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY);
            bucketable.loadFromBucketTag(customdata.copyTag());
            bucketable.setFromBucket(true);
        }
    }
}
