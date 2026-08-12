package womp.shellfishmod.compat.terrablender;

import net.minecraftforge.fml.ModList;
import terrablender.api.Regions;

public class TerraBlender {

    public static void register() {
        if (!ModList.isLoaded("terrablender")) return;
        Regions.register(new MarshRegion());
    }
}
