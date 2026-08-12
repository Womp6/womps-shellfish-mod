package womp.shellfishmod.client;

import net.minecraft.client.color.block.BlockTintSource;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.block.BlockAndTintGetter;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.chunk.ChunkSectionLayer;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.GrassColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import womp.shellfishmod.ShellfishMod;
import womp.shellfishmod.client.model.*;
import womp.shellfishmod.client.model.snail.*;
import womp.shellfishmod.client.renderer.*;
import womp.shellfishmod.util.config.ShellfishConfig;
import womp.shellfishmod.registry.ShellfishBlocks;
import womp.shellfishmod.registry.ShellfishEntities;
import womp.shellfishmod.registry.ShellfishScreens;
import womp.shellfishmod.screens.ShellfishTrapScreen;

import java.util.List;

@Mod.EventBusSubscriber(modid = ShellfishMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.BOTH, value = Dist.CLIENT)
public class ShellfishClient {

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
        event.registerLayerDefinition(SEA_SNAIL_MODEL1, SeaSnailModelV1::getTexturedModelData);
        event.registerLayerDefinition(SEA_SNAIL_MODEL2, SeaSnailModelV2::getTexturedModelData);
        event.registerLayerDefinition(SEA_SNAIL_MODEL3, SeaSnailModelV3::getTexturedModelData);
        event.registerLayerDefinition(SEA_SNAIL_MODEL4, SeaSnailModelV4::getTexturedModelData);
        event.registerLayerDefinition(SEA_SNAIL_MODEL5, SeaSnailModelV5::getTexturedModelData);
        event.registerLayerDefinition(SEA_URCHIN_MODEL, SeaUrchinModel::getTexturedModelData);
        event.registerLayerDefinition(CLAM_MODEL, ClamModel::getTexturedModelData);
        event.registerLayerDefinition(OYSTER_MODEL, OysterModel::getTexturedModelData);
        event.registerLayerDefinition(MUSSEL_MODEL, MusselModel::getTexturedModelData);
        event.registerLayerDefinition(MOSS_BALL_MODEL, MossBallModel::getTexturedModelData);
    }

    @SubscribeEvent
    public static void addBlockColors(RegisterColorHandlersEvent.Block event) {
        event.register(List.of(new BlockTintSource() {
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
        }), ShellfishBlocks.WATER_GRASS.get(), ShellfishBlocks.TALL_WATER_GRASS.get());
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        registerClientEntityRenders();

        BlockEntityRenderers.register(ShellfishBlocks.WATER_LETTUCE_BLOCK_ENTITY.get(), WaterLettuceRenderer::new);
        BlockEntityRenderers.register(ShellfishBlocks.SEA_LETTUCE_BLOCK_ENTITY.get(), SeaLettuceRenderer::new);
        BlockEntityRenderers.register(ShellfishBlocks.SHELLFISH_TRAP_BLOCK_ENTITY.get(), ShellfishTrapRenderer::new);
        BlockEntityRenderers.register(ShellfishBlocks.REINFORCED_TRAP_BLOCK_ENTITY.get(), ShellfishTrapRenderer::new);

        MenuScreens.register(ShellfishScreens.SHELLFISH_TRAP_SCREEN_HANDLER.get(), ShellfishTrapScreen::new);

        ShellfishConfig.loadConfig();
    }
}
