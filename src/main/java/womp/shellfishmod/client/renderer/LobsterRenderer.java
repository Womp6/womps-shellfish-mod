package womp.shellfishmod.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.resources.Identifier;
import womp.shellfishmod.client.ShellfishClient;
import womp.shellfishmod.client.model.LobsterModel;
import womp.shellfishmod.client.states.ShellfishRenderState;
import womp.shellfishmod.entity.LobsterEntity;

public class LobsterRenderer extends MobRenderer<LobsterEntity, ShellfishRenderState<LobsterEntity.Variant>,  LobsterModel> {

    public LobsterRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new LobsterModel(renderManager.bakeLayer(ShellfishClient.LOBSTER_MODEL)), 0.4f);
    }

    @Override
    public Identifier getTextureLocation(ShellfishRenderState<LobsterEntity.Variant> animatable) {
        int variant = animatable.variant.getIndex();
        return Identifier.fromNamespaceAndPath("shellfish", "textures/entity/lobster/lobster_" + variant + ".png");
    }

    @Override
    public void submit(ShellfishRenderState<LobsterEntity.Variant> entity, PoseStack poseStack,
                       SubmitNodeCollector queue, CameraRenderState camera) {
        int variant = entity.variant.getIndex();
        if (variant == 0) entity.shellfish.scale(poseStack, 1.2f, 0.8f, 0.5f);
        else if (variant == 4) entity.shellfish.scale(poseStack, 1.3f, 0.85f, 0.5f);
        else entity.shellfish.scale(poseStack, 1f, 0.5f);

        super.submit(entity, poseStack, queue, camera);
    }

    @Override
    public ShellfishRenderState<LobsterEntity.Variant> createRenderState() {
        return new ShellfishRenderState<>();
    }

    @Override
    public void extractRenderState(LobsterEntity lobster, ShellfishRenderState<LobsterEntity.Variant> lobsterState, float f) {
        super.extractRenderState(lobster, lobsterState, f);
        lobsterState.variant = lobster.getVariant();
        lobsterState.shellfish = lobster;
        lobsterState.idleAnimationState.copyFrom(lobster.idleAnimationState);
        lobsterState.moveAnimationState.copyFrom(lobster.moveAnimationState);
    }
}
