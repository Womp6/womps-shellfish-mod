package womp.shellfishmod.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import womp.shellfishmod.client.ShellfishClient;
import womp.shellfishmod.client.model.OysterModel;
import womp.shellfishmod.client.states.ShellfishRenderState;
import womp.shellfishmod.entity.OysterEntity;

public class OysterRenderer extends MobRenderer<OysterEntity, ShellfishRenderState<OysterEntity.Variant>,  OysterModel> {

    public OysterRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new OysterModel(ctx.bakeLayer(ShellfishClient.OYSTER_MODEL)), 0.3f);
    }

    @Override
    public ResourceLocation getTextureLocation(ShellfishRenderState<OysterEntity.Variant> var1) {
        int variant = var1.variant.getIndex();
        return ResourceLocation.fromNamespaceAndPath("shellfish", "textures/entity/oyster/oyster_" + variant + ".png");
    }

    @Override
    public void render(ShellfishRenderState<OysterEntity.Variant> livingEntity, PoseStack matrixStack,
                       MultiBufferSource vertexConsumerProvider, int i) {
        if(livingEntity.isBaby) {
            matrixStack.scale(0.5f, 0.5f, 0.5f);
        }

        super.render(livingEntity, matrixStack, vertexConsumerProvider, i);
    }

    @Override
    public ShellfishRenderState<OysterEntity.Variant> createRenderState() {
        return new ShellfishRenderState<>();
    }

    @Override
    public void extractRenderState(OysterEntity oyster, ShellfishRenderState<OysterEntity.Variant> oysterState, float f) {
        super.extractRenderState(oyster, oysterState, f);
        oysterState.variant = oyster.getVariant();
        oysterState.idleAnimationState.copyFrom(oyster.idleAnimationState);
        oysterState.moveAnimationState.copyFrom(oyster.moveAnimationState);
    }
}
