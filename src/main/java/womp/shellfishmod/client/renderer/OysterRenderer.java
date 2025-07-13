package womp.shellfishmod.client.renderer;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory.Context;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import womp.shellfishmod.client.model.OysterModel;
import womp.shellfishmod.client.states.ShellfishRenderState;
import womp.shellfishmod.entity.OysterEntity;
import womp.shellfishmod.entity.OysterEntity.Variant;
import womp.shellfishmod.client.ShellfishClient;

public class OysterRenderer extends MobEntityRenderer<OysterEntity, ShellfishRenderState<OysterEntity.Variant>, OysterModel> {

    public OysterRenderer(Context ctx) {
        super(ctx, new OysterModel(ctx.getPart(ShellfishClient.OYSTER_MODEL)), 0.3f);
    }

    @Override
    public Identifier getTexture(ShellfishRenderState<OysterEntity.Variant> var1) {
        int variant = var1.variant.getIndex();
        return Identifier.of("shellfish", "textures/entity/oyster/oyster_" + variant + ".png");
    }

    @Override
    public void render(ShellfishRenderState<OysterEntity.Variant> entity, MatrixStack poseStack,
            VertexConsumerProvider vertexConsumerProvider, int i) {
        int variant = entity.variant.getIndex();                  
        if (variant == 0) entity.shellfish.scale(poseStack, 0.8f, 0.4f);
        else if (variant == 2 || variant == 3) entity.shellfish.scale(poseStack, 1.2f, 0.85f, 0.5f);
        else entity.shellfish.scale(poseStack, 1.0f, 0.5f);

        super.render(entity, poseStack, vertexConsumerProvider, i);
    }

    @Override
    public ShellfishRenderState<Variant> createRenderState() {
        return new ShellfishRenderState<Variant>();
    }

    @Override
    public void updateRenderState(OysterEntity oyster, ShellfishRenderState<Variant> oysterState, float f) {
        super.updateRenderState(oyster, oysterState, f);
        oysterState.variant = oyster.getVariant();
        oysterState.shellfish = oyster;
        oysterState.idleAnimationState.copyFrom(oyster.idleAnimationState);
        oysterState.moveAnimationState.copyFrom(oyster.moveAnimationState);
    }
}
