package womp.shellfishmod.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import womp.shellfishmod.client.ShellfishClient;
import womp.shellfishmod.client.model.CrayfishModel;
import womp.shellfishmod.entity.CrayfishEntity;

public class CrayfishRenderer extends MobRenderer<CrayfishEntity, CrayfishModel> {

    public CrayfishRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new CrayfishModel(renderManager.bakeLayer(ShellfishClient.CRAYFISH_MODEL)), 0.4f);
    }

    @Override
    public ResourceLocation getTextureLocation(CrayfishEntity animatable) {
        int variant = animatable.getVariant();
        if (animatable.hasCustomName() && "Supercrayfish".equals(animatable.getName().getString())) {
            return new ResourceLocation("shellfish", "textures/entity/crayfish/supercrayfish.png");
        } else {
            return new ResourceLocation("shellfish", "textures/entity/crayfish/crayfish_" + variant + ".png");
        }
    }

    @Override
    public void render(CrayfishEntity entity, float entityYaw, float partialTick, PoseStack poseStack,
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
