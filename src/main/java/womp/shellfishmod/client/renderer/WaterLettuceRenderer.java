package womp.shellfishmod.client.renderer;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.command.ModelCommandRenderer.CrumblingOverlayCommand;
import net.minecraft.client.render.state.CameraRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import womp.shellfishmod.blocks.WaterLettuceBlockEntity;
import womp.shellfishmod.client.model.WaterLettuceModel;
import womp.shellfishmod.client.states.WaterLettuceBlockEntityRenderState;
import womp.shellfishmod.registry.ShellfishWorldgen;
import womp.shellfishmod.util.config.ShellfishConfig;

@Environment(EnvType.CLIENT)
public class WaterLettuceRenderer implements BlockEntityRenderer<WaterLettuceBlockEntity, WaterLettuceBlockEntityRenderState> {

    private final WaterLettuceModel lettuceModel;
    private final Identifier darkTexture = Identifier.of("shellfish", "textures/block/water_lettuce_dark.png");
    private final Identifier marshTexture = Identifier.of("shellfish", "textures/block/water_lettuce_marsh.png");
    private final Identifier defaultTexture = Identifier.of("shellfish", "textures/block/water_lettuce.png");

    protected static final VoxelShape SHAPE = ShellfishConfig.getShellfishGraphics() == 2 ? Block.createCuboidShape(2.5, -1.0, 2.5, 13.5, 0.5, 13.5) : Block.createCuboidShape(1.0, 0.0, 1.0, 15.0, 1.5, 15.0);

    public WaterLettuceRenderer(BlockEntityRendererFactory.Context context) {
        this.lettuceModel = new WaterLettuceModel(WaterLettuceModel.getTexturedModelData().createModel());
    }

    @Override
    public void render(WaterLettuceBlockEntityRenderState blockEntity, MatrixStack matrices, OrderedRenderCommandQueue queue,
            CameraRenderState cameraState) {

        if (ShellfishConfig.getShellfishGraphics() >= 1) {
            matrices.push();
            matrices.translate(0.5f, 1.38f, 0.5f);
            matrices.scale(1f, 1f, 1f);
            matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(180.0F));
            lettuceModel.render(blockEntity, matrices, queue, cameraState);

            matrices.pop();
        }
    }

    private Identifier getTexture(WaterLettuceBlockEntity lettuce) {
        if (isBiome(BiomeKeys.SWAMP, lettuce)) {
            return darkTexture;
        } else if (isBiome(ShellfishWorldgen.MARSH, lettuce)) {
            return marshTexture;
        }
        return defaultTexture;
    }

    private boolean isBiome(RegistryKey<Biome> biome, WaterLettuceBlockEntity blockEntity) {
        if (blockEntity.getWorld().getBiome(blockEntity.getPos()).matchesKey(biome)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public WaterLettuceBlockEntityRenderState createRenderState() {
        return new WaterLettuceBlockEntityRenderState();
    }

    @Override
    public void updateRenderState(WaterLettuceBlockEntity blockEntity, WaterLettuceBlockEntityRenderState state,
            float tickProgress, Vec3d cameraPos, CrumblingOverlayCommand crumblingOverlay) {
        BlockEntityRenderer.super.updateRenderState(blockEntity, state, tickProgress, cameraPos, crumblingOverlay);
        blockEntity.set3d(ShellfishConfig.getShellfishGraphics() >= 1);
        blockEntity.setSwamp(isBiome(BiomeKeys.SWAMP, blockEntity));
        blockEntity.setMarsh(isBiome(ShellfishWorldgen.MARSH, blockEntity));
        state.texture = getTexture(blockEntity);
    }
}
