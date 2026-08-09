package womp.shellfishmod.client.renderer;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.command.ModelCommandRenderer.CrumblingOverlayCommand;
import net.minecraft.client.render.state.CameraRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;

import java.util.Random;

import womp.shellfishmod.blocks.SeaLettuceBlockEntity;
import womp.shellfishmod.client.model.SeaLettuceModel;
import womp.shellfishmod.client.states.SeaLettuceBlockEntityRenderState;
import womp.shellfishmod.util.config.ShellfishConfig;

@Environment(EnvType.CLIENT)
public class SeaLettuceRenderer implements BlockEntityRenderer<SeaLettuceBlockEntity, SeaLettuceBlockEntityRenderState> {

    private final SeaLettuceModel seaLettuceModel;
    private final Identifier texture = Identifier.of("shellfish", "textures/block/sea_lettuce.png");

    public SeaLettuceRenderer(BlockEntityRendererFactory.Context context) {
        this.seaLettuceModel = new SeaLettuceModel(SeaLettuceModel.getTexturedModelData().createModel());
    }

    @Override
    public void render(SeaLettuceBlockEntityRenderState blockEntity, MatrixStack matrices, OrderedRenderCommandQueue queue,
            CameraRenderState cameraState) {

        if(ShellfishConfig.getShellfishGraphics() == 2) {
            matrices.push();
        
            matrices.translate(0.5f, 1.495f, 0.5f);
            matrices.scale(1f, 1f, 1f);
            matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(180.0F));

            Random random = new Random(blockEntity.randomOffsetHash);
            float offsetX = (random.nextFloat() - 0.5f) * 0.5f;
            float offsetZ = (random.nextFloat() - 0.5f) * 0.5f;

            matrices.translate(offsetX, 0f, offsetZ);

            if (blockEntity.isLarge) {
                matrices.scale(2f, 2f, 2f);
                matrices.translate(0f, -0.7475f, 0f);
            }

            seaLettuceModel.render(blockEntity, matrices, queue, cameraState, getTexture());

            matrices.pop();
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
    public void updateRenderState(SeaLettuceBlockEntity blockEntity, SeaLettuceBlockEntityRenderState state,
            float tickProgress, Vec3d cameraPos, CrumblingOverlayCommand crumblingOverlay) {
        BlockEntityRenderer.super.updateRenderState(blockEntity, state, tickProgress, cameraPos, crumblingOverlay);
        blockEntity.set3d(ShellfishConfig.getShellfishGraphics() == 2);
        state.isLarge = blockEntity.isLarge();
        state.randomOffsetHash = blockEntity.getPos().hashCode();
        state.animationStartTime = blockEntity.getAnimationStartTime();
    }
}