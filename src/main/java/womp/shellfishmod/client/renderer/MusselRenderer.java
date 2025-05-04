package womp.shellfishmod.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import womp.shellfishmod.client.ShellfishClient;
import womp.shellfishmod.client.model.MusselModel;
import womp.shellfishmod.entity.MusselEntity;

public class MusselRenderer extends MobRenderer<MusselEntity, MusselModel> {

    public MusselRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new MusselModel(ctx.bakeLayer(ShellfishClient.MUSSEL_MODEL)), 0.3f);
    }

    @Override
    public ResourceLocation getTextureLocation(MusselEntity var1) {
        int variant = var1.getVariant();
        return new ResourceLocation("shellfish", "textures/entity/mussel/mussel_" + variant + ".png");
    }

    @Override
    public void render(MusselEntity livingEntity, float f, float g, PoseStack matrixStack,
                       MultiBufferSource vertexConsumerProvider, int i) {
        int variant = livingEntity.getVariant();

        if(livingEntity.isBaby() && livingEntity.getVariant() != 3) {
            matrixStack.scale(0.4f, 0.4f, 0.4f);
        }

        if(!livingEntity.isBaby() && livingEntity.getVariant() != 3) {
            matrixStack.scale(0.8f, 0.8f, 0.8f);
        }

        if(livingEntity.isBaby() && livingEntity.getVariant() == 3) {
            matrixStack.scale(0.35f, 0.35f, 0.35f);
        }

        if(variant == 3 && !livingEntity.isBaby()) {
            matrixStack.scale(0.55f, 0.55f, 0.55f);
        }

        super.render(livingEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }
}
