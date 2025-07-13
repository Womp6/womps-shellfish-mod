package womp.shellfishmod.client.renderer;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import womp.shellfishmod.client.model.ShrimpModel;
import womp.shellfishmod.entity.ShrimpEntity;
import womp.shellfishmod.client.ShellfishClient;

public class ShrimpRenderer extends MobEntityRenderer<ShrimpEntity, ShrimpModel> {

    public ShrimpRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new ShrimpModel(renderManager.getPart(ShellfishClient.SHRIMP_MODEL)), 0.4f);
    }

    @Override
    public Identifier getTexture(ShrimpEntity animatable) {
        int variant = animatable.getVariant().getIndex();
        return Identifier.of("shellfish", "textures/entity/shrimp/shrimp_" + variant + ".png");
    }

    @Override
    public void render(ShrimpEntity entity, float entityYaw, float partialTick, MatrixStack poseStack,
                       VertexConsumerProvider bufferSource, int packedLight) {

        int variant = entity.getVariant().getIndex();                  
        if (variant == 0) entity.scale(poseStack, 0.75f, 0.6f, 0.5f);
        else if (variant == 2) entity.scale(poseStack, 1.0f, 0.75f, 0.5f);
        else entity.scale(poseStack, 0.45f, 0.25f);

        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
