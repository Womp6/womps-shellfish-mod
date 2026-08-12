package womp.shellfishmod.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.world.phys.Vec3;
import womp.shellfishmod.blocks.SeaLettuceBlockEntity;
import womp.shellfishmod.client.model.SeaLettuceModel;
import womp.shellfishmod.client.states.SeaLettuceBlockEntityRenderState;
import womp.shellfishmod.util.config.ShellfishConfig;

import java.util.Random;

public class SeaLettuceRenderer implements BlockEntityRenderer<SeaLettuceBlockEntity, SeaLettuceBlockEntityRenderState> {

    private final SeaLettuceModel seaLettuceModel;
    private final Identifier texture = Identifier.fromNamespaceAndPath("shellfish", "textures/block/sea_lettuce.png");

    public SeaLettuceRenderer(BlockEntityRendererProvider.Context context) {
        this.seaLettuceModel = new SeaLettuceModel(SeaLettuceModel.createBodyLayer().bakeRoot());
    }

    @Override
    public void submit(SeaLettuceBlockEntityRenderState blockEntity, PoseStack matrices, SubmitNodeCollector queue, CameraRenderState camera) {

        if(ShellfishConfig.getShellfishGraphics() == 2) {
            matrices.pushPose();

            matrices.translate(0.5f, 1.495f, 0.5f);
            matrices.scale(1f, 1f, 1f);
            matrices.mulPose(Axis.XP.rotationDegrees(180.0F));

            Random random = new Random(blockEntity.randomOffsetHash);
            float offsetX = (random.nextFloat() - 0.5f) * 0.5f;
            float offsetZ = (random.nextFloat() - 0.5f) * 0.5f;

            matrices.translate(offsetX, 0f, offsetZ);

            if (blockEntity.isLarge) {
                matrices.scale(2f, 2f, 2f);
                matrices.translate(0f, -0.7475f, 0f);
            }

            seaLettuceModel.render(blockEntity, matrices, queue, camera, getTexture());

            matrices.popPose();
        }
    }

    private Identifier getTexture() {
        return texture;
    }

    @Override
    public SeaLettuceBlockEntityRenderState createRenderState() {
        return new SeaLettuceBlockEntityRenderState();
    }

    @Override
    public void extractRenderState(SeaLettuceBlockEntity blockEntity, SeaLettuceBlockEntityRenderState state,
                                   float tickProgress, Vec3 cameraPos, ModelFeatureRenderer.CrumblingOverlay crumblingOverlay) {
        BlockEntityRenderer.super.extractRenderState(blockEntity, state, tickProgress, cameraPos, crumblingOverlay);
        blockEntity.set3d(ShellfishConfig.getShellfishGraphics() == 2);
        state.isLarge = blockEntity.isLarge();
        state.randomOffsetHash = blockEntity.getBlockPos().hashCode();
        state.animationStartTime = blockEntity.getAnimationStartTime();
    }
}
