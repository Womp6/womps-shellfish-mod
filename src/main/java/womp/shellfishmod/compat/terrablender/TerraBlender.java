package womp.shellfishmod.compat.terrablender;

import net.neoforged.fml.ModList;
import terrablender.api.Regions;
import womp.shellfishmod.ShellfishMod;

public class TerraBlender {

    public static void register() {
        if (!ModList.get().isLoaded("terrablender")) return;
        Regions.register(new MarshRegion());
    }
}
