package womp.shellfishmod.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import womp.shellfishmod.client.ShellfishClient;
import womp.shellfishmod.client.model.MossBallModel;
import womp.shellfishmod.entity.MossBallEntity;

public class MossBallRenderer extends MobRenderer<MossBallEntity, MossBallModel> {

    public MossBallRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new MossBallModel(renderManager.bakeLayer(ShellfishClient.MOSS_BALL_MODEL)), 0f);
    }

    @Override
    public ResourceLocation getTextureLocation(MossBallEntity entity) {
        return ResourceLocation.fromNamespaceAndPath("shellfish", "textures/entity/moss_ball.png");
    }

    @Override
    public void render(MossBallEntity entity, float entityYaw, float partialTick, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLight)  {

        if(entity.getVariant().getIndex() == 0) {
            poseStack.scale(0.5f, 0.5f, 0.5f);
        } else {
            poseStack.scale(0.75f, 0.75f, 0.75f);
        }


        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
