package womp.shellfishmod.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import womp.shellfishmod.client.ShellfishClient;
import womp.shellfishmod.client.model.LobsterModel;
import womp.shellfishmod.entity.LobsterEntity;

public class LobsterRenderer extends MobRenderer<LobsterEntity, LobsterModel> {

    public LobsterRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new LobsterModel(renderManager.bakeLayer(ShellfishClient.LOBSTER_MODEL)), 0.4f);
    }

    @Override
    public ResourceLocation getTextureLocation(LobsterEntity animatable) {
        int variant = animatable.getVariant();
        return new ResourceLocation("shellfish", "textures/entity/lobster/lobster_" + variant + ".png");
    }

    @Override
    public void render(LobsterEntity entity, float entityYaw, float partialTick, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLight) {
        if(entity.isBaby()) {
            poseStack.scale(0.5f, 0.5f, 0.5f);
        }

        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
