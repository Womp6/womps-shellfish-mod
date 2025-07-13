package womp.shellfishmod.client.renderer;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import womp.shellfishmod.client.model.CrabModel;
import womp.shellfishmod.entity.CrabEntity;
import womp.shellfishmod.client.ShellfishClient;

public class CrabRenderer extends MobEntityRenderer<CrabEntity, CrabModel> {

    public CrabRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new CrabModel(renderManager.getPart(ShellfishClient.CRAB_MODEL)), 0.3f);
    }

    @Override
    public Identifier getTexture(CrabEntity animatable) {
        int variant = animatable.getVariant().getIndex();
        return Identifier.of("shellfish", "textures/entity/crab/crab_" + variant + ".png");
    }

    @Override
    public void render(CrabEntity entity, float entityYaw, float partialTick, MatrixStack poseStack,
                       VertexConsumerProvider bufferSource, int packedLight) {
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
