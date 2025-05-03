package womp.shellfishmod.client.renderer;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import womp.shellfishmod.blocks.parents.AbstractTrapBlockEntity;
import womp.shellfishmod.util.config.ShellfishConfig;

public class ShellfishTrapRenderer<T extends AbstractTrapBlockEntity> implements BlockEntityRenderer<T>{

    public ShellfishTrapRenderer(BlockEntityRendererFactory.Context context) {}

    @Override
    public void render(T blockEntity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers,
            int light, int overlay) {
        
        if (ShellfishConfig.getShellfishGraphics() >= 1) {
            ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();
            ItemStack itemStack = blockEntity.renderBait();
            matrices.push();
            long worldTime = blockEntity.getWorld().getTime();
            float bobbingOffset = 0.02f * (float) Math.sin((worldTime + tickDelta) / 8.0);
            matrices.translate(0.5f, 0.5f + bobbingOffset, 0.5f);
            matrices.scale(0.6f, 0.6f, 0.6f);
            float angle = (worldTime + tickDelta) % 360 * 2f;
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(angle));

            itemRenderer.renderItem(itemStack, ModelTransformationMode.FIXED, getLightLevel(blockEntity.getWorld(), blockEntity.getPos()), OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, blockEntity.getWorld(), 1);
            matrices.pop();
        }
    }
    
    private int getLightLevel(World world, BlockPos pos) {
        int bLight = world.getLightLevel(LightType.BLOCK, pos);
        int sLight = world.getLightLevel(LightType.SKY, pos);
        return LightmapTextureManager.pack(bLight, sLight);
    }
}
