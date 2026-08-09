package womp.shellfishmod.entity.goals;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import womp.shellfishmod.blocks.parents.EggsBlock;
import womp.shellfishmod.entity.parents.EggLaying;

public class ShellfishLayEggGoal extends MoveToBlockGoal {

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
    public boolean canUse() {
        if (this.shellfish.hasEgg()) {
            return super.canUse();
        }
        return false;
    }

    @Override
    public boolean canContinueToUse() {
        return super.canContinueToUse() && this.shellfish.hasEgg();
    }

    @Override
    public void tick() {
        super.tick();
        BlockPos blockPos = this.shellfish.getEntity().blockPosition();
        if (this.shellfish.getEntity().isInWater() && this.isReachedTarget() && i == 0) {
                Level world = this.shellfish.getEntity().level();
                world.playSound(null, blockPos, laySound, SoundSource.BLOCKS, 0.3f, 0.9f + world.getRandom().nextFloat() * 0.2f);
                BlockPos blockPos2 = this.blockPos.above();
                BlockState blockState;
                if (eggBlock instanceof EggsBlock) blockState = (BlockState)eggBlock.defaultBlockState()
                    .setValue(EggsBlock.VARIANT1, this.shellfish.getEntity().getVariant().getIndex() + 1)
                    .setValue(EggsBlock.VARIANT2, shellfish.getPartnerVariant() + 1);
                else blockState = (BlockState)eggBlock.defaultBlockState();
                this.shellfish.setPartnerVariant(-1);  // Reset
                world.setBlock(blockPos2, blockState, Block.UPDATE_ALL);
                world.gameEvent(GameEvent.BLOCK_PLACE, blockPos2, GameEvent.Context.of(this.shellfish.getEntity(), blockState));
                this.shellfish.setHasEgg(false);
                this.shellfish.getEntity().setInLoveTime(600);
                i++;
        }
    }

    @Override
    protected boolean isValidTarget(LevelReader world, BlockPos pos) {
        FluidState blockState = world.getFluidState(pos);
        FluidState fluidUp = world.getFluidState(pos.above());
        BlockState blockup = world.getBlockState(pos.above());
        return blockState.isEmpty() && fluidUp.is(Fluids.WATER) && !(blockup.getBlock() instanceof EggsBlock);
    }
}
