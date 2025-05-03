package womp.shellfishmod.command;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.storage.DimensionDataStorage;

public class ShellfishStateUtil {

    public static boolean isShellfishPassive(ServerLevel world) {
        DimensionDataStorage stateManager = world.getDataStorage();
        PassiveShellfishState passiveShellfishState = stateManager.computeIfAbsent(
                PassiveShellfishState::fromNbt,
                () -> new PassiveShellfishState(false),
                "passiveshellfish"
        );
        return passiveShellfishState.getValue();
    }
}
