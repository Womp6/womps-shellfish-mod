package womp.shellfishmod.command;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.PersistentState;

public class PassiveShellfishState extends PersistentState {

    private static final String KEY = "passiveshellfish";
    private boolean passiveshellfish;

    public PassiveShellfishState(boolean value) {
        this.passiveshellfish = value;
    }

    public boolean getValue() {
        return passiveshellfish;
    }

    public void setValue(boolean value) {
        this.passiveshellfish = value;
        markDirty();
    }

    @Override
    public NbtCompound writeNbt(NbtCompound nbt) {
        nbt.putBoolean(KEY, passiveshellfish);
        return nbt;
    }

    public static PassiveShellfishState fromNbt(NbtCompound nbt) {
        return new PassiveShellfishState(nbt.getBoolean(KEY));
    }
}
