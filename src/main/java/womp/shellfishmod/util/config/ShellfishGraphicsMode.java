package womp.shellfishmod.util.config;

import java.util.function.IntFunction;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.TranslatableOption;
import net.minecraft.util.function.ValueLists;

@Environment(value=EnvType.CLIENT)
public enum ShellfishGraphicsMode implements TranslatableOption
{
    FAST(0, "shellfish_options.graphics.fast"),
    DEFAULT(1, "shellfish_options.graphics.default"),
    FANCY(2, "shellfish_options.graphics.fancy");

    private static final IntFunction<ShellfishGraphicsMode> BY_ID;
    private final int id;
    private final String translationKey;

    private ShellfishGraphicsMode(int id, String translationKey) {
        this.id = id;
        this.translationKey = translationKey;
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public String getTranslationKey() {
        return this.translationKey;
    }

    public String toString() {
        return switch (this) {
            default -> throw new IncompatibleClassChangeError();
            case FAST -> "fast";
            case DEFAULT -> "default";
            case FANCY -> "fancy";
        };
    }

    public static ShellfishGraphicsMode byId(int id) {
        return BY_ID.apply(id);
    }

    static {
        BY_ID = ValueLists.createIndexToValueFunction(ShellfishGraphicsMode::getId, ShellfishGraphicsMode.values(), ValueLists.OutOfBoundsHandling.WRAP);
    }
}
