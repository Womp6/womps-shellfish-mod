package womp.shellfishmod.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.phys.Vec3;
import womp.shellfishmod.blocks.parents.AbstractTrapBlockEntity;
import womp.shellfishmod.util.config.ShellfishConfig;

public class ShellfishTrapRenderer implements BlockEntityRenderer<AbstractTrapBlockEntity> {

    public ShellfishTrapRenderer(BlockEntityRendererProvider.Context context) {}

    @Override
    public void render(AbstractTrapBlockEntity blockEntity, float tickDelta, PoseStack matrices, MultiBufferSource vertexConsumers,
                       int light, int overlay, Vec3 vec) {

        if (ShellfishConfig.getShellfishGraphics() >= 1) {
            ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
            ItemStack itemStack = blockEntity.renderBait();
            matrices.pushPose();
            long worldTime = blockEntity.getLevel().getGameTime() ;
            float bobbingOffset = 0.02f * (float) Math.sin((worldTime + tickDelta) / 8.0);
            matrices.translate(0.5f, 0.5f + bobbingOffset, 0.5f);
            matrices.scale(0.6f, 0.6f, 0.6f);
            float angle = (worldTime + tickDelta) % 360 * 2f;
            matrices.mulPose(Axis.YP.rotationDegrees(angle));

            itemRenderer.renderStatic(itemStack, ItemDisplayContext.FIXED, getLightLevel(blockEntity.getLevel(), blockEntity.getBlockPos()), OverlayTexture.NO_OVERLAY, matrices, vertexConsumers, blockEntity.getLevel(), 1);
            matrices.popPose();
        }
    }

    private int getLightLevel(Level world, BlockPos pos) {
        int bLight = world.getBrightness(LightLayer.BLOCK, pos);
        int sLight = world.getBrightness(LightLayer.SKY, pos);
        return LightTexture.pack(bLight, sLight);
    }
}
