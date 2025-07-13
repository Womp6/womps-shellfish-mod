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
        int variant = var1.getVariant().getIndex();
        return ResourceLocation.fromNamespaceAndPath("shellfish", "textures/entity/mussel/mussel_" + variant + ".png");
    }

    @Override
    public void render(MusselEntity entity, float f, float g, PoseStack poseStack,
                       MultiBufferSource vertexConsumerProvider, int i) {
        int variant = entity.getVariant().getIndex();
        if (variant == 1) entity.scale(poseStack, 0.95f, 0.65f, 0.4f);
        else if (variant == 2) entity.scale(poseStack, 1.1f, 0.8f, 0.4f);
        else if (variant == 3) entity.scale(poseStack, 0.55f, 0.3f);
        else entity.scale(poseStack, 0.8f, 0.4f);

        super.render(entity, f, g, poseStack, vertexConsumerProvider, i);
    }
}
