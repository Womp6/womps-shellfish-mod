package womp.shellfishmod;

import net.minecraft.world.level.block.ComposterBlock;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.bus.BusGroup;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import womp.shellfishmod.compat.terrablender.TerraBlender;
import womp.shellfishmod.registry.ShellfishFeatures;
import womp.shellfishmod.registry.ShellfishRecipes;
import womp.shellfishmod.registry.*;
import womp.shellfishmod.registry.ShellfishScreens;
import womp.shellfishmod.villagers.ShellfishTrapper;

@Mod(ShellfishMod.MOD_ID)
public class ShellfishMod {
    public static final String MOD_ID = "shellfish";
    public static final Logger LOGGER = LoggerFactory.getLogger(ShellfishMod.class);

    public ShellfishMod(FMLJavaModLoadingContext context) {
        var modEventBus = context.getModBusGroup();

        ShellfishBlocks.register(modEventBus);
        ShellfishItems.register(modEventBus);
        ShellfishEntities.register(modEventBus);
        ShellfishSounds.register(modEventBus);
        ShellfishGroup.register(modEventBus);
        ShellfishWorldgen.register(modEventBus);
        ShellfishTrapper.register(modEventBus);
        ShellfishFeatures.register(modEventBus);
        ShellfishScreens.register(modEventBus);
        ShellfishRecipes.register(modEventBus);
        ShellfishComponents.register(modEventBus);

        // TerraBlender compatibility (optional)
        if (ModList.isLoaded("terrablender")) TerraBlender.register();

        FMLCommonSetupEvent.getBus(modEventBus).addListener(this::commonSetup);
    }


    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            ComposterBlock.COMPOSTABLES.put(ShellfishItems.ROCKWEED.get(), 0.3f);
            ComposterBlock.COMPOSTABLES.put(ShellfishItems.WATER_LETTUCE.get(), 0.65f);
            ComposterBlock.COMPOSTABLES.put(ShellfishItems.EELGRASS.get(), 0.3f);
            ComposterBlock.COMPOSTABLES.put(ShellfishItems.PADDLEWEED.get(), 0.3f);
            ComposterBlock.COMPOSTABLES.put(ShellfishItems.SEA_LETTUCE.get(), 0.3f);
            ComposterBlock.COMPOSTABLES.put(ShellfishItems.CATTAIL.get(), 0.65f);
            ComposterBlock.COMPOSTABLES.put(ShellfishItems.TALL_CATTAIL.get(), 0.65f);
            ComposterBlock.COMPOSTABLES.put(ShellfishItems.PICKERELWEED.get(), 0.65f);
            ComposterBlock.COMPOSTABLES.put(ShellfishItems.TALL_PICKERELWEED.get(), 0.65f);
            ComposterBlock.COMPOSTABLES.put(ShellfishItems.WHEATGRASS.get(), 0.5f);
            ComposterBlock.COMPOSTABLES.put(ShellfishItems.TALL_WHEATGRASS.get(), 0.65f);
            ComposterBlock.COMPOSTABLES.put(ShellfishItems.WATER_GRASS.get(), 0.3f);
            ComposterBlock.COMPOSTABLES.put(ShellfishItems.TALL_WATER_GRASS.get(), 0.5f);
            ComposterBlock.COMPOSTABLES.put(ShellfishItems.SHELLFISH_BAIT.get(), 0.3f);
            ComposterBlock.COMPOSTABLES.put(ShellfishItems.DRIED_SHELLFISH_BAIT.get(), 0.3f);
        });
    }
}
