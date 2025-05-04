package womp.shellfishmod.command;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.datafix.DataFixTypes;
import net.minecraft.world.level.saveddata.SavedData;

public class PassiveShellfishState extends SavedData {

    private static final String KEY = "passiveshellfish";
    private boolean passiveshellfish;

    public static final Factory<PassiveShellfishState> TYPE = new SavedData.Factory<PassiveShellfishState>(() -> new PassiveShellfishState(false), PassiveShellfishState::fromNbt, DataFixTypes.SAVED_DATA_COMMAND_STORAGE);

    public PassiveShellfishState(boolean value) {
        this.passiveshellfish = value;
    }

    public boolean getValue() {
        return passiveshellfish;
    }

    public void setValue(boolean value) {
        this.passiveshellfish = value;
        setDirty();
    }

    @Override
    public CompoundTag save(CompoundTag nbt) {
        nbt.putBoolean(KEY, passiveshellfish);
        return nbt;
    }

    public static PassiveShellfishState fromNbt(CompoundTag nbt) {
        return new PassiveShellfishState(nbt.getBoolean(KEY));
    }
}
