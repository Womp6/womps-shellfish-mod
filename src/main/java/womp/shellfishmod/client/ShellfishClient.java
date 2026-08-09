package womp.shellfishmod.client;

import java.util.List;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.BlockColorRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ModelLayerRegistry;
import net.minecraft.client.color.block.BlockTintSource;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.block.BlockAndTintGetter;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.GrassColor;
import net.minecraft.world.level.block.state.BlockState;
import womp.shellfishmod.client.model.ClamModel;
import womp.shellfishmod.client.model.CrabModel;
import womp.shellfishmod.client.model.CrayfishModel;
import womp.shellfishmod.client.model.LobsterModel;
import womp.shellfishmod.client.model.MossBallModel;
import womp.shellfishmod.client.model.MusselModel;
import womp.shellfishmod.client.model.OysterModel;
import womp.shellfishmod.client.model.SeaUrchinModel;
import womp.shellfishmod.client.model.ShrimpModel;
import womp.shellfishmod.client.model.snail.*;
import womp.shellfishmod.client.renderer.ClamRenderer;
import womp.shellfishmod.client.renderer.CrabRenderer;
import womp.shellfishmod.client.renderer.CrayfishRenderer;
import womp.shellfishmod.client.renderer.LobsterRenderer;
import womp.shellfishmod.client.renderer.MossBallRenderer;
import womp.shellfishmod.client.renderer.MusselRenderer;
import womp.shellfishmod.client.renderer.OysterRenderer;
import womp.shellfishmod.client.renderer.SeaLettuceRenderer;
import womp.shellfishmod.client.renderer.SeaSnailRenderer;
import womp.shellfishmod.client.renderer.SeaUrchinRenderer;
import womp.shellfishmod.client.renderer.ShellfishTrapRenderer;
import womp.shellfishmod.client.renderer.ShrimpRenderer;
import womp.shellfishmod.client.renderer.WaterLettuceRenderer;
import womp.shellfishmod.registry.ShellfishBlocks;
import womp.shellfishmod.registry.ShellfishEntities;
import womp.shellfishmod.registry.ShellfishPackets;
import womp.shellfishmod.registry.ShellfishScreens;
import womp.shellfishmod.screens.ShellfishTrapScreen;
import womp.shellfishmod.util.config.ShellfishConfig;

@Environment(EnvType.CLIENT)
public class ShellfishClient implements ClientModInitializer {
    
    public static final ModelLayerLocation OYSTER_MODEL = registerML("oyster", "oyster_model");
    public static final ModelLayerLocation CLAM_MODEL = registerML("clam", "clam_model");
    public static final ModelLayerLocation SEA_URCHIN_MODEL = registerML("sea_urchin", "sea_urchin_model");
    public static final ModelLayerLocation SEA_SNAIL_MODEL1 = registerML("sea_snail1", "sea_snail_model1");
    public static final ModelLayerLocation SEA_SNAIL_MODEL2 = registerML("sea_snail2", "sea_snail_model2");
    public static final ModelLayerLocation SEA_SNAIL_MODEL3 = registerML("sea_snail3", "sea_snail_model3");
    public static final ModelLayerLocation SEA_SNAIL_MODEL4 = registerML("sea_snail4", "sea_snail_model4");
    public static final ModelLayerLocation SEA_SNAIL_MODEL5 = registerML("sea_snail5", "sea_snail_model5");
    public static final ModelLayerLocation SHRIMP_MODEL = registerML("shrimp", "shrimp_model");
    public static final ModelLayerLocation LOBSTER_MODEL = registerML("lobster", "lobster_model");
    public static final ModelLayerLocation CRAYFISH_MODEL = registerML("crayfish", "crayfish_model");
    public static final ModelLayerLocation CRAB_MODEL = registerML("crab", "crab_model");
    public static final ModelLayerLocation MUSSEL_MODEL = registerML("mussel", "mussel_model");
    public static final ModelLayerLocation MOSS_BALL_MODEL = registerML("moss_ball", "moss_ball_model");

    private static ModelLayerLocation registerML(String path, String name) {
        return new ModelLayerLocation(Identifier.fromNamespaceAndPath("shellfish", path), name);
    }

    private static void registerClientEntityRenders() {
        EntityRenderers.register(ShellfishEntities.CRAYFISH, CrayfishRenderer::new);
        EntityRenderers.register(ShellfishEntities.LOBSTER, LobsterRenderer::new);
        EntityRenderers.register(ShellfishEntities.CRAB, CrabRenderer::new);
        EntityRenderers.register(ShellfishEntities.SHRIMP, ShrimpRenderer::new);
        EntityRenderers.register(ShellfishEntities.SEA_SNAIL, SeaSnailRenderer::new);
        EntityRenderers.register(ShellfishEntities.SEA_URCHIN, SeaUrchinRenderer::new);   
        EntityRenderers.register(ShellfishEntities.CLAM, ClamRenderer::new);   
        EntityRenderers.register(ShellfishEntities.OYSTER, OysterRenderer::new);   
        EntityRenderers.register(ShellfishEntities.MUSSEL, MusselRenderer::new);   
        EntityRenderers.register(ShellfishEntities.MOSS_BALL, MossBallRenderer::new);   
    }

    private static void registerClientEntityModels() {
        ModelLayerRegistry.registerModelLayer(OYSTER_MODEL, OysterModel::getTexturedModelData);
        ModelLayerRegistry.registerModelLayer(CLAM_MODEL, ClamModel::getTexturedModelData);
        ModelLayerRegistry.registerModelLayer(SEA_URCHIN_MODEL, SeaUrchinModel::getTexturedModelData);
        ModelLayerRegistry.registerModelLayer(SEA_SNAIL_MODEL1, SeaSnailModelV1::getTexturedModelData);
        ModelLayerRegistry.registerModelLayer(SEA_SNAIL_MODEL2, SeaSnailModelV2::getTexturedModelData);
        ModelLayerRegistry.registerModelLayer(SEA_SNAIL_MODEL3, SeaSnailModelV3::getTexturedModelData);
        ModelLayerRegistry.registerModelLayer(SEA_SNAIL_MODEL4, SeaSnailModelV4::getTexturedModelData);
        ModelLayerRegistry.registerModelLayer(SEA_SNAIL_MODEL5, SeaSnailModelV5::getTexturedModelData);
        ModelLayerRegistry.registerModelLayer(SHRIMP_MODEL, ShrimpModel::getTexturedModelData);
        ModelLayerRegistry.registerModelLayer(LOBSTER_MODEL, LobsterModel::getTexturedModelData);
        ModelLayerRegistry.registerModelLayer(CRAYFISH_MODEL, CrayfishModel::getTexturedModelData);
        ModelLayerRegistry.registerModelLayer(CRAB_MODEL, CrabModel::getTexturedModelData);
        ModelLayerRegistry.registerModelLayer(MUSSEL_MODEL, MusselModel::getTexturedModelData);
        ModelLayerRegistry.registerModelLayer(MOSS_BALL_MODEL, MossBallModel::getTexturedModelData);
    }


    @Override
    public void onInitializeClient() {
        registerClientEntityRenders();
        registerClientEntityModels();

        BlockColorRegistry.register(List.of(new BlockTintSource() {
            @Override
            public int colorInWorld(BlockState state, BlockAndTintGetter level, BlockPos pos) {
                if (level != null && pos != null) {
                    return BiomeColors.getAverageGrassColor(level, pos);
                }
                return GrassColor.getDefaultColor();
            }

            @Override
            public int color(BlockState state) {
                return GrassColor.getDefaultColor();
            }
        }), ShellfishBlocks.WATER_GRASS, ShellfishBlocks.TALL_WATER_GRASS);
        
        BlockEntityRenderers.register(ShellfishBlocks.WATER_LETTUCE_BLOCK_ENTITY, WaterLettuceRenderer::new);
        BlockEntityRenderers.register(ShellfishBlocks.SEA_LETTUCE_BLOCK_ENTITY, SeaLettuceRenderer::new);
        BlockEntityRenderers.register(ShellfishBlocks.SHELLFISH_TRAP_BLOCK_ENTITY, ShellfishTrapRenderer::new);
        BlockEntityRenderers.register(ShellfishBlocks.REINFORCED_TRAP_BLOCK_ENTITY, ShellfishTrapRenderer::new);

        MenuScreens.register(ShellfishScreens.SHELLFISH_TRAP_SCREEN_HANDLER, ShellfishTrapScreen::new);

        ShellfishConfig.loadConfig();
        ShellfishPackets.registerClient();
    }
}
