package womp.shellfishmod.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import womp.shellfishmod.blocks.WaterLettuceBlockEntity;
import womp.shellfishmod.client.model.WaterLettuceModel;
import womp.shellfishmod.util.config.ShellfishConfig;

@OnlyIn(Dist.CLIENT)
public class WaterLettuceRenderer implements BlockEntityRenderer<WaterLettuceBlockEntity> {

    private final WaterLettuceModel lettuceModel;
    private final ResourceLocation darkTexture = ResourceLocation.fromNamespaceAndPath("shellfish", "textures/block/water_lettuce_dark.png");
    private final ResourceLocation defaultTexture = ResourceLocation.fromNamespaceAndPath("shellfish", "textures/block/water_lettuce.png");

    protected static final VoxelShape SHAPE = ShellfishConfig.getShellfishGraphics() == 2 ? Block.box(2.5, -1.0, 2.5, 13.5, 0.5, 13.5) : Block.box(1.0, 0.0, 1.0, 15.0, 1.5, 15.0);

    public WaterLettuceRenderer(BlockEntityRendererProvider.Context context) {
        this.lettuceModel = new WaterLettuceModel(WaterLettuceModel.createBodyLayer().bakeRoot());
    }

    @Override
    public void render(WaterLettuceBlockEntity blockEntity, float tickDelta, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay, Vec3 vec) {

        blockEntity.set3d(ShellfishConfig.getShellfishGraphics() >= 1);
        blockEntity.setSwamp(isSwamp(blockEntity));

        if (ShellfishConfig.getShellfishGraphics() >= 1) {
            matrices.pushPose();
            matrices.translate(0.5f, 1.38f, 0.5f);
            matrices.scale(1f, 1f, 1f);
            matrices.mulPose(Axis.XP.rotationDegrees(180.0F));
            lettuceModel.render(matrices, vertexConsumers.getBuffer(RenderType.entityCutout(getTexture(blockEntity))), light, overlay, vec);

            matrices.popPose();
        }
    }

    private ResourceLocation getTexture(WaterLettuceBlockEntity lettuce) {
        if (lettuce.getLevel().getBiome(lettuce.getBlockPos()).is(Biomes.SWAMP)) {
            return darkTexture;
        }
        return defaultTexture;
    }

    private boolean isSwamp(WaterLettuceBlockEntity blockEntity) {
        if (blockEntity.getLevel().getBiome(blockEntity.getBlockPos()).is(Biomes.SWAMP)) {
            return true;
        } else {
            return false;
        }
    }
}
