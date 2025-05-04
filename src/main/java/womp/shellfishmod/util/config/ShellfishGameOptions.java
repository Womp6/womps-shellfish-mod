package womp.shellfishmod.util.config;

import com.mojang.serialization.Codec;
import java.io.File;
import java.util.Arrays;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;

public class ShellfishGameOptions extends GameOptions {

    public ShellfishGameOptions(MinecraftClient client, File optionsFile) {
        super(client, optionsFile);
    }

    private static final Text FAST_GRAPHICS_TOOLTIP = Text.translatable("shellfish_options.graphics.fast.tooltip");
    private static final Text DEFAULT_GRAPHICS_TOOLTIP = Text.translatable("shellfish_options.graphics.default.tooltip");
    private static final Text FANCY_GRAPHICS_TOOLTIP = Text.translatable("shellfish_options.graphics.fancy.tooltip");
    private static final SimpleOption<ShellfishGraphicsMode> shellfishGraphicsMode = new SimpleOption<ShellfishGraphicsMode>("shellfish_options.graphics", value -> switch (value) {
        default -> throw new IncompatibleClassChangeError();
        case FAST -> Tooltip.of(FAST_GRAPHICS_TOOLTIP);
        case DEFAULT -> Tooltip.of(DEFAULT_GRAPHICS_TOOLTIP);
        case FANCY -> Tooltip.of(FANCY_GRAPHICS_TOOLTIP);
    }, (optionText, value) -> {
        MutableText mutableText = Text.translatable(value.getTranslationKey());
        return mutableText;
    }, new SimpleOption.PotentialValuesBasedCallbacks<ShellfishGraphicsMode>(Arrays.asList(ShellfishGraphicsMode.values()), Codec.INT.xmap(ShellfishGraphicsMode::byId, ShellfishGraphicsMode::getId)), ShellfishGraphicsMode.DEFAULT, value -> {
        ShellfishConfig.setShellfishGraphics(value.getId());
    });
    
    public static SimpleOption<ShellfishGraphicsMode> getShellfishGraphicsMode() {
        return shellfishGraphicsMode;
    }
}