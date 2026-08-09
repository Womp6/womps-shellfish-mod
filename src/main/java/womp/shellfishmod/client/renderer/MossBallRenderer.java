package womp.shellfishmod.client.renderer;

import womp.shellfishmod.client.model.MossBallModel;
import womp.shellfishmod.client.states.ShellfishRenderState;
import womp.shellfishmod.entity.MossBallEntity;
import womp.shellfishmod.entity.MossBallEntity.Variant;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.resources.Identifier;
import womp.shellfishmod.client.ShellfishClient;

public class MossBallRenderer extends MobRenderer<MossBallEntity, ShellfishRenderState<MossBallEntity.Variant>, MossBallModel> {

    public MossBallRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new MossBallModel(renderManager.bakeLayer(ShellfishClient.MOSS_BALL_MODEL)), 0f);
    }

    @Override
    public Identifier getTextureLocation(ShellfishRenderState<MossBallEntity.Variant> entity) {
        return Identifier.fromNamespaceAndPath("shellfish", "textures/entity/moss_ball.png");
    }

    @Override
    public void submit(ShellfishRenderState<MossBallEntity.Variant> entity, PoseStack poseStack,
                       SubmitNodeCollector orderedRenderCommandQueue, CameraRenderState cameraRenderState)  {

        if(entity.variant.getIndex() == 1) {
            poseStack.scale(0.5f, 0.5f, 0.5f);
        } else {
            poseStack.scale(0.75f, 0.75f, 0.75f);
        }


        super.submit(entity, poseStack, orderedRenderCommandQueue, cameraRenderState);
    }

    @Override
    public ShellfishRenderState<Variant> createRenderState() {
        return new ShellfishRenderState<Variant>();
    }

    @Override
    public void extractRenderState(MossBallEntity mossBall, ShellfishRenderState<Variant> mossBallState, float f) {
        super.extractRenderState(mossBall, mossBallState, f);
        mossBallState.variant = mossBall.getVariant();
    }
}
