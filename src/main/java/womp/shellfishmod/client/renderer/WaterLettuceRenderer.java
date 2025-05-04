package womp.shellfishmod.client.renderer;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.biome.BiomeKeys;
import womp.shellfishmod.blocks.WaterLettuceBlockEntity;
import womp.shellfishmod.client.model.WaterLettuceModel;
import womp.shellfishmod.util.config.ShellfishConfig;

@Environment(EnvType.CLIENT)
public class WaterLettuceRenderer implements BlockEntityRenderer<WaterLettuceBlockEntity> {

    private final WaterLettuceModel lettuceModel;
    private final Identifier darkTexture = new Identifier("shellfish", "textures/block/water_lettuce_dark.png");
    private final Identifier defaultTexture = new Identifier("shellfish", "textures/block/water_lettuce.png");

    protected static final VoxelShape SHAPE = ShellfishConfig.getShellfishGraphics() == 2 ? Block.createCuboidShape(2.5, -1.0, 2.5, 13.5, 0.5, 13.5) : Block.createCuboidShape(1.0, 0.0, 1.0, 15.0, 1.5, 15.0);

    public WaterLettuceRenderer(BlockEntityRendererFactory.Context context) {
        this.lettuceModel = new WaterLettuceModel(WaterLettuceModel.getTexturedModelData().createModel());
    }

    @Override
    public void render(WaterLettuceBlockEntity blockEntity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {

        blockEntity.set3d(ShellfishConfig.getShellfishGraphics() >= 1);
        blockEntity.setSwamp(isSwamp(blockEntity));

        if (ShellfishConfig.getShellfishGraphics() >= 1) {
            matrices.push();
            matrices.translate(0.5f, 1.38f, 0.5f);
            matrices.scale(1f, 1f, 1f);
            matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(180.0F));
            lettuceModel.render(matrices, vertexConsumers.getBuffer(RenderLayer.getEntityCutout(getTexture(blockEntity))), light, overlay);

            matrices.pop();
        }
    }

    private Identifier getTexture(WaterLettuceBlockEntity lettuce) {
        if (lettuce.getWorld().getBiome(lettuce.getPos()).matchesKey(BiomeKeys.SWAMP)) {
            return darkTexture;
        }
        return defaultTexture;
    }

    private boolean isSwamp(WaterLettuceBlockEntity blockEntity) {
        if (blockEntity.getWorld().getBiome(blockEntity.getPos()).matchesKey(BiomeKeys.SWAMP)) {
            return true;
        } else {
            return false;
        }
    }
}
