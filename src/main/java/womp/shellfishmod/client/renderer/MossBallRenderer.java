package womp.shellfishmod.client.renderer;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import womp.shellfishmod.client.model.MossBallModel;
import womp.shellfishmod.entity.MossBallEntity;
import womp.shellfishmod.client.ShellfishClient;

public class MossBallRenderer extends MobEntityRenderer<MossBallEntity, MossBallModel> {

    public MossBallRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new MossBallModel(renderManager.getPart(ShellfishClient.MOSS_BALL_MODEL)), 0f);
    }

    @Override
    public Identifier getTexture(MossBallEntity entity) {
        return new Identifier("shellfish", "textures/entity/moss_ball.png");
    }

    @Override
    public void render(MossBallEntity entity, float entityYaw, float partialTick, MatrixStack poseStack,
                       VertexConsumerProvider bufferSource, int packedLight)  {

        if(entity.getVariant().getIndex() == 1) {
            poseStack.scale(0.5f, 0.5f, 0.5f);
        } else {
            poseStack.scale(0.75f, 0.75f, 0.75f);
        }


        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
