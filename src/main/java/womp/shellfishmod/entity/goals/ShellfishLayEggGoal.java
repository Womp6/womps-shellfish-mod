package womp.shellfishmod.entity.goals;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.ai.goal.MoveToTargetPosGoal;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.event.GameEvent;
import womp.shellfishmod.blocks.parents.EggsBlock;
import womp.shellfishmod.entity.parents.EggLaying;

public class ShellfishLayEggGoal extends MoveToTargetPosGoal {

    protected final EggLaying shellfish;
    private final SoundEvent laySound;
    private final Block eggBlock;
    private int i = 0;

    public ShellfishLayEggGoal(EggLaying shellfish, double speed, SoundEvent laySound, Block eggBlock) {
        super(shellfish.getEntity(), speed, 16);
        this.shellfish = shellfish;
        this.laySound = laySound;
        this.eggBlock = eggBlock;
    }

    @Override
    public boolean canStart() {
        if (this.shellfish.hasEgg()) {
            return super.canStart();
        }
        return false;
    }

    @Override
    public boolean shouldContinue() {
        return super.shouldContinue() && this.shellfish.hasEgg();
    }

    @Override
    public void tick() {
        super.tick();
        BlockPos blockPos = this.shellfish.getEntity().getBlockPos();
        if (this.shellfish.getEntity().isTouchingWater() && this.hasReached() && i == 0) {
                World world = this.shellfish.getEntity().getWorld();
                world.playSound(null, blockPos, laySound, SoundCategory.BLOCKS, 0.3f, 0.9f + world.random.nextFloat() * 0.2f);
                BlockPos blockPos2 = this.targetPos.up();
                BlockState blockState;
                if (eggBlock instanceof EggsBlock) blockState = (BlockState)eggBlock.getDefaultState()
                    .with(EggsBlock.VARIANT1, this.shellfish.getEntity().getVariant().getIndex() + 1)
                    .with(EggsBlock.VARIANT2, shellfish.getPartnerVariant() + 1);
                else blockState = (BlockState)eggBlock.getDefaultState();
                this.shellfish.setPartnerVariant(-1);  // Reset
                world.setBlockState(blockPos2, blockState, Block.NOTIFY_ALL);
                world.emitGameEvent(GameEvent.BLOCK_PLACE, blockPos2, GameEvent.Emitter.of(this.shellfish.getEntity(), blockState));
                this.shellfish.setHasEgg(false);
                this.shellfish.getEntity().setLoveTicks(600);
                i++;
        }
    }

    @Override
    protected boolean isTargetPos(WorldView world, BlockPos pos) {
        FluidState blockState = world.getFluidState(pos);
        FluidState fluidUp = world.getFluidState(pos.up());
        BlockState blockup = world.getBlockState(pos.up());
        return blockState.isEmpty() && fluidUp.isOf(Fluids.WATER) && !(blockup.getBlock() instanceof EggsBlock);
    }
}
