package womp.shellfishmod.screens;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;

// This file was creating using help from Kaupenjoe
public class ShellfishTrapScreen extends AbstractContainerScreen<ShellfishTrapScreenHandler> {

    public static final Identifier TEXTURE = Identifier.fromNamespaceAndPath("shellfish", "textures/gui/shellfish_trap.png");

    public Component durability;

    public ShellfishTrapScreen(ShellfishTrapScreenHandler handler, Inventory inventory, Component title) {
        super(handler, inventory, title);
        durability = Component.literal(Component.translatable("shellfish_trap.durability").getString() + handler.getDurability());
    }

    @Override
    protected void init() {
        super.init();
        titleLabelX = (this.imageWidth - this.font.width(this.title)) / 2;
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor context, int mouseX, int mouseY, float delta) {
        super.extractBackground(context, mouseX, mouseY, delta);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        context.blit(RenderPipelines.GUI_TEXTURED, TEXTURE, x, y, 0, 0, imageWidth, imageHeight, 256, 256);
        renderProgressArrow(context, x, y);
        renderDurabilityBar(context, x, y);
    }

    public void renderProgressArrow(GuiGraphicsExtractor context, int x, int y) {
        if (menu.isTrapping()) {
            context.blit(RenderPipelines.GUI_TEXTURED, TEXTURE, x + 32, y + 38, 176, 0, menu.getScaledProgress(), 10, 256, 256);
        }
    }

    @Override
    public void extractTooltip(GuiGraphicsExtractor context, int mouseX, int mouseY) {
        super.extractTooltip(context, mouseX, mouseY);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        durability = Component.literal(Component.translatable("shellfish_trap.durability").getString() + menu.getDurability() + " / " + menu.getMaxDurability());
        if (mouseX >= 11 + x && mouseX <= 52 + x && mouseY >= 16 + y && mouseY <= 23 + y) {
            context.setTooltipForNextFrame(font, this.durability, mouseX, mouseY);
        }
    }

    public void renderDurabilityBar(GuiGraphicsExtractor context, int x, int y) {
        if (menu.getDurability() > menu.getMaxDurability() / 2) {
            context.blit(RenderPipelines.GUI_TEXTURED, TEXTURE, x + 12, y + 16, 177, 10, menu.getScaledDurability(), 8, 256, 256);
        } else if (menu.getDurability() > menu.getMaxDurability() / 5) {
            context.blit(RenderPipelines.GUI_TEXTURED, TEXTURE, x + 12, y + 16, 177, 18, menu.getScaledDurability(), 8, 256, 256);
        } else {
            context.blit(RenderPipelines.GUI_TEXTURED, TEXTURE, x + 12, y + 16, 177, 26, menu.getScaledDurability(), 8, 256, 256);
        }

        if (menu.getDurability() != 0) {
            context.blit(RenderPipelines.GUI_TEXTURED, TEXTURE, x + 11, y + 16, 176, 10, 1, 8, 256, 256);
        }
        if (menu.getDurability() == menu.getMaxDurability()) {
            context.blit(RenderPipelines.GUI_TEXTURED, TEXTURE, x + 52, y + 16, 217, 10, 1, 8, 256, 256);
        }
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor context, int mouseX, int mouseY, float delta) {
        extractBackground(context, mouseX, mouseY, delta);
        super.extractRenderState(context, mouseX, mouseY, delta);
        extractTooltip(context, mouseX, mouseY);
    }
}
