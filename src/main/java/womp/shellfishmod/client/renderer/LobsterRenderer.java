package womp.shellfishmod.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import womp.shellfishmod.client.ShellfishClient;
import womp.shellfishmod.client.model.LobsterModel;
import womp.shellfishmod.client.states.ShellfishRenderState;
import womp.shellfishmod.entity.LobsterEntity;

public class LobsterRenderer extends MobRenderer<LobsterEntity, ShellfishRenderState<LobsterEntity.Variant>,  LobsterModel> {

    public LobsterRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new LobsterModel(renderManager.bakeLayer(ShellfishClient.LOBSTER_MODEL)), 0.4f);
    }

    @Override
    public ResourceLocation getTextureLocation(ShellfishRenderState<LobsterEntity.Variant> animatable) {
        int variant = animatable.variant.getIndex();
        return ResourceLocation.fromNamespaceAndPath("shellfish", "textures/entity/lobster/lobster_" + variant + ".png");
    }

    @Override
    public void render(ShellfishRenderState<LobsterEntity.Variant> entity, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLight) {
        if(entity.isBaby) {
            poseStack.scale(0.5f, 0.5f, 0.5f);
        }

        super.render(entity, poseStack, bufferSource, packedLight);
    }

    @Override
    public ShellfishRenderState<LobsterEntity.Variant> createRenderState() {
        return new ShellfishRenderState<>();
    }

    @Override
    public void extractRenderState(LobsterEntity lobster, ShellfishRenderState<LobsterEntity.Variant> lobsterState, float f) {
        super.extractRenderState(lobster, lobsterState, f);
        lobsterState.variant = lobster.getVariant();
        lobsterState.idleAnimationState.copyFrom(lobster.idleAnimationState);
        lobsterState.moveAnimationState.copyFrom(lobster.moveAnimationState);
    }
}
