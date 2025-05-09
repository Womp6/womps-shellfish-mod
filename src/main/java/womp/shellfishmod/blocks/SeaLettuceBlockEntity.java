package womp.shellfishmod.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import womp.shellfishmod.blocks.parents.ShellfishPlantBlockEntity3d;
import womp.shellfishmod.registry.ShellfishBlocks;

public class SeaLettuceBlockEntity extends ShellfishPlantBlockEntity3d {

    public boolean large;
    private long animationStartTime;

    public SeaLettuceBlockEntity(BlockPos pos, BlockState state) {
        super(ShellfishBlocks.SEA_LETTUCE_BLOCK_ENTITY.get(), pos, state);
        this.large = false;
        this.animationStartTime = System.currentTimeMillis();
    }

    public boolean isLarge() {
        return large;
    }

    public void setLarge(boolean large) {
        this.large = large;
        setChanged();

        if (level != null) {
            BlockState state = level.getBlockState(worldPosition);
            if (state.getBlock() instanceof SeaLettuceBlock) {
                level.setBlock(worldPosition, state.setValue(SeaLettuceBlock.LARGE, this.large), 2);
            }
        }

        if (level != null) {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 2);
        }
    }

    public long getAnimationStartTime() {
        return animationStartTime;
    }

    @Override
    public void loadAdditional(CompoundTag tag, HolderLookup.Provider wrapper) {
        super.loadAdditional(tag, wrapper);
        this.large = tag.getBooleanOr("large", false);
        this.animationStartTime = tag.getLongOr("AnimationStartTime", 0);

        if (level != null) {
            BlockState state = level.getBlockState(worldPosition);
            if (state.getBlock() instanceof SeaLettuceBlock) {
                level.setBlock(worldPosition, state.setValue(SeaLettuceBlock.LARGE, this.large), 2);
            }
        }
    }

    @Override
    public void saveAdditional(CompoundTag tag, HolderLookup.Provider wrapper) {
        super.saveAdditional(tag, wrapper);
        tag.putBoolean("large", this.large);
        tag.putLong("AnimationStartTime", animationStartTime);
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider wrapper) {
        return saveWithoutMetadata(wrapper);
    }
}
