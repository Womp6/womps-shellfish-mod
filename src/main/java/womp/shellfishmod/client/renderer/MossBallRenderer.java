package womp.shellfishmod.client.renderer;

import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.state.CameraRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import womp.shellfishmod.client.model.MossBallModel;
import womp.shellfishmod.client.states.ShellfishRenderState;
import womp.shellfishmod.entity.MossBallEntity;
import womp.shellfishmod.entity.MossBallEntity.Variant;
import womp.shellfishmod.client.ShellfishClient;

public class MossBallRenderer extends MobEntityRenderer<MossBallEntity, ShellfishRenderState<MossBallEntity.Variant>, MossBallModel> {

    public MossBallRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new MossBallModel(renderManager.getPart(ShellfishClient.MOSS_BALL_MODEL)), 0f);
    }

    @Override
    public Identifier getTexture(ShellfishRenderState<MossBallEntity.Variant> entity) {
        return Identifier.of("shellfish", "textures/entity/moss_ball.png");
    }

    @Override
    public void render(ShellfishRenderState<MossBallEntity.Variant> entity, MatrixStack poseStack,
                       OrderedRenderCommandQueue orderedRenderCommandQueue, CameraRenderState cameraRenderState)  {

        if(entity.variant.getIndex() == 1) {
            poseStack.scale(0.5f, 0.5f, 0.5f);
        } else {
            poseStack.scale(0.75f, 0.75f, 0.75f);
        }


        super.render(entity, poseStack, orderedRenderCommandQueue, cameraRenderState);
    }

    @Override
    public ShellfishRenderState<Variant> createRenderState() {
        return new ShellfishRenderState<Variant>();
    }

    @Override
    public void updateRenderState(MossBallEntity mossBall, ShellfishRenderState<Variant> mossBallState, float f) {
        super.updateRenderState(mossBall, mossBallState, f);
        mossBallState.variant = mossBall.getVariant();
    }
}
