package womp.shellfishmod.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.resources.Identifier;
import womp.shellfishmod.client.ShellfishClient;
import womp.shellfishmod.client.model.OysterModel;
import womp.shellfishmod.client.states.ShellfishRenderState;
import womp.shellfishmod.entity.OysterEntity;

public class OysterRenderer extends MobRenderer<OysterEntity, ShellfishRenderState<OysterEntity.Variant>,  OysterModel> {

    public OysterRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new OysterModel(ctx.bakeLayer(ShellfishClient.OYSTER_MODEL)), 0.3f);
    }

    @Override
    public Identifier getTextureLocation(ShellfishRenderState<OysterEntity.Variant> var1) {
        int variant = var1.variant.getIndex();
        return Identifier.fromNamespaceAndPath("shellfish", "textures/entity/oyster/oyster_" + variant + ".png");
    }

    @Override
    public void submit(ShellfishRenderState<OysterEntity.Variant> entity, PoseStack poseStack,
                       SubmitNodeCollector queue, CameraRenderState camera) {
        int variant = entity.variant.getIndex();
        if (variant == 0) entity.shellfish.scale(poseStack, 0.8f, 0.4f);
        else if (variant == 2 || variant == 3) entity.shellfish.scale(poseStack, 1.2f, 0.85f, 0.5f);
        else entity.shellfish.scale(poseStack, 1.0f, 0.5f);

        super.submit(entity, poseStack, queue, camera);
    }

    @Override
    public ShellfishRenderState<OysterEntity.Variant> createRenderState() {
        return new ShellfishRenderState<>();
    }

    @Override
    public void extractRenderState(OysterEntity oyster, ShellfishRenderState<OysterEntity.Variant> oysterState, float f) {
        super.extractRenderState(oyster, oysterState, f);
        oysterState.variant = oyster.getVariant();
        oysterState.shellfish = oyster;
        oysterState.idleAnimationState.copyFrom(oyster.idleAnimationState);
        oysterState.moveAnimationState.copyFrom(oyster.moveAnimationState);
    }
}
