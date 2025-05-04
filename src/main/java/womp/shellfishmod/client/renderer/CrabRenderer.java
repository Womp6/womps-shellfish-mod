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
        int variant = animatable.getVariant();
        return new ResourceLocation("shellfish", "textures/entity/crab/crab_" + variant + ".png");
    }

    @Override
    public void render(CrabEntity entity, float entityYaw, float partialTick, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLight) {
        if(entity.isBaby()) {
            poseStack.scale(0.4f, 0.4f, 0.4f);
        }
        if(entity.isAlive() && !entity.isBaby()) {
            poseStack.scale(0.75f, 0.75f, 0.75f);
        }
        if(!entity.isAlive() && !entity.isBaby()) {
            poseStack.scale(0.75f, 0.75f, 0.75f);
        }

        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
