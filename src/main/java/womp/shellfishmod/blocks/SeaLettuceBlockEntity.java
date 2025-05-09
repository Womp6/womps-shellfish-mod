package womp.shellfishmod.blocks;

import org.jetbrains.annotations.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.math.BlockPos;
import womp.shellfishmod.blocks.parents.ShellfishPlantBlockEntity3d;
import womp.shellfishmod.registry.ShellfishBlocks;

public class SeaLettuceBlockEntity extends ShellfishPlantBlockEntity3d {
    
    public boolean large;
    private long animationStartTime;

    public SeaLettuceBlockEntity(BlockPos pos, BlockState state) {
        super(ShellfishBlocks.SEA_LETTUCE_BLOCK_ENTITY, pos, state);
        this.large = false;
        this.animationStartTime = System.currentTimeMillis();
    }

    public boolean isLarge() {
        return large;
    }

    public void setLarge(boolean large) {
        this.large = large;
        markDirty();

        if (world != null) {
            BlockState state = world.getBlockState(pos);
            if (state.getBlock() instanceof SeaLettuceBlock) {
                world.setBlockState(pos, state.with(SeaLettuceBlock.LARGE, this.large), Block.NOTIFY_LISTENERS);
            }
        }

        if (world != null) {
            world.updateListeners(pos, getCachedState(), getCachedState(), Block.NOTIFY_LISTENERS);
        }
    }

    public long getAnimationStartTime() {
        return animationStartTime;
    }

    @Override
    public void readNbt(NbtCompound tag, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(tag, registryLookup);
        this.large = tag.getBoolean("large", false);
        this.animationStartTime = tag.getLong("AnimationStartTime", 0);

        if (world != null) {
            BlockState state = world.getBlockState(pos);
            if (state.getBlock() instanceof SeaLettuceBlock) {
                world.setBlockState(pos, state.with(SeaLettuceBlock.LARGE, this.large), Block.NOTIFY_LISTENERS);
            }
        }
    }

    @Override
    public void writeNbt(NbtCompound tag, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(tag, registryLookup);
        tag.putBoolean("large", this.large);
        tag.putLong("AnimationStartTime", animationStartTime);
    }

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registryLookup) {
        return createNbt(registryLookup);
    }
}