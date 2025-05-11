package womp.shellfishmod.command;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.datafix.DataFixTypes;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedDataType;

public class PassiveShellfishState extends SavedData {

    private static final String KEY = "passiveshellfish";
    private boolean passiveshellfish;

    public static final Codec<PassiveShellfishState> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    Codec.BOOL.fieldOf(KEY).forGetter(passiveShellfishState -> passiveShellfishState.passiveshellfish)).apply(instance, PassiveShellfishState::new));

    public static final SavedDataType<PassiveShellfishState> TYPE = new SavedDataType<PassiveShellfishState>(KEY, () -> new PassiveShellfishState(false), CODEC, DataFixTypes.SAVED_DATA_COMMAND_STORAGE);

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
}
