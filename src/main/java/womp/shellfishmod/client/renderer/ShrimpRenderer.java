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
        int variant = animatable.getVariant().getIndex();
        return ResourceLocation.fromNamespaceAndPath("shellfish", "textures/entity/shrimp/shrimp_" + variant + ".png");
    }

    @Override
    public void render(ShrimpEntity entity, float entityYaw, float partialTick, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLight) {
        if(entity.isBaby()) {
            poseStack.scale(0.3f, 0.3f, 0.3f);
        }
        if(entity.isAlive() && !entity.isBaby()) {
            poseStack.scale(0.65f, 0.65f, 0.65f);
        }
        if(!entity.isAlive() && !entity.isBaby()) {
            poseStack.scale(0.65f, 0.65f, 0.65f);
        }

        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}