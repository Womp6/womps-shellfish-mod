package womp.shellfishmod.screens;

import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

// This file was creating using help from Kaupenjoe
public class ShellfishTrapScreen extends HandledScreen<ShellfishTrapScreenHandler> {

    public static final Identifier TEXTURE = Identifier.of("shellfish", "textures/gui/shellfish_trap.png");

    public Text durability;

    public ShellfishTrapScreen(ShellfishTrapScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        durability = Text.literal(Text.translatable("shellfish_trap.durability").getString() + handler.getDurability());
    }

    @Override
    protected void init() {
        super.init();
        titleX = (this.backgroundWidth - this.textRenderer.getWidth(this.title)) / 2;
    }

    @Override
    protected void drawBackground(DrawContext context, float var2, int var3, int var4) {
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;

        context.drawTexture(TEXTURE, x, y, 0, 0, backgroundWidth, backgroundHeight);
        renderProgressArrow(context, x, y);
        renderDurabilityBar(context, x, y);
    }

    public void renderProgressArrow(DrawContext context, int x, int y) {
        if (handler.isTrapping()) {
            context.drawTexture(TEXTURE, x + 32, y + 38, 176, 0, handler.getScaledProgress(), 10);
        }
    }

    @Override
    public void drawMouseoverTooltip(DrawContext context, int mouseX, int mouseY) {
        super.drawMouseoverTooltip(context, mouseX, mouseY);
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        durability = Text.literal(Text.translatable("shellfish_trap.durability").getString() + handler.getDurability() + " / " + handler.getMaxDurability());
        if (mouseX >= 11 + x && mouseX <= 52 + x && mouseY >= 16 + y && mouseY <= 23 + y) {
            context.drawTooltip(textRenderer, this.durability, mouseX, mouseY);
        }
    }

    public void renderDurabilityBar(DrawContext context, int x, int y) {
        if (handler.getDurability() > handler.getMaxDurability() / 2) {
            context.drawTexture(TEXTURE, x + 12, y + 16, 177, 10, handler.getScaledDurability(), 8);
        } else if (handler.getDurability() > handler.getMaxDurability() / 5) {
            context.drawTexture(TEXTURE, x + 12, y + 16, 177, 18, handler.getScaledDurability(), 8);
        } else {
            context.drawTexture(TEXTURE, x + 12, y + 16, 177, 26, handler.getScaledDurability(), 8);
        }

        if (handler.getDurability() != 0) {
            context.drawTexture(TEXTURE, x + 11, y + 16, 176, 10, 1, 8);
        }
        if (handler.getDurability() == handler.getMaxDurability()) {
            context.drawTexture(TEXTURE, x + 52, y + 16, 217, 10, 1, 8);
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context, mouseX, mouseY, delta);
        super.render(context, mouseX, mouseY, delta);
        drawMouseoverTooltip(context, mouseX, mouseY);
    }   
}
