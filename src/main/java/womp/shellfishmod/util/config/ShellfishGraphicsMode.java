package womp.shellfishmod.util.config;

import java.util.function.IntFunction;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.StringRepresentable;

@Environment(value=EnvType.CLIENT)
public enum ShellfishGraphicsMode implements StringRepresentable
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
    
    public int getId() {
        return this.id;
    }

    public String getTranslationKey() {
        return this.translationKey;
    }

    @Override
    public String getSerializedName() {
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
        BY_ID = ByIdMap.continuous(ShellfishGraphicsMode::getId, ShellfishGraphicsMode.values(), ByIdMap.OutOfBoundsStrategy.WRAP);
    }
}
