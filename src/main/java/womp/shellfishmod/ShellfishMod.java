package womp.shellfishmod;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
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
    public static boolean loaded = false;
    public static final Logger LOGGER = LoggerFactory.getLogger(ShellfishMod.class);

    public ShellfishMod(IEventBus modEventBus, ModContainer modContainer) {

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

        modEventBus.addListener(this::commonSetup);
        NeoForge.EVENT_BUS.register(this);
        modEventBus.addListener(this::addCreative);
    }


    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            loaded = true;
            // TerraBlender compatibility (optional)
            if (ModList.get().isLoaded("terrablender")) TerraBlender.register();
        });
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
    }
}
