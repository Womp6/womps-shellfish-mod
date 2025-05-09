package womp.shellfishmod.command;

import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.PersistentStateManager;

public class ShellfishStateUtil {
    
    public static boolean isShellfishPassive(ServerWorld world) {
        PersistentStateManager stateManager = world.getPersistentStateManager();
        PassiveShellfishState passiveShellfishState = stateManager.getOrCreate(
            PassiveShellfishState.TYPE
        );
        return passiveShellfishState.getValue();
    }
}
