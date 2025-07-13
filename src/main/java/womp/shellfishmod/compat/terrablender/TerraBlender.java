package womp.shellfishmod.compat.terrablender;

import net.fabricmc.loader.api.FabricLoader;
import terrablender.api.Regions;
import terrablender.api.TerraBlenderApi;

public class TerraBlender implements TerraBlenderApi {
    
    @Override
    public void onTerraBlenderInitialized() {
        if (!FabricLoader.getInstance().isModLoaded("terrablender")) return;
        Regions.register(new MarshRegion());
    }
}
