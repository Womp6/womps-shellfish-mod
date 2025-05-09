package womp.shellfishmod.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import womp.shellfishmod.blocks.SeaLettuceBlockEntity;
import womp.shellfishmod.client.model.SeaLettuceModel;
import womp.shellfishmod.util.config.ShellfishConfig;

import java.util.Random;

@OnlyIn(Dist.CLIENT)
public class SeaLettuceRenderer implements BlockEntityRenderer<SeaLettuceBlockEntity> {

    private final SeaLettuceModel seaLettuceModel;
    private final ResourceLocation texture = ResourceLocation.fromNamespaceAndPath("shellfish", "textures/block/sea_lettuce.png");

    public SeaLettuceRenderer(BlockEntityRendererProvider.Context context) {
        this.seaLettuceModel = new SeaLettuceModel(SeaLettuceModel.createBodyLayer().bakeRoot());
    }

    @Override
    public void render(SeaLettuceBlockEntity blockEntity, float tickDelta, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay) {

        blockEntity.set3d(ShellfishConfig.getShellfishGraphics() == 2);

        if(ShellfishConfig.getShellfishGraphics() == 2) {
            matrices.pushPose();

            matrices.translate(0.5f, 1.495f, 0.5f);
            matrices.scale(1f, 1f, 1f);
            matrices.mulPose(Axis.XP.rotationDegrees(180.0F));

            Random random = new Random(blockEntity.getBlockPos().hashCode());
            float offsetX = (random.nextFloat() - 0.5f) * 0.5f;
            float offsetZ = (random.nextFloat() - 0.5f) * 0.5f;

            matrices.translate(offsetX, 0f, offsetZ);

            if (blockEntity.isLarge()) {
                matrices.scale(2f, 2f, 2f);
                matrices.translate(0f, -0.7475f, 0f);
            }

            seaLettuceModel.render(blockEntity, matrices, vertexConsumers.getBuffer(RenderType.entityCutoutNoCull(getTexture(blockEntity))), light, overlay);

            matrices.popPose();
        }
    }

    @Override
    public boolean shouldRenderOffScreen(SeaLettuceBlockEntity blockEntity) {
        return true;
    }

    private ResourceLocation getTexture(SeaLettuceBlockEntity lettuce) {
        return texture;
    }
}
