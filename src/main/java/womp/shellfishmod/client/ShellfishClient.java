package womp.shellfishmod.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.GrassColors;
import womp.shellfishmod.client.model.ClamModel;
import womp.shellfishmod.client.model.CrabModel;
import womp.shellfishmod.client.model.CrayfishModel;
import womp.shellfishmod.client.model.LobsterModel;
import womp.shellfishmod.client.model.MossBallModel;
import womp.shellfishmod.client.model.MusselModel;
import womp.shellfishmod.client.model.OysterModel;
import womp.shellfishmod.client.model.SeaSnailModel;
import womp.shellfishmod.client.model.SeaUrchinModel;
import womp.shellfishmod.client.model.ShrimpModel;
import womp.shellfishmod.client.registry.ShellfishCrayfish;
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
    
    public static final EntityModelLayer OYSTER_MODEL = registerML("oyster", "oyster_model");
    public static final EntityModelLayer CLAM_MODEL = registerML("clam", "clam_model");
    public static final EntityModelLayer SEA_URCHIN_MODEL = registerML("sea_urchin", "sea_urchin_model");
    public static final EntityModelLayer SEA_SNAIL_MODEL = registerML("sea_snail", "sea_snail_model");
    public static final EntityModelLayer SHRIMP_MODEL = registerML("shrimp", "shrimp_model");
    public static final EntityModelLayer LOBSTER_MODEL = registerML("lobster", "lobster_model");
    public static final EntityModelLayer CRAYFISH_MODEL = registerML("crayfish", "crayfish_model");
    public static final EntityModelLayer CRAB_MODEL = registerML("crab", "crab_model");
    public static final EntityModelLayer MUSSEL_MODEL = registerML("mussel", "mussel_model");
    public static final EntityModelLayer MOSS_BALL_MODEL = registerML("moss_ball", "moss_ball_model");

    private static EntityModelLayer registerML(String path, String name) {
        return new EntityModelLayer(Identifier.of("shellfish", path), name);
    }

    private static void registerClientEntityRenders() {
        EntityRendererRegistry.register(ShellfishEntities.CRAYFISH, CrayfishRenderer::new);
        EntityRendererRegistry.register(ShellfishEntities.LOBSTER, LobsterRenderer::new);
        EntityRendererRegistry.register(ShellfishEntities.CRAB, CrabRenderer::new);
        EntityRendererRegistry.register(ShellfishEntities.SHRIMP, ShrimpRenderer::new);
        EntityRendererRegistry.register(ShellfishEntities.SEA_SNAIL, SeaSnailRenderer::new);
        EntityRendererRegistry.register(ShellfishEntities.SEA_URCHIN, SeaUrchinRenderer::new);   
        EntityRendererRegistry.register(ShellfishEntities.CLAM, ClamRenderer::new);   
        EntityRendererRegistry.register(ShellfishEntities.OYSTER, OysterRenderer::new);   
        EntityRendererRegistry.register(ShellfishEntities.MUSSEL, MusselRenderer::new);   
        EntityRendererRegistry.register(ShellfishEntities.MOSS_BALL, MossBallRenderer::new);   
    }

    private static void registerClientEntityModels() {
        EntityModelLayerRegistry.registerModelLayer(OYSTER_MODEL, OysterModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(CLAM_MODEL, ClamModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(SEA_URCHIN_MODEL, SeaUrchinModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(SEA_SNAIL_MODEL, SeaSnailModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(SHRIMP_MODEL, ShrimpModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(LOBSTER_MODEL, LobsterModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(CRAYFISH_MODEL, CrayfishModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(CRAB_MODEL, CrabModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(MUSSEL_MODEL, MusselModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(MOSS_BALL_MODEL, MossBallModel::getTexturedModelData);
    }


    @Override
    public void onInitializeClient() {
        registerClientEntityRenders();
        registerClientEntityModels();
        
        BlockRenderLayerMap.INSTANCE.putBlock(ShellfishBlocks.SEA_SNAIL_EGGS_BLOCK, RenderLayer.getTranslucent());
        for (Block cut : ShellfishBlocks.getCutouts()) BlockRenderLayerMap.INSTANCE.putBlock(cut, RenderLayer.getCutout());

        ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> {
            if (world != null && pos != null) {
                return BiomeColors.getGrassColor(world, pos);
            }
            return GrassColors.getDefaultColor();
        }, ShellfishBlocks.WATER_GRASS, ShellfishBlocks.TALL_WATER_GRASS);

        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> {
            return GrassColors.getDefaultColor();
        }, ShellfishBlocks.WATER_GRASS, ShellfishBlocks.TALL_WATER_GRASS);

        BlockEntityRendererFactories.register(ShellfishBlocks.WATER_LETTUCE_BLOCK_ENTITY, WaterLettuceRenderer::new);
        BlockEntityRendererFactories.register(ShellfishBlocks.SEA_LETTUCE_BLOCK_ENTITY, SeaLettuceRenderer::new);
        BlockEntityRendererFactories.register(ShellfishBlocks.SHELLFISH_TRAP_BLOCK_ENTITY, ShellfishTrapRenderer::new);
        BlockEntityRendererFactories.register(ShellfishBlocks.REINFORCED_TRAP_BLOCK_ENTITY, ShellfishTrapRenderer::new);

        HandledScreens.register(ShellfishScreens.SHELLFISH_TRAP_SCREEN_HANDLER, ShellfishTrapScreen::new);

        ShellfishConfig.loadConfig();
        ShellfishPackets.registerClient();
        ShellfishCrayfish.register();
    }
}
