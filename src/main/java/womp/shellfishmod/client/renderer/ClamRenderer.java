package womp.shellfishmod.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import womp.shellfishmod.client.ShellfishClient;
import womp.shellfishmod.client.model.ClamModel;
import womp.shellfishmod.entity.ClamEntity;

public class ClamRenderer extends MobRenderer<ClamEntity, ClamModel> {

    public ClamRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new ClamModel(renderManager.bakeLayer(ShellfishClient.CLAM_MODEL)), 0.25f);
    }

    @Override
    public ResourceLocation getTextureLocation(ClamEntity animatable) {
        int variant = animatable.getVariant();
        return new ResourceLocation("shellfish", "textures/entity/clam/clam_" + variant + ".png");
    }

    @Override
    public void render(ClamEntity entity, float entityYaw, float partialTick, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLight) {

        int variant = entity.getVariant();
        if (variant == 1) entity.scale(poseStack, 1.1f, 0.8f, 0.5f);
        else if (variant == 2) entity.scale(poseStack, 0.85f, 0.45f);
        else if (variant == 3 || variant == 4) entity.scale(poseStack, 0.65f, 0.35f);
        else entity.scale(poseStack, 1.0f, 0.5f);

        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
