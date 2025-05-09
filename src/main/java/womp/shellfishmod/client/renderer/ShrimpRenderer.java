package womp.shellfishmod.client.renderer;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import womp.shellfishmod.client.model.ShrimpModel;
import womp.shellfishmod.client.states.ShellfishRenderState;
import womp.shellfishmod.entity.ShrimpEntity;
import womp.shellfishmod.entity.ShrimpEntity.Variant;
import womp.shellfishmod.client.ShellfishClient;

public class ShrimpRenderer extends MobEntityRenderer<ShrimpEntity, ShellfishRenderState<ShrimpEntity.Variant>, ShrimpModel> {

    public ShrimpRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new ShrimpModel(renderManager.getPart(ShellfishClient.SHRIMP_MODEL)), 0.4f);
    }

    @Override
    public Identifier getTexture(ShellfishRenderState<ShrimpEntity.Variant> animatable) {
        int variant = animatable.variant.getIndex();
        return Identifier.of("shellfish", "textures/entity/shrimp/shrimp_" + variant + ".png");
    }

    @Override
    public void render(ShellfishRenderState<ShrimpEntity.Variant> entity, MatrixStack poseStack,
                       VertexConsumerProvider bufferSource, int packedLight) {
        if(entity.baby) {
            poseStack.scale(0.3f, 0.3f, 0.3f);
        }
        if(!entity.baby) {
            poseStack.scale(0.65f, 0.65f, 0.65f);
        }

        super.render(entity, poseStack, bufferSource, packedLight);
    }

    @Override
    public ShellfishRenderState<Variant> createRenderState() {
        return new ShellfishRenderState<Variant>();
    }

    @Override
    public void updateRenderState(ShrimpEntity shrimp, ShellfishRenderState<Variant> shrimpState, float f) {
        super.updateRenderState(shrimp, shrimpState, f);
        shrimpState.variant = shrimp.getVariant();
        shrimpState.idleAnimationState.copyFrom(shrimp.idleAnimationState);
        shrimpState.moveAnimationState.copyFrom(shrimp.swimAnimationState);
    }
}
