package womp.shellfishmod.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import womp.shellfishmod.client.ShellfishClient;
import womp.shellfishmod.client.model.CrabModel;
import womp.shellfishmod.entity.CrabEntity;

public class CrabRenderer extends MobRenderer<CrabEntity, CrabModel> {

    public CrabRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new CrabModel(renderManager.bakeLayer(ShellfishClient.CRAB_MODEL)), 0.3f);
    }

    @Override
    public ResourceLocation getTextureLocation(CrabEntity animatable) {
        int variant = animatable.getVariant().getIndex();
        return ResourceLocation.fromNamespaceAndPath("shellfish", "textures/entity/crab/crab_" + variant + ".png");
    }

    @Override
    public void render(CrabEntity entity, float entityYaw, float partialTick, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLight) {
        int variant = entity.getVariant().getIndex();
        if (variant == 0) entity.scale(poseStack, 1.1f, 0.85f, 0.4f);
        else if (variant == 1) entity.scale(poseStack, 0.6f, 0.35f);
        else if (variant == 2) entity.scale(poseStack, 0.85f, 0.6f, 0.4f);
        else if (variant == 3) entity.scale(poseStack, 1f, 0.75f, 0.4f);
        else if (variant == 4) entity.scale(poseStack, 0.5f, 0.3f);
        else entity.scale(poseStack, 0.75f, 0.4f);

        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
