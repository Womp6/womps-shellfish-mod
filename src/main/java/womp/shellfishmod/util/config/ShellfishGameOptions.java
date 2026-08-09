package womp.shellfishmod.util.config;

import com.mojang.serialization.Codec;
import java.io.File;
import java.util.Arrays;
import net.minecraft.client.Minecraft;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.Options;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

public class ShellfishGameOptions extends Options {

    public ShellfishGameOptions(Minecraft client, File optionsFile) {
        super(client, optionsFile);
    }

    private static final Component FAST_GRAPHICS_TOOLTIP = Component.translatable("shellfish_options.graphics.fast.tooltip");
    private static final Component DEFAULT_GRAPHICS_TOOLTIP = Component.translatable("shellfish_options.graphics.default.tooltip");
    private static final Component FANCY_GRAPHICS_TOOLTIP = Component.translatable("shellfish_options.graphics.fancy.tooltip");
    private static final OptionInstance<ShellfishGraphicsMode> shellfishGraphicsMode = new OptionInstance<ShellfishGraphicsMode>("shellfish_options.graphics", value -> switch (value) {
        default -> throw new IncompatibleClassChangeError();
        case FAST -> Tooltip.create(FAST_GRAPHICS_TOOLTIP);
        case DEFAULT -> Tooltip.create(DEFAULT_GRAPHICS_TOOLTIP);
        case FANCY -> Tooltip.create(FANCY_GRAPHICS_TOOLTIP);
    }, (optionText, value) -> {
        MutableComponent mutableText = Component.translatable(value.getTranslationKey());
        return mutableText;
    }, new OptionInstance.Enum<ShellfishGraphicsMode>(Arrays.asList(ShellfishGraphicsMode.values()), Codec.INT.xmap(ShellfishGraphicsMode::byId, ShellfishGraphicsMode::getId)), ShellfishGraphicsMode.DEFAULT, value -> {
        ShellfishConfig.setShellfishGraphics(value.getId());
    });
    
    public static OptionInstance<ShellfishGraphicsMode> getShellfishGraphicsMode() {
        return shellfishGraphicsMode;
    }
}