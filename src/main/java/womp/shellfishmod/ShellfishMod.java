package womp.shellfishmod;

import net.fabricmc.api.ModInitializer;
import womp.shellfishmod.compat.BiomesOPlenty;
import womp.shellfishmod.registry.ShellfishBlocks;
import womp.shellfishmod.registry.ShellfishUtil;
import womp.shellfishmod.registry.ShellfishEntities;
import womp.shellfishmod.registry.ShellfishFeatures;
import womp.shellfishmod.registry.ShellfishGroup;
import womp.shellfishmod.registry.ShellfishItems;
import womp.shellfishmod.registry.ShellfishRecipes;
import womp.shellfishmod.registry.ShellfishSounds;
import womp.shellfishmod.registry.ShellfishWorldgen;
import womp.shellfishmod.registry.ShellfishScreens;
import womp.shellfishmod.villagers.ShellfishTrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShellfishMod implements ModInitializer {
    
    public static final Logger LOGGER = LoggerFactory.getLogger(ShellfishMod.class);

    @Override
    public void onInitialize() {
		ShellfishBlocks.register();
		ShellfishItems.register();
        ShellfishEntities.register();
		ShellfishGroup.register();
		ShellfishTrapper.register();
		ShellfishSounds.register();
		ShellfishFeatures.register();
		ShellfishWorldgen.register();
		ShellfishScreens.register();
		ShellfishRecipes.register();
		ShellfishUtil.register();
		
		//When using Biomes O' Plenty
		BiomesOPlenty.registerExtraSpawns();
    }
}