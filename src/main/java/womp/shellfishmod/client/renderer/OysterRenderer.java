package womp.shellfishmod.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import womp.shellfishmod.client.ShellfishClient;
import womp.shellfishmod.client.model.OysterModel;
import womp.shellfishmod.entity.OysterEntity;

public class OysterRenderer extends MobRenderer<OysterEntity, OysterModel> {

    public OysterRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new OysterModel(ctx.bakeLayer(ShellfishClient.OYSTER_MODEL)), 0.3f);
    }

    @Override
    public ResourceLocation getTextureLocation(OysterEntity var1) {
        int variant = var1.getVariant().getIndex();
        return ResourceLocation.fromNamespaceAndPath("shellfish", "textures/entity/oyster/oyster_" + variant + ".png");
    }

    @Override
    public void render(OysterEntity livingEntity, float f, float g, PoseStack matrixStack,
                       MultiBufferSource vertexConsumerProvider, int i) {
        if(livingEntity.isBaby()) {
            matrixStack.scale(0.5f, 0.5f, 0.5f);
        }

        super.render(livingEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }
}
