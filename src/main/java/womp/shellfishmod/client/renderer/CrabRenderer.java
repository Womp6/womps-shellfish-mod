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
