package womp.shellfishmod.client.renderer;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import womp.shellfishmod.client.model.LobsterModel;
import womp.shellfishmod.client.states.ShellfishRenderState;
import womp.shellfishmod.entity.LobsterEntity;
import womp.shellfishmod.entity.LobsterEntity.Variant;
import womp.shellfishmod.client.ShellfishClient;

public class LobsterRenderer extends MobEntityRenderer<LobsterEntity, ShellfishRenderState<LobsterEntity.Variant>, LobsterModel> {
    
    public LobsterRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new LobsterModel(renderManager.getPart(ShellfishClient.LOBSTER_MODEL)), 0.4f);
    }

    @Override
    public Identifier getTexture(ShellfishRenderState<LobsterEntity.Variant> animatable) {
        int variant = animatable.variant.getIndex();
        return Identifier.of("shellfish", "textures/entity/lobster/lobster_" + variant + ".png");
    }

    @Override
    public void render(ShellfishRenderState<LobsterEntity.Variant> entity, MatrixStack poseStack,
                       VertexConsumerProvider bufferSource, int packedLight) {
        if(entity.baby) {
            poseStack.scale(0.5f, 0.5f, 0.5f);
        }

        super.render(entity, poseStack, bufferSource, packedLight);
    }

    @Override
    public ShellfishRenderState<Variant> createRenderState() {
        return new ShellfishRenderState<Variant>();
    }

    @Override
    public void updateRenderState(LobsterEntity lobster, ShellfishRenderState<Variant> lobsterState, float f) {
        super.updateRenderState(lobster, lobsterState, f);
        lobsterState.variant = lobster.getVariant();
        lobsterState.idleAnimationState.copyFrom(lobster.idleAnimationState);
        lobsterState.moveAnimationState.copyFrom(lobster.moveAnimationState);
    }
}
