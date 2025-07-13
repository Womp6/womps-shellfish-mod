package womp.shellfishmod.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import womp.shellfishmod.client.ShellfishClient;
import womp.shellfishmod.client.model.ShrimpModel;
import womp.shellfishmod.entity.ShrimpEntity;

public class ShrimpRenderer extends MobRenderer<ShrimpEntity, ShrimpModel> {

    public ShrimpRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new ShrimpModel(renderManager.bakeLayer(ShellfishClient.SHRIMP_MODEL)), 0.4f);
    }

    @Override
    public ResourceLocation getTextureLocation(ShrimpEntity animatable) {
        int variant = animatable.getVariant();
        return new ResourceLocation("shellfish", "textures/entity/shrimp/shrimp_" + variant + ".png");
    }

    @Override
    public void render(ShrimpEntity entity, float entityYaw, float partialTick, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLight) {

        int variant = entity.getVariant();
        if (variant == 0) entity.scale(poseStack, 0.75f, 0.6f, 0.5f);
        else if (variant == 2) entity.scale(poseStack, 1.0f, 0.75f, 0.5f);
        else entity.scale(poseStack, 0.45f, 0.25f);

        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}