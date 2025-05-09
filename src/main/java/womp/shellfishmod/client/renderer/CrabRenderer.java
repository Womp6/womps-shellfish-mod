package womp.shellfishmod.client.renderer;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import womp.shellfishmod.client.model.CrabModel;
import womp.shellfishmod.client.states.ShellfishRenderState;
import womp.shellfishmod.entity.CrabEntity;
import womp.shellfishmod.entity.CrabEntity.Variant;
import womp.shellfishmod.client.ShellfishClient;

public class CrabRenderer extends MobEntityRenderer<CrabEntity, ShellfishRenderState<CrabEntity.Variant>, CrabModel> {

    public CrabRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new CrabModel(renderManager.getPart(ShellfishClient.CRAB_MODEL)), 0.3f);
    }

    @Override
    public Identifier getTexture(ShellfishRenderState<CrabEntity.Variant> animatable) {
        int variant = animatable.variant.getIndex();
        return Identifier.of("shellfish", "textures/entity/crab/crab_" + variant + ".png");
    }

    @Override
    public void render(ShellfishRenderState<CrabEntity.Variant> entity, MatrixStack poseStack,
                       VertexConsumerProvider bufferSource, int packedLight) {
            if(entity.baby) {
                poseStack.scale(0.4f, 0.4f, 0.4f);
            }
            if(!entity.baby) {
                poseStack.scale(0.75f, 0.75f, 0.75f);
            }

        super.render(entity, poseStack, bufferSource, packedLight);
    }

    @Override
    public ShellfishRenderState<Variant> createRenderState() {
        return new ShellfishRenderState<Variant>();
    }

    @Override
    public void updateRenderState(CrabEntity crab, ShellfishRenderState<Variant> crabState, float f) {
        super.updateRenderState(crab, crabState, f);
        crabState.variant = crab.getVariant();
        crabState.idleAnimationState.copyFrom(crab.idleAnimationState);
        crabState.moveAnimationState.copyFrom(crab.moveAnimationState);
    }
}
