package womp.shellfishmod.client.renderer;

import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.state.CameraRenderState;
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
                       OrderedRenderCommandQueue orderedRenderCommandQueue, CameraRenderState cameraRenderState) {
        int variant = entity.variant.getIndex();                  
        if (variant == 0) entity.shellfish.scale(poseStack, 1.2f, 0.8f, 0.5f);
        else if (variant == 4) entity.shellfish.scale(poseStack, 1.3f, 0.85f, 0.5f);
        else entity.shellfish.scale(poseStack, 1f, 0.5f);

        super.render(entity, poseStack, orderedRenderCommandQueue, cameraRenderState);
    }

    @Override
    public ShellfishRenderState<Variant> createRenderState() {
        return new ShellfishRenderState<Variant>();
    }

    @Override
    public void updateRenderState(LobsterEntity lobster, ShellfishRenderState<Variant> lobsterState, float f) {
        super.updateRenderState(lobster, lobsterState, f);
        lobsterState.variant = lobster.getVariant();
        lobsterState.shellfish = lobster;
        lobsterState.idleAnimationState.copyFrom(lobster.idleAnimationState);
        lobsterState.moveAnimationState.copyFrom(lobster.moveAnimationState);
    }
}
