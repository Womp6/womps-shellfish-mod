package womp.shellfishmod.client;

import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.GrassColor;
import net.minecraft.world.level.block.Block;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import womp.shellfishmod.ShellfishMod;
import womp.shellfishmod.client.model.*;
import womp.shellfishmod.client.registry.ShellfishCrayfish;
import womp.shellfishmod.client.renderer.*;
import womp.shellfishmod.util.config.ShellfishConfig;
import womp.shellfishmod.registry.ShellfishBlocks;
import womp.shellfishmod.registry.ShellfishEntities;
import womp.shellfishmod.registry.ShellfishScreens;
import womp.shellfishmod.screens.ShellfishTrapScreen;

@EventBusSubscriber(modid = ShellfishMod.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ShellfishClient {

    public static final ModelLayerLocation OYSTER_MODEL = registerML("oyster", "oyster_model");
    public static final ModelLayerLocation CLAM_MODEL = registerML("clam", "clam_model");
    public static final ModelLayerLocation SEA_URCHIN_MODEL = registerML("sea_urchin", "sea_urchin_model");
    public static final ModelLayerLocation SEA_SNAIL_MODEL = registerML("sea_snail", "sea_snail_model");
    public static final ModelLayerLocation SHRIMP_MODEL = registerML("shrimp", "shrimp_model");
    public static final ModelLayerLocation LOBSTER_MODEL = registerML("lobster", "lobster_model");
    public static final ModelLayerLocation CRAYFISH_MODEL = registerML("crayfish", "crayfish_model");
    public static final ModelLayerLocation CRAB_MODEL = registerML("crab", "crab_model");
    public static final ModelLayerLocation MUSSEL_MODEL = registerML("mussel", "mussel_model");
    public static final ModelLayerLocation MOSS_BALL_MODEL = registerML("moss_ball", "moss_ball_model");

    private static ModelLayerLocation registerML(String path, String name) {
        return new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("shellfish", path), name);
    }

    public static void registerClientEntityRenders() {
        EntityRenderers.register(ShellfishEntities.CRAYFISH.get(), CrayfishRenderer::new);
        EntityRenderers.register(ShellfishEntities.LOBSTER.get(), LobsterRenderer::new);
        EntityRenderers.register(ShellfishEntities.CRAB.get(), CrabRenderer::new);
        EntityRenderers.register(ShellfishEntities.SHRIMP.get(), ShrimpRenderer::new);
        EntityRenderers.register(ShellfishEntities.SEA_SNAIL.get(), SeaSnailRenderer::new);
        EntityRenderers.register(ShellfishEntities.SEA_URCHIN.get(), SeaUrchinRenderer::new);
        EntityRenderers.register(ShellfishEntities.CLAM.get(), ClamRenderer::new);
        EntityRenderers.register(ShellfishEntities.OYSTER.get(), OysterRenderer::new);
        EntityRenderers.register(ShellfishEntities.MUSSEL.get(), MusselRenderer::new);
        EntityRenderers.register(ShellfishEntities.MOSS_BALL.get(), MossBallRenderer::new);
    }

    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(CRAYFISH_MODEL, CrayfishModel::getTexturedModelData);
        event.registerLayerDefinition(LOBSTER_MODEL, LobsterModel::getTexturedModelData);
        event.registerLayerDefinition(CRAB_MODEL, CrabModel::getTexturedModelData);
        event.registerLayerDefinition(SHRIMP_MODEL, ShrimpModel::getTexturedModelData);
        event.registerLayerDefinition(SEA_SNAIL_MODEL, SeaSnailModel::getTexturedModelData);
        event.registerLayerDefinition(SEA_URCHIN_MODEL, SeaUrchinModel::getTexturedModelData);
        event.registerLayerDefinition(CLAM_MODEL, ClamModel::getTexturedModelData);
        event.registerLayerDefinition(OYSTER_MODEL, OysterModel::getTexturedModelData);
        event.registerLayerDefinition(MUSSEL_MODEL, MusselModel::getTexturedModelData);
        event.registerLayerDefinition(MOSS_BALL_MODEL, MossBallModel::getTexturedModelData);
    }

    @SubscribeEvent
    public static void registerScreens(RegisterMenuScreensEvent event) {
        event.register(ShellfishScreens.SHELLFISH_TRAP_SCREEN_HANDLER.get(), ShellfishTrapScreen::new);
    }

    @SubscribeEvent
    public static void addBlockColors(RegisterColorHandlersEvent.Block event) {
        event.register((state, world, pos, tintIndex) -> {
            if (world != null && pos != null) {
                return BiomeColors.getAverageGrassColor(world, pos);
            }
            return GrassColor.getDefaultColor();
        }, ShellfishBlocks.WATER_GRASS.get(), ShellfishBlocks.TALL_WATER_GRASS.get());
    }

    @SubscribeEvent
    public static void addItemColors(RegisterColorHandlersEvent.Item event) {
        event.register((stack, tintIndex) -> GrassColor.getDefaultColor(), ShellfishBlocks.WATER_GRASS.get(), ShellfishBlocks.TALL_WATER_GRASS.get());
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        registerClientEntityRenders();

        ItemBlockRenderTypes.setRenderLayer(ShellfishBlocks.SEA_SNAIL_EGGS_BLOCK.get(), RenderType.translucent());
        for (Block block : ShellfishBlocks.getCutouts()) {
            ItemBlockRenderTypes.setRenderLayer(block, RenderType.cutout());
        }

        BlockEntityRenderers.register(ShellfishBlocks.WATER_LETTUCE_BLOCK_ENTITY.get(), WaterLettuceRenderer::new);
        BlockEntityRenderers.register(ShellfishBlocks.SEA_LETTUCE_BLOCK_ENTITY.get(), SeaLettuceRenderer::new);
        BlockEntityRenderers.register(ShellfishBlocks.SHELLFISH_TRAP_BLOCK_ENTITY.get(), ShellfishTrapRenderer::new);
        BlockEntityRenderers.register(ShellfishBlocks.REINFORCED_TRAP_BLOCK_ENTITY.get(), ShellfishTrapRenderer::new);

        ShellfishConfig.loadConfig();
        ShellfishCrayfish.register();
    }
}
