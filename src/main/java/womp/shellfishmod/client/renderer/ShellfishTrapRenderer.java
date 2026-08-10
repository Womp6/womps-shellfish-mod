package womp.shellfishmod.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.phys.Vec3;
import womp.shellfishmod.blocks.parents.AbstractTrapBlockEntity;
import womp.shellfishmod.client.states.ShellfishTrapBlockEntityRenderState;
import womp.shellfishmod.util.config.ShellfishConfig;

public class ShellfishTrapRenderer implements BlockEntityRenderer<AbstractTrapBlockEntity, ShellfishTrapBlockEntityRenderState> {

    private final ItemModelResolver itemModelManager;

    public ShellfishTrapRenderer(BlockEntityRendererProvider.Context context) {
        this.itemModelManager = context.itemModelResolver();
    }

    @Override
    public void submit(ShellfishTrapBlockEntityRenderState blockEntity, PoseStack matrices, SubmitNodeCollector queue, CameraRenderState cameraState) {

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

    private int getLightLevel(Level world, BlockPos pos) {
        int bLight = world.getBrightness(LightLayer.BLOCK, pos);
        int sLight = world.getBrightness(LightLayer.SKY, pos);
        return LightTexture.pack(bLight, sLight);
    }

    @Override
    public ShellfishTrapBlockEntityRenderState createRenderState() {
        return new ShellfishTrapBlockEntityRenderState();
    }

    @Override
    public void extractRenderState(AbstractTrapBlockEntity blockEntity, ShellfishTrapBlockEntityRenderState state, float tickProgress, Vec3 cameraPos,
                                   ModelFeatureRenderer.CrumblingOverlay crumblingOverlay) {
        BlockEntityRenderer.super.extractRenderState(blockEntity, state, tickProgress, cameraPos, crumblingOverlay);
        state.tickDelta = tickProgress;
        state.renderBait = new ItemStackRenderState();
        itemModelManager.updateForTopItem(state.renderBait, blockEntity.renderBait(), ItemDisplayContext.FIXED, blockEntity.getLevel(), null, 1);
        state.worldTime = blockEntity.getLevel().getGameTime();
    }
}
