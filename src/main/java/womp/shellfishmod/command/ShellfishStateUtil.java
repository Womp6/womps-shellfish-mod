package womp.shellfishmod.command;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.storage.SavedDataStorage;

public class ShellfishStateUtil {

    public static boolean isShellfishPassive(ServerLevel world) {
        SavedDataStorage stateManager = world.getDataStorage();
        PassiveShellfishState passiveShellfishState = stateManager.computeIfAbsent(
                PassiveShellfishState.TYPE
        );
        return passiveShellfishState.getValue();
    }
}
