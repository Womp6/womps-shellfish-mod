package womp.shellfishmod.client.renderer;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;

import java.util.Random;

import womp.shellfishmod.blocks.SeaLettuceBlockEntity;
import womp.shellfishmod.client.model.SeaLettuceModel;
import womp.shellfishmod.util.config.ShellfishConfig;

@Environment(EnvType.CLIENT)
public class SeaLettuceRenderer implements BlockEntityRenderer<SeaLettuceBlockEntity> {

    private final SeaLettuceModel seaLettuceModel;
    private final Identifier texture = Identifier.of("shellfish", "textures/block/sea_lettuce.png");

    public SeaLettuceRenderer(BlockEntityRendererFactory.Context context) {
        this.seaLettuceModel = new SeaLettuceModel(SeaLettuceModel.getTexturedModelData().createModel());
    }

    @Override
    public void render(SeaLettuceBlockEntity blockEntity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, Vec3d vec) {
        
        blockEntity.set3d(ShellfishConfig.getShellfishGraphics() == 2);

        if(ShellfishConfig.getShellfishGraphics() == 2) {
            matrices.push();
        
            matrices.translate(0.5f, 1.495f, 0.5f);
            matrices.scale(1f, 1f, 1f);
            matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(180.0F));

            Random random = new Random(blockEntity.getPos().hashCode());
            float offsetX = (random.nextFloat() - 0.5f) * 0.5f;
            float offsetZ = (random.nextFloat() - 0.5f) * 0.5f;

            matrices.translate(offsetX, 0f, offsetZ);

            if (blockEntity.isLarge()) {
                matrices.scale(2f, 2f, 2f);
                matrices.translate(0f, -0.7475f, 0f);
            }

            seaLettuceModel.render(blockEntity, matrices, vertexConsumers.getBuffer(RenderLayer.getEntityCutoutNoCull(getTexture(blockEntity))), light, overlay, vec);

            matrices.pop();
        }
    }

    @Override
    public boolean rendersOutsideBoundingBox(SeaLettuceBlockEntity blockEntity) {
        return true;
    }

    private Identifier getTexture(SeaLettuceBlockEntity lettuce) {
        return texture;
    }
}