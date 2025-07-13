package womp.shellfishmod.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import womp.shellfishmod.client.ShellfishClient;
import womp.shellfishmod.client.model.SeaUrchinModel;
import womp.shellfishmod.entity.SeaUrchinEntity;

public class SeaUrchinRenderer extends MobRenderer<SeaUrchinEntity, SeaUrchinModel> {

    public SeaUrchinRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new SeaUrchinModel(renderManager.bakeLayer(ShellfishClient.SEA_URCHIN_MODEL)), 0.125f);
    }

    @Override
    public ResourceLocation getTextureLocation(SeaUrchinEntity animatable) {
        int variant = animatable.getVariant().getIndex();
        return ResourceLocation.fromNamespaceAndPath("shellfish", "textures/entity/sea_urchin/sea_urchin_" + variant + ".png");
    }

    @Override
    public void render(SeaUrchinEntity entity, float entityYaw, float partialTick, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLight) {
        int variant = entity.getVariant().getIndex();
        if (variant == 1) entity.scale(poseStack, 2f, 1.2f, 0.5f);
        else if (variant == 3) entity.scale(poseStack, 0.6f, 0.3f);
        else if (variant == 4) entity.scale(poseStack, 1.5f, 1.0f, 0.5f);
        else entity.scale(poseStack, 1.0f, 0.5f);

        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
