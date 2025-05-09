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
