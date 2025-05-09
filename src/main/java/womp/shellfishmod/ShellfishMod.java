package womp.shellfishmod;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
        IEventBus modEventBus = context.getModEventBus();

        ShellfishBlocks.register(modEventBus);
        ShellfishItems.register(modEventBus);
        ShellfishEntities.register(modEventBus);
        ShellfishSounds.register(modEventBus);
        ShellfishGroup.register(modEventBus);
        ShellfishTrapper.register(modEventBus);
        ShellfishFeatures.register(modEventBus);
        ShellfishScreens.register(modEventBus);
        ShellfishRecipes.register(modEventBus);
        ShellfishComponents.register(modEventBus);

        modEventBus.addListener(this::commonSetup);
        MinecraftForge.EVENT_BUS.register(this);
        modEventBus.addListener(this::addCreative);
    }


    private void commonSetup(final FMLCommonSetupEvent event) {
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
    }
}
