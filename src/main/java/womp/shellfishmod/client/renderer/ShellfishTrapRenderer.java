package womp.shellfishmod.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer.CrumblingOverlay;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.phys.Vec3;
import womp.shellfishmod.blocks.parents.AbstractTrapBlockEntity;
import womp.shellfishmod.client.states.ShellfishTrapBlockEntityRenderState;
import womp.shellfishmod.util.config.ShellfishConfig;

public class ShellfishTrapRenderer<T extends AbstractTrapBlockEntity> implements BlockEntityRenderer<T, ShellfishTrapBlockEntityRenderState>{

    private final ItemModelResolver itemModelManager;

    public ShellfishTrapRenderer(BlockEntityRendererProvider.Context context) {
        this.itemModelManager = context.itemModelResolver();
    }

    @Override
    public void submit(ShellfishTrapBlockEntityRenderState blockEntity, PoseStack matrices, SubmitNodeCollector queue,
            CameraRenderState cameraState) {
        
        if (ShellfishConfig.getShellfishGraphics() >= 1) {
            matrices.pushPose();
            float bobbingOffset = 0.02f * (float) Math.sin((blockEntity.worldTime + blockEntity.tickDelta) / 8.0);
            matrices.translate(0.5f, 0.5f + bobbingOffset, 0.5f);
            matrices.scale(0.6f, 0.6f, 0.6f);
            float angle = (blockEntity.worldTime + blockEntity.tickDelta) % 360 * 2f;
            matrices.mulPose(Axis.YP.rotationDegrees(angle));

            blockEntity.renderBait.submit(matrices, queue, blockEntity.lightCoords, OverlayTexture.NO_OVERLAY, 0);
            matrices.popPose();
        }
    }

    @Override
    public ShellfishTrapBlockEntityRenderState createRenderState() {
        return new ShellfishTrapBlockEntityRenderState();
    }

    @Override
    public void extractRenderState(T blockEntity, ShellfishTrapBlockEntityRenderState state, float tickProgress, Vec3 cameraPos,
            CrumblingOverlay crumblingOverlay) {
        BlockEntityRenderer.super.extractRenderState(blockEntity, state, tickProgress, cameraPos, crumblingOverlay);
        state.tickDelta = tickProgress;
        state.renderBait = new ItemStackRenderState();
        itemModelManager.updateForTopItem(state.renderBait, blockEntity.renderBait(), ItemDisplayContext.FIXED, blockEntity.getLevel(), null, 1);
        state.worldTime = blockEntity.getLevel().getGameTime();
    }
}
