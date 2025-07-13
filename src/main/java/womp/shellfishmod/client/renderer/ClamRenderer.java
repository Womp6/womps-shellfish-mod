package womp.shellfishmod.client.renderer;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import womp.shellfishmod.client.ShellfishClient;
import womp.shellfishmod.client.model.ClamModel;
import womp.shellfishmod.entity.ClamEntity;

public class ClamRenderer extends MobEntityRenderer<ClamEntity, ClamModel> {
    
    public ClamRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new ClamModel(renderManager.getPart(ShellfishClient.CLAM_MODEL)), 0.25f);
    }

    @Override
    public Identifier getTexture(ClamEntity animatable) {
        int variant = animatable.getVariant();
        return new Identifier("shellfish", "textures/entity/clam/clam_" + variant + ".png");
    }

    @Override
    public void render(ClamEntity entity, float entityYaw, float partialTick, MatrixStack poseStack,
                       VertexConsumerProvider bufferSource, int packedLight) {

        int variant = entity.getVariant();                  
        if (variant == 1) entity.scale(poseStack, 1.1f, 0.8f, 0.5f);
        else if (variant == 2) entity.scale(poseStack, 0.85f, 0.45f);
        else if (variant == 3 || variant == 4) entity.scale(poseStack, 0.65f, 0.35f);
        else entity.scale(poseStack, 1.0f, 0.5f);

        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
