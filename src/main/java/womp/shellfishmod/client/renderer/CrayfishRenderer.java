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
        int variant = animatable.getVariant().getIndex();
        if (animatable.hasCustomName() && "Supercrayfish".equals(animatable.getName().getString())) {
            return ResourceLocation.fromNamespaceAndPath("shellfish", "textures/entity/crayfish/supercrayfish.png");
        } else {
            return ResourceLocation.fromNamespaceAndPath("shellfish", "textures/entity/crayfish/crayfish_" + variant + ".png");
        }
    }

    @Override
    public void render(CrayfishEntity entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {

        int variant = entity.getVariant().getIndex();
        if (variant == 11 || variant == 17 || variant == 25) entity.scale(poseStack, 0.9f, 0.65f, 0.4f);
        else if (variant == 18) entity.scale(poseStack, 1.4f, 0.9f, 0.4f);
        else if (variant == 10 || variant == 21) entity.scale(poseStack, 0.4f, 0.2f);
        else if (variant == 14 || variant == 19) entity.scale(poseStack, 0.6f, 0.3f);
        else if (variant == 23) entity.scale(poseStack, 0.5f, 0.25f);
        else entity.scale(poseStack, 0.75f, 0.4f);

        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
