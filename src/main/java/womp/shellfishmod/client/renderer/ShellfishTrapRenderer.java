package womp.shellfishmod.client.renderer;

import net.minecraft.client.item.ItemModelManager;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.command.ModelCommandRenderer.CrumblingOverlayCommand;
import net.minecraft.client.render.item.ItemRenderState;
import net.minecraft.client.render.state.CameraRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemDisplayContext;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;
import womp.shellfishmod.blocks.parents.AbstractTrapBlockEntity;
import womp.shellfishmod.client.states.ShellfishTrapBlockEntityRenderState;
import womp.shellfishmod.util.config.ShellfishConfig;

public class ShellfishTrapRenderer<T extends AbstractTrapBlockEntity> implements BlockEntityRenderer<T, ShellfishTrapBlockEntityRenderState>{

    private final ItemModelManager itemModelManager;

    public ShellfishTrapRenderer(BlockEntityRendererFactory.Context context) {
        this.itemModelManager = context.itemModelManager();
    }

    @Override
    public void render(ShellfishTrapBlockEntityRenderState blockEntity, MatrixStack matrices, OrderedRenderCommandQueue queue,
            CameraRenderState cameraState) {
        
        if (ShellfishConfig.getShellfishGraphics() >= 1) {
            matrices.push();
            float bobbingOffset = 0.02f * (float) Math.sin((blockEntity.worldTime + blockEntity.tickDelta) / 8.0);
            matrices.translate(0.5f, 0.5f + bobbingOffset, 0.5f);
            matrices.scale(0.6f, 0.6f, 0.6f);
            float angle = (blockEntity.worldTime + blockEntity.tickDelta) % 360 * 2f;
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(angle));

            blockEntity.renderBait.render(matrices, queue, blockEntity.lightmapCoordinates, OverlayTexture.DEFAULT_UV, 0);
            matrices.pop();
        }
    }

    @Override
    public ShellfishTrapBlockEntityRenderState createRenderState() {
        return new ShellfishTrapBlockEntityRenderState();
    }

    @Override
    public void updateRenderState(T blockEntity, ShellfishTrapBlockEntityRenderState state, float tickProgress, Vec3d cameraPos,
            CrumblingOverlayCommand crumblingOverlay) {
        BlockEntityRenderer.super.updateRenderState(blockEntity, state, tickProgress, cameraPos, crumblingOverlay);
        state.tickDelta = tickProgress;
        state.renderBait = new ItemRenderState();
        itemModelManager.clearAndUpdate(state.renderBait, blockEntity.renderBait(), ItemDisplayContext.FIXED, blockEntity.getWorld(), null, 1);
        state.worldTime = blockEntity.getWorld().getTime();
    }
}
