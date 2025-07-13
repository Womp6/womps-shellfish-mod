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
    public void render(OysterEntity entity, float f, float g, PoseStack poseStack,
                       MultiBufferSource vertexConsumerProvider, int i) {
        int variant = entity.getVariant().getIndex();
        if (variant == 0) entity.scale(poseStack, 0.8f, 0.4f);
        else if (variant == 2 || variant == 3) entity.scale(poseStack, 1.2f, 0.85f, 0.5f);
        else entity.scale(poseStack, 1.0f, 0.5f);

        super.render(entity, f, g, poseStack, vertexConsumerProvider, i);
    }
}
